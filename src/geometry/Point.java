package geometry;



/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class Point {
    // fields of Point.
    private double x;
    private double y;

    /**
     * Point - construct a point object.
     * <p>
     *
     * <p>
     *
     *
     * @param x - the location of the point on x grid.
     @param y - the location of the point on y grid.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * distance - return the distance of this point to the other point.
     * <p>
     * calculate using the formula given on the exercise page.
     * <p>
     *
     *
     * @param other - the point we want to calculate the distance from.
     * @return the distance from the given point. */
    public double distance(Point other) {
        double distance = Math.sqrt(((this.x - other.x) * (this.x - other.x))
                + ((this.y - other.y) * (this.y - other.y)));
        return distance;
    }
    /**
     * equals - return true if the points are equal, false otherwise.
     * <p>
     *
     * <p>
     *
     *
     * @param other - the point we want to check if equals.
     * @return true if the points are equal, false otherwise. */
    public boolean equals(Point other) {
        if (this.x == other.x && this.y == other.y) {
            return true;
        }
        return false;
    }
    /**
     * getX - Return the x value of this point.
     * <p>
     *
     * <p>
     *
     *
     * @param
     * @return the x value of this point. */
    public double getX() {
        return this.x;
    }
    /**
     * getY - Return the y value of this point.
     * <p>
     *
     * <p>
     *
     *
     * @param
     * @return the y value of this point. */
    public double getY() {
        return this.y;
    }
}