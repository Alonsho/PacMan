package geometry;

import java.util.List;
/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class Line {
    //fields of Line
    private Point start;
    private Point end;
    //slope and intercept are the 'm' and 'b' of the line equation y = m*x + b.
    private double slope;
    private double intercept;
    /**
     * Line - construct a Line object.
     * <p>
     *
     * <p>
     *
     *
     * @param start - the location of the beginning of the line.
     @param end - the location of the end of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        //slope and intercept are the 'm' and 'b' of the line equation y = m*x + b.
        this.slope = ((this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX()));
        this.intercept = this.start.getY() - (this.slope * this.start.getX());
    }
    /**
     * Line - construct a Line object.
     * <p>
     *
     * <p>
     *
     *
     * @param x1 - the x coordinate of the beginning of the line.
     @param y1 - the y coordinate of the beginning of the line.
     @param x2 - the x coordinate of the end of the line.
     @param y2 - the x coordinate of the end of the line.
     */
    public Line(double x1, double y1, double x2, double y2) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        this.start = p1;
        this.end = p2;
        //slope and intercept are the 'm' and 'b' of the line equation y = m*x + b.
        this.slope = (y1 - y2) / (x1 - x2);
        this.intercept = y1 - (this.slope * x1);
    }
    /**
     * getLength - return the length of the line.
     * <p>
     *
     * <p>
     *
     *
     * @return the length of the line calculated using the Point 'distance' method.
     */
    public double getLength() {
        return this.start.distance(this.end);
    }
    /**
     * middle - returns the middle point of the line.
     * <p>
     *
     * <p>
     *
     *
     * @return a newly created middle point.
     */
    public Point middle() {
        double xMiddle = (this.start.getX() + this.end.getX()) / 2;
        double yMiddle = (this.start.getY() + this.end.getY()) / 2;
        return (new Point(xMiddle, yMiddle));
    }
    /**
     * getStart - returns the start point of the line.
     * <p>
     *
     * <p>
     *
     *
     * @return the start point of the line.
     */
    public Point getStart() {
        return this.start;
    }
    /**
     * getEnd - returns the end point of the line.
     * <p>
     *
     * <p>
     *
     *
     * @return the end point of the line.
     */
    public Point getEnd() {
        return this.end;
    }
    /**
     * isIntersecting - returns true if the lines intersect, false otherwise.
     * <p>
     *
     * <p>
     * if lines converge/overlap, the method will return false
     *
     * @param other - line to be checked with.
     * @return true/false.
     */
    public boolean isIntersecting(Line other) {
        //in order to check if a line is vertical we need to create a Double object for the slopes
        Double thisSlope = this.slope;
        Double otherSlope = other.slope;
        //this codition handles all scenarios where at least one of the lines is vertical
        if (thisSlope.isInfinite() || otherSlope.isInfinite()) {
            if (thisSlope.isInfinite() && otherSlope.isInfinite()) {
                return false;
            }
            if (thisSlope.isInfinite() && !otherSlope.isInfinite()) {
                double x = this.start.getX();
                double y = (other.slope * x) + other.intercept;
                Point inters = new Point(x, y);
                if (this.isOnLine(inters) && other.isOnLine(inters)) {
                    return true;
                }
            }
            if (!thisSlope.isInfinite() && otherSlope.isInfinite()) {
                double x = other.start.getX();
                double y = (this.slope * x) + this.intercept;
                Point inters = new Point(x, y);
                if (this.isOnLine(inters) && other.isOnLine(inters)) {
                    return true;
                }
            }
            return false;
        }
        //check if lines are parallel but do not converge/overlap.
        if (this.slope == other.slope && this.intercept != other.intercept) {
            return false;
        }
        //check if lines are parallel and also converge/overlap.
        if (this.slope == other.slope && this.intercept == other.intercept) {
            return false;
        }
        // 'x' and 'y' are the coordinates of the point of intersection, calculated using the line equation.
        double x = (this.intercept - other.intercept) / (other.slope - this.slope);
        double y = (this.slope * x) + this.intercept;
        //'inters' is the point of intersection
        Point inters = new Point(x, y);
        //check if the point of intersection is on both lines using the triangle inequality theory.
        if (this.isOnLine(inters) && other.isOnLine(inters)) {
            return true;
        }
        return false;
    }
    /**
     * intersectionWith - returns the intersection point if the lines intersect, and null otherwise.
     * <p>
     * will use the 'isIntersecting' method.
     * <p>
     *
     *
     * @param other - line to be checked with.
     * @return the point of intersection or null.
     */
    public Point intersectionWith(Line other) {
        //in order to check if a line is vertical we need to create a Double object for the slopes
        Double thisSlope = this.slope;
        Double otherSlope = other.slope;
        // check if the lines intersect, return null if they don't.
        if (!this.isIntersecting(other)) {
            return null;
        }
        //this next 2 conditions handle all scenarios where one of the lines is vertical
        if (thisSlope.isInfinite() && !otherSlope.isInfinite()) {
            double x = this.start.getX();
            double y = (other.slope * x) + other.intercept;
            return (new Point(x, y));
        }
        if (!thisSlope.isInfinite() && otherSlope.isInfinite()) {
            double x = other.start.getX();
            double y = (this.slope * x) + this.intercept;
            return (new Point(x, y));
        }
        // 'x' and 'y' are the coordinates of the point of intersection, calculated using the line equation.
        double x = (this.intercept - other.intercept) / (other.slope - this.slope);
        double y = (this.slope * x) + this.intercept;
        return (new Point(x, y));
    }
    /**
     * equals - returns true is the lines are equal, false otherwise.
     * <p>
     *
     * <p>
     *
     *
     * @param other - line to be checked with.
     * @return true/false.
     */
    public boolean equals(Line other) {
        if ((this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start))) {
            return true;
        }
        return false;
    }
    /**
     * isOnLine - returns true if the given point is on the line, false otherwise.
     * <p>
     *
     * <p>
     *
     *
     * @param p1 - the given point.
     * @return true/false.
     */
    public boolean isOnLine(Point p1) {
        //first condition checks if the x coordinate of the point is between the x coordinates of
        //the start and end point of the line
        if ((p1.getX() >= this.start.getX() && p1.getX() <= this.end.getX())
                || (p1.getX() >= this.end.getX() && p1.getX() <= this.start.getX())) {
            //second condition checks if the y coordinate of the point is
            //between the y coordinates of the start and end point of the line
            if ((p1.getY() >= this.start.getY() && p1.getY() <= this.end.getY())
                    || (p1.getY() >= this.end.getY() && p1.getY() <= this.start.getY())) {
                return true;
            }
        }
        return false;
    }
    /**
     * closestIntersectionToStartOfLine -  If this line does not intersect with the rectangle, return null.
     Otherwise, return the closest intersection point to the
     start of the line.
     * <p>
     *
     * <p>
     *
     *
     * @param rect - the given rectangle.
     * @return closest - the closest intersection point to the start of the line, OR null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //create a list of the intersection points
        List<Point> interList = rect.intersectionPoints(this);
        Point closest;
        //if the list is empty, there are no intersections, return null.
        if (interList.size() == 0) {
            return null;
        } else {
            //set 'min' to be the distance of the first intersection point from the beginning of the line
            double min = interList.get(0).distance(this.start);
            //set the closest intersection point to be the first in the list
            closest = interList.get(0);
            //go through all intersection point
            for (Point point : interList) {
                //if the current intersection is closer to the start of line then 'min'
                if (point.distance(this.start) < min) {
                    //set the new min distance
                    min = point.distance(this.start);
                    //set the new closest point
                    closest = point;
                }
            }
        }
        return closest;
    }
}