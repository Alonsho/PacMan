package gameplay;


import geometry.Point;
import sprites.Collidable;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class CollisionInfo {
    // the point at which the collision occurs.
    private Point collisionPoint;
    // the collidable object involved in the collision.
    private Collidable collisionObject;
    /**
     * CollisionInfo - construct a CollisionInfo object.
     * <p>
     *
     * <p>
     *
     *
     * @param collisionPoint - the point of collision.
     * @param collisionObject - the object involved in the collison.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }
    /**
     * getCollisionPoint - Return the point of the collision.
     * <p>
     *
     * <p>
     *
     *
     * @return the point of the collision.
     */
    public Point getCollisionPoint() {
        return this.collisionPoint;
    }
    /**
     * getCollisionPoint - Return the object involved in the collision.
     * <p>
     *
     * <p>
     *
     *
     * @return the object involved in the collision.
     */
    public Collidable getCollisionObject() {
        return this.collisionObject;
    }
}