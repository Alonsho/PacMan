package geometry;


import java.util.ArrayList;
import java.util.List;
/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class Rectangle {
    //fields of Rectangle
    private Point upperLeft;
    private Point upperRight;
    private Point lowerLeft;
    private Point lowerRight;
    private double width;
    private double height;
    private Line upLine;
    private Line downLine;
    private Line leftLine;
    private Line rightLine;
    /**
     * Rectangle - construct a Rectangle object.
     * <p>
     * Create a new rectangle with location and width/height.
     * <p>
     *
     *
     * @param upperLeft - the location of the upper left corner of the rectangle.
     * @param width - the width of the rectangle.
     * @param height - the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        this.upLine = new Line(this.upperLeft, this.upperRight);
        this.downLine = new Line(this.lowerLeft, this.lowerRight);
        this.rightLine = new Line(this.lowerRight, this.upperRight);
        this.leftLine = new Line(this.upperLeft, this.lowerLeft);
    }
    /**
     * intersectionPoints - Return a list of intersection points of a line with this rectangle.
     * <p>
     * returns an empty list if there are no intersections.
     * <p>
     *
     *
     * @param line - the line to check with.
     * @return the list of intersection points.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> interList = new ArrayList<>();
        // check if any of the rectangles borders intersect with the line.
        if (this.upLine.isIntersecting(line)) {
            interList.add(upLine.intersectionWith(line));
        }
        if (this.downLine.isIntersecting(line)) {
            interList.add(downLine.intersectionWith(line));
        }
        if (this.rightLine.isIntersecting(line)) {
            interList.add(rightLine.intersectionWith(line));
        }
        if (this.leftLine.isIntersecting(line)) {
            interList.add(leftLine.intersectionWith(line));
        }
        return interList;
    }
    /**
     * getWidth - Return the width of the rectangle.
     * <p>
     *
     * <p>
     *
     *
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * getHeight- Return the height of the rectangle.
     * <p>
     *
     * <p>
     *
     *
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * getupperLeft - Return the upper left corner of the rectangle.
     * <p>
     *
     * <p>
     *
     *
     * @return the upper left corner of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * getlowerRight - Return the lower right corner of the rectangle.
     * <p>
     *
     * <p>
     *
     *
     * @return the lower right corner of the rectangle.
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }
    /**
     * getUpLine - Return the upper edge of the rectangle.
     * <p>
     *
     * <p>
     *
     *
     * @return the upper edge of the rectangle
     */
    public Line getUpLine() {
        return this.upLine;
    }
}