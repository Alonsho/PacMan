package sprites;

import biuoop.DrawSurface;
import gameplay.*;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Target extends Ball implements Sprite, Collidable {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment board;
    private List<HitListener> hitListeners;
    private TargetType type;


    public Target(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        hitListeners = new ArrayList<>();
    }



    public Target(double x, double y, TargetType type) {
        this.center = new Point(x, y);
        this.v = new Velocity(0, 0);
        this.type = type;
        hitListeners = new ArrayList<>();
        switch (type) {
            case LARGE_RED:
                this.r = 8;
                this.color = Color.red;
                break;
            case SMALL_RED:
                this.r = 5;
                this.color = Color.red;
                break;
            case LARGE_BLUE:
                this.r = 8;
                this.color = Color.blue;
                break;
            case SMALL_BLUE:
                this.r = 5;
                this.color = Color.blue;
                break;
        }
    }



    public int getX() {
        //cast to be compatible
        return (int) this.center.getX();
    }

    public int getY() {
        //cast to be compatible
        return (int) this.center.getY();
    }



    public Point getCenter() {
        return this.center;
    }

    public int getSize() {
        return this.r;
    }


    public java.awt.Color getColor() {
        return this.color;
    }


    public void setGameEnvironment(GameEnvironment game) {
        this.board = game;
    }


    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }



    public void setVelocity(Velocity vel) {
        this.v = vel;
    }



    public void drawOn(DrawSurface surface) {
        //set the color to be the color of the ball
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
        surface.setColor(java.awt.Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.r);
    }


    public void timePassed() {
        //get the ball to move
        this.moveOneStep();
    }


    public void addToGame(Game game) {
        //set the ball to recognize the given game environment.
        this.setGameEnvironment(game.getEnvironment());
        game.getSprites().addSprite(this);
        game.addCollidable(this);
    }


    public void moveOneStep() {
        //the location where the ball would be after the next step
        Point nextLocation = new Point(this.center.getX() + this.v.getDx(), this.center.getY() + this.v.getDy());
        //the trajectory line of the balls movement
        Line trajectory = new Line(this.center, nextLocation);
        //the details of the collision that will occur during next step, if there is one.
        CollisionInfo collision = this.board.getClosestCollision(trajectory);
        //if no collision will occur, move the ball normally.
        if (collision == null) {
            this.center = this.v.applyToPoint(this.center);
        } else {
            //the following block will move the ball to "almost" the hit point, but just slightly before it.
            if (this.v.getDx() > 0) {
                this.center = new Point(collision.getCollisionPoint().getX() - 1, collision.getCollisionPoint().getY());
            }
            if (this.v.getDx() < 0) {
                this.center = new Point(collision.getCollisionPoint().getX() + 1, collision.getCollisionPoint().getY());
            }
            if (this.v.getDy() > 0) {
                this.center = new Point(this.center.getX(), collision.getCollisionPoint().getY() - 1);
            }
            if (this.v.getDy() < 0) {
                this.center = new Point(this.center.getX(), collision.getCollisionPoint().getY() + 1);
            }
            //notify the hit object that a collision occurred, and set the new velocity to the ball.
            this.setVelocity(collision.getCollisionObject().hit(this, collision.getCollisionPoint(), this.v));
        }
    }


    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }


    public Rectangle getCollisionRectangle() {
        return new Rectangle(new Point(5, 5), 7, 4);
    }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        return hitter.getVelocity();
    }


    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
        return;
    }


    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
        return;
    }


    public TargetType getType() {
        return this.type;
    }



    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
