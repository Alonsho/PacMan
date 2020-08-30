package sprites;

import biuoop.DrawSurface;
import gameplay.*;
import geometry.Line;
import geometry.Point;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.List;

public class User extends Ball implements Sprite {
    private Controller xc;
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private Game game;
    private GameEnvironment board;
    private List<Target> targetList;
    private Counter score;






    public User(double x, double y, int r, java.awt.Color color, Controller xc, List<Target> targetList, Game game, Counter score) {
        this.xc = xc;
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.targetList = targetList;
        this.game = game;
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
        surface.fillCircle(this.getX(), this.getY(), this.r);
        surface.setColor(java.awt.Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.r);
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
        this.xc.poll();
        double xVel = 0;
        double yVel = 0;
        Component[] components = xc.getComponents();

        for(int i=0;i<components.length;i++) {
            if (components[i].getName().equals("Y Axis")) {
                yVel = components[i].getPollData() * (4.5 + Game.GAME_SPEED);
            }

            if (components[i].getName().equals("X Axis")) {
                xVel = components[i].getPollData() * (4.5 + Game.GAME_SPEED);
            }
        }


        this.v = new Velocity(xVel, yVel);





        /*
        //FOR ACCELERATION
        for(int i=0;i<components.length;i++) {
            if (components[i].getName().equals("Y Axis")) {
                yVel = components[i].getPollData() / 10;
            }

            if (components[i].getName().equals("X Axis")) {
                xVel = components[i].getPollData() / 10;
            }
        }


        this.v = new Velocity(this.v.getDx() + xVel, this.v.getDy() + yVel);

         */




        /*
        //FOR ACCELERATION WITH FRICTION
        double yForce = 0;
        double xForce = 0;
        for(int i=0;i<components.length;i++) {
            if (components[i].getName().equals("Y Axis")) {
                yForce = components[i].getPollData() / 5;
            }

            if (components[i].getName().equals("X Axis")) {
                xForce = components[i].getPollData() / 5;
            }
        }


        this.v = new Velocity(this.v.getDx() + xForce, this.v.getDy() + yForce);
        Double speedAngle = this.v.toAngle();
        Double f = (double) this.r / 30;
        Velocity frictionVel = Velocity.fromAngleAndSpeed(Math.toDegrees(speedAngle  + Math.PI), (double) this.r / 150);
        if (frictionVel.getSize() >= this.v.getSize()) {
            this.v = new Velocity(0, 0);
        } else {
            this.v = Velocity.fromAngleAndSpeed(Math.toDegrees(this.v.toAngle()), this.v.getSize() - frictionVel.getSize());
        }

         */








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


        List<Target> targetsToRemove = new ArrayList<>();
        for (Target tg : this.targetList) {
            if (this.center.distance(tg.getCenter()) <= this.r + tg.getSize()) {
                targetsToRemove.add(tg);
                if (tg.getType() == TargetType.LARGE_RED || tg.getType() == TargetType.LARGE_BLUE) {
                    this.score.increase(2);
                } else {
                    this.score.increase(1);
                }
            }
        }
        for (Target tg: targetsToRemove) {
            tg.removeFromGame(this.game);
            this.targetList.remove(tg);
        }
        targetsToRemove.clear();


    }
}
