package sprites;



import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The type Sprite collection.
 *
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019 -03-03
 */
public class SpriteCollection {
    //field of sprite
    private List<Sprite> sprites;

    /**
     * SpriteCollection - construct a SpriteCollection object.
     * <p>
     * creates a list of sprites.
     * <p>
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * addSprite - add a new sprite to the sprite collection.
     * <p>
     *
     * <p>
     *
     * @param s - the new sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * removeSprite - remove a sprite from the sprite collection.
     * <p>
     *
     * <p>
     *
     * @param s - the sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
        return;
    }

    /**
     * timePassed - notify all the sprites that time has passed.
     * <p>
     *
     * <p>
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
      /*
      goes through all sprites in the collection and calls timePassed() on them.
      */
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * drawAllOn - draw all the sprites on the given draw surface.
     * <p>
     *
     * <p>
     *
     * @param d - the given draw surface to be drawn on.
     */
    public void drawAllOn(DrawSurface d) {
      /*
      goes through all sprites in the collection and calls drawOn() on them.
      */
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}