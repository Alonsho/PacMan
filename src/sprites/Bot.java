package sprites;

import biuoop.DrawSurface;
import gameplay.*;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot extends Ball implements Sprite {

    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private Game game;
    private GameEnvironment board;
    private List<Target> targetList;
    private boolean[] typesToChase;
    private Counter score;



    public Bot(double x, double y, int r, java.awt.Color color, List<Target> targetList, boolean[] typesToChase, Game game, Counter score) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.targetList = targetList;
        this.game = game;
        this.typesToChase = typesToChase;
        this.score = score;
    }



    public int getX() {
        //cast to be compatible
        return (int) this.center.getX();
    }

    public int getY() {
        //cast to be compatible
        return (int) this.center.getY();
    }


    public int getSize() {
        return this.r;
    }


    public java.awt.Color getColor() {
        return this.color;
    }


    public Point getCenter() {
        return this.center;
    }


    public void setGameEnvironment(GameEnvironment game) {
        this.board = game;
    }


    public void drawOn(DrawSurface surface) {
        //set the color to be the color of the ball
        surface.setColor(this.color);
        surface.fillRectangle(this.getX() - this.r, this.getY() - this.r, 2 * this.r, 2* this.r);
        surface.setColor(java.awt.Color.black);
        surface.drawRectangle(this.getX() - this.r, this.getY() - this.r, 2 * this.r, 2* this.r);
    }


    public void timePassed() {
        //get the ball to move
        this.moveOneStep();
    }




    public void setVelocity(Velocity vel) {
        this.v = vel;
    }



    public void addToGame(Game game) {
        //set the ball to recognize the given game environment.
        this.setGameEnvironment(game.getEnvironment());
        game.getSprites().addSprite(this);
    }


    public void moveOneStep() {
        Target closest = findClosestTarget();
        this.v = findVelocity(closest);





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


        if (closest == null) {
            return;
        }
        if (this.center.distance(closest.getCenter()) <= this.r + closest.getSize()) {
            closest.removeFromGame(this.game);
            this.targetList.remove(closest);
            if (closest.getType() == TargetType.LARGE_RED || closest.getType() == TargetType.LARGE_BLUE) {
                this.score.increase(2);
            } else {
                this.score.increase(1);
            }
        }
    }

    public Target findClosestTarget() {
        Target closest = null;
        Double minDistance = null;
        if (this.targetList.isEmpty()) {
            return null;
        }
        for (Target tg : this.targetList) {
            if (!this.typesToChase[tg.getType().ordinal()]) {
                continue;
            }
            Double currDistance = this.center.distance(tg.getCenter());
            if (minDistance == null || currDistance < minDistance) {
                minDistance = currDistance;
                closest = tg;
            }
        }
        return closest;
    }


    public Velocity findVelocity(Target tg) {
        if (tg == null) {
            return new Velocity(0,0);
        }
        double xVector = tg.getCenter().getX() - this.center.getX();
        double yVector = tg.getCenter().getY() - this.center.getY();
        Velocity v = new Velocity(xVector, yVector);
        double angle = v.toAngle();
        return Velocity.fromAngleAndSpeed(Math.toDegrees(angle), (3.5 + Game.GAME_SPEED));
    }
}
