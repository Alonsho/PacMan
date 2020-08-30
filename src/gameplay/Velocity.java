package gameplay;


import geometry.Point;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class Velocity {
    //fields of Velocity
    private double dx;
    private double dy;
    /**
     * Velocity - construct a Velocity object.
     * <p>
     *
     * <p>
     *
     *
     * @param dx - the change in position on the 'x' axis.
     * @param dy - the change in position on the 'y' axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * fromAngleAndSpeed - coverts a given angle and speed to a Velocity object.
     * <p>
     * claculates the velocity vectors on 'x' and 'y' axes
     * <p>
     *
     *
     * @param angle - the angle of the speed vector (starting up and going clockwise).
     * @param speed - the size of the velocity vector.
     * @return the created Velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        //calculate the velocity vector on the 'x' axis
        double dx = Math.sin(angle) * speed;
        //calculate the velocity vector on the 'y' axis
        double dy = -Math.cos(angle) * speed;
        return new Velocity(dx, dy);
    }


    public Double toAngle() {
        if (this.dy == 0) {
            if (this.dx == 0) {
                return null;
            } else if (this.dx > 0) {
                return Math.toRadians(90.0);
            } else if (this.dx < 0) {
                return Math.toRadians(270.0);
            }
        }
        if (this.dx >= 0 && this.dy <= 0) {
            return Math.atan(-this.dx/this.dy);
        }
        if (this.dx >= 0 && this.dy >= 0) {
            return (Math.atan(-this.dx/this.dy) + Math.PI);
        }
        if (this.dx <= 0 && this.dy <= 0) {
            return Math.atan(-this.dx/this.dy);
        }
        if (this.dx <= 0 && this.dy >= 0) {
            return (Math.atan(-this.dx/this.dy) + Math.PI);
        }
        return null;
    }


    public double getSize() {
        return (Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2)));
    }
    /**
     * getDx - Return the the change in position on the 'x' axis of the Velocity object.
     * <p>
     *
     * <p>
     *
     *
     * @return the the change in position on the 'x' axis of the Velocity object */
    public double getDx() {
        return this.dx;
    }
    /**
     * getDy - Return the the change in position on the 'y' axis of the Velocity object.
     * <p>
     *
     * <p>
     *
     *
     * @return the the change in position on the 'y' axis of the Velocity object */
    public double getDy() {
        return this.dy;
    }
    /**
     * applyToPoint - changes the location of the center of the ball to where it should be after one movement.
     * <p>
     *Take a point with position (x,y) and return a new point with position (x+dx, y+dy)
     * <p>
     *
     *
     * @param p - the point to be moved
     * @return a new point after one movement
     **/
    public Point applyToPoint(Point p) {
        return (new Point(p.getX() + this.dx, p.getY() + this.dy));
    }
}