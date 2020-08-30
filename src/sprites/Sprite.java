package sprites;


import biuoop.DrawSurface;
/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public interface Sprite {
    /**
     * drawOn - draw the sprite to the screen.
     * <p>
     *
     * <p>
     *
     *
     * @param d - the surface to be drawn on
     */
    void drawOn(DrawSurface d);
    /**
     * timePassed - notify the sprite that time has passed.
     * <p>
     *
     * <p>
     *
     *
     */
    void timePassed();
}