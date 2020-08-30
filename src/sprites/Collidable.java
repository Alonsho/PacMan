package sprites;


import gameplay.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public interface Collidable {
    /**
     * getCollisionRectangle - Return the "collision shape" of the object.
     * <p>
     *
     * <p>
     *
     *
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * getCollisionRectangle - Notify the object that we collided with it at collisionPoint with a given velocity.
     * <p>
     *
     * <p>
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint - the point where the collision occurs
     * @param currentVelocity - the velocity of the ball before the collision.
     * @param hitter - the ball that is taking part in the collision
     * @return the new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}