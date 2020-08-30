package sprites;

import biuoop.DrawSurface;
import gameplay.CollisionInfo;
import gameplay.GameEnvironment;
import gameplay.Velocity;
import geometry.Line;
import geometry.Point;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class Ball implements Sprite {
    //fields of Ball
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment board;



    public Ball() {

    }
    /**
     * Ball - construct a Ball object.
     * <p>
     *
     * <p>
     *
     *
     * @param center - the location of the center of the ball.
     * @param r - the size (radius) of the ball.
     * @param color - the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }
    /**
     * Ball - construct a Ball object.
     * <p>
     *
     * <p>
     *
     *
     * @param x - the x coordiante of the center of the ball.
     * @param y - the y coordiante of the center of the ball.
     * @param r - the size (radius) of the ball.
     * @param color - the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }
    /**
     * getX - Return the x value of the center of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @return the x value of the center of the ball. */
    public int getX() {
        //cast to be compatible
        return (int) this.center.getX();
    }
    /**
     * getY - Return the y value of the center of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @return the y value of the center of the ball. */
    public int getY() {
        //cast to be compatible
        return (int) this.center.getY();
    }
    /**
     * getSize - Return the size (radius) of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @return the size (radius) of the ball. */
    public int getSize() {
        return this.r;
    }
    /**
     * getSize - Return the color of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @return the color of the ball. */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * setGameEnvironment - set the game environment for the ball.
     * <p>
     *
     * <p>
     *
     *
     * @param game - the game environment. */
    public void setGameEnvironment(GameEnvironment game) {
        this.board = game;
    }
    /**
     * drawOn - draw the ball on the given DrawSurface.
     * <p>
     *
     * <p>
     *
     *
     * @param surface - the surface to be drawn on
     **/
    public void drawOn(DrawSurface surface) {
        //set the color to be the color of the ball
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
        surface.setColor(java.awt.Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.r);
    }
    /**
     * setVelocity - set the velocity (v) field of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @param vel - the velocity to be given to the ball
     **/
    public void setVelocity(Velocity vel) {
        this.v = vel;
    }
    /**
     * setVelocity - set the velocity (v) field of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @param dx - the change (velocity) on x grid
     * @param dy - the change (velocity) on y grid
     **/
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }
    /**
     * getVelocity - return the velocity of the ball.
     * <p>
     *
     * <p>
     *
     *
     * @return the velocity of the ball
     **/
    public Velocity getVelocity() {
        return this.v;
    }
    /**
     * moveOneStep - moves the ball one step in the game enivronment.
     * <p>
     *
     * <p>
     *if the ball hits a collidable, change speed accordingly
     *
     *
     **/
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
    /**
     * timePassed - notify the ball to make an action.
     * <p>
     * the action the ball does is move on the screen
     * <p>
     *
     *
     *
     **/
    public void timePassed() {
        //get the ball to move
        this.moveOneStep();
    }
}