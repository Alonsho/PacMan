package gameplay;


import sprites.Ball;
import sprites.Block;
import sprites.Target;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public interface HitListener {
    /**
     * hitEvent - This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that is hit by the ball
     * @param hitter the ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);

    void hitEvent(Target beingHit, Ball hitter);
}