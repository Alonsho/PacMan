package sprites;

import biuoop.DrawSurface;
import gameplay.*;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Image;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The type Block.
 *
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019 -03-03
 */
public class Block implements Collidable, Sprite, HitNotifier {
    //fields of Block.
    private Rectangle rec;
    private Color color;
    private Color strokeColor;
    private boolean hasStroke;
    private int counter;
    private List<HitListener> hitListeners;
    private HashMap<Integer, Color> hitToColorMap;
    private HashMap<Integer, Image> hitToImgMap;
    private Color defaultFillColor;
    private Image defaultFillImg;

    /**
     * Block - construct a Block object.
     * <p>
     * Create a new block with a given rectangle and a color.
     * <p>
     *
     * @param rec     - the rectangle to create a block from.
     * @param color   - the color of the rectangle.
     * @param counter - the amount of hits left for the block.
     */
    public Block(Rectangle rec, Color color, int counter) {
        this.rec = rec;
        this.color = color;
        this.counter = counter;
        hitListeners = new ArrayList<>();
    }

    /**
     * Instantiates a new Block.
     *
     * @param rec              the rec
     * @param counter          the counter
     * @param hitToColorMap    the hit to color map
     * @param hitToImgMap      the hit to img map
     * @param strokeColor      the stroke color
     * @param defaultFillColor the default fill color
     * @param defaultFillImg   the default fill img
     */
    public Block(Rectangle rec, int counter, HashMap<Integer, Color> hitToColorMap,
                 HashMap<Integer, Image> hitToImgMap, Color strokeColor,
                 Color defaultFillColor, Image defaultFillImg) {
        this.rec = rec;
        this.counter = counter;
        hitListeners = new ArrayList<>();
        this.hitToColorMap = hitToColorMap;
        this.hitToImgMap = hitToImgMap;
        this.strokeColor = strokeColor;
        if (strokeColor == null) {
            this.hasStroke = false;
        } else {
            this.hasStroke = true;
        }
        this.defaultFillColor = defaultFillColor;
        this.defaultFillImg = defaultFillImg;
    }
    /**
     * getCollisionRectangle - Return the "collision shape" of the object.
     * <p>
     *
     * <p>
     *
     *
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }
    /**
     * hit - Notify the object that we collided with it at collisionPoint with a given velocity.
     * <p>
     *
     * <p>
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint - the point where the collision occurs
     * @param currentVelocity - the velocity of the ball before the collision.
     * @param hitter - the ball that hits the block
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity v = currentVelocity;
      /*
      these 2 conditions check the location of the collision on the block, and the balls speed,
      adjusts the new velocity accordingly, and returns it.
      */
        if ((collisionPoint.getX() == this.rec.getLowerRight().getX() &&  v.getDx() < 0)
                || (collisionPoint.getX() == this.rec.getUpperLeft().getX() && v.getDx() > 0)) {
            v = new Velocity(-v.getDx(), v.getDy());
        }
        if ((collisionPoint.getY() == this.rec.getLowerRight().getY() && v.getDy() < 0)
                || (collisionPoint.getY() == this.rec.getUpperLeft().getY() && v.getDy() > 0)) {
            v = new Velocity(v.getDx(), -v.getDy());
        }
        // notify all the listeners about the hit
        this.notifyHit(hitter);
        // decrease 1 from the hit counter.
        if (this.counter > 0) {
            counter--;
        }
        return v;
    }
    /**
     * drawOn - draw the block on the given DrawSurface.
     * <p>
     *
     * <p>
     *
     *
     * @param surface - the surface to be drawn on
     */
    public void drawOn(DrawSurface surface) {

        if (hitToColorMap == null) {
            surface.setColor(this.color);
            /*
            draw the block on the surface, and draw its borders in black.
            casting is done to support the draw and fill methods.
            */
            surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                    (int) this.rec.getWidth(), (int) this.rec.getHeight());
            return;
        }
        if (hitToColorMap.containsKey(this.counter)) {
            //set the color to be the color of the block
            surface.setColor(this.hitToColorMap.get(this.counter));
            /*
            draw the block on the surface, and draw its borders in black.
            casting is done to support the draw and fill methods.
            */
            surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                    (int) this.rec.getWidth(), (int) this.rec.getHeight());
            if (this.counter == 0) {
                return;
            }
        } else if (hitToImgMap.containsKey(this.counter)) {
            /*
            draw the block on the surface, and draw its borders in black.
            casting is done to support the draw and fill methods.
            */
            surface.drawImage((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                    this.hitToImgMap.get(this.counter));
            if (this.counter == 0) {
                return;
            }
        } else if (this.defaultFillColor != null) {
            //set the color to be the color of the block
            surface.setColor(this.defaultFillColor);
            /*
            draw the block on the surface, and draw its borders in black.
            casting is done to support the draw and fill methods.
            */
            surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                    (int) this.rec.getWidth(), (int) this.rec.getHeight());
            if (this.counter == 0) {
                return;
            }
        } else {
            surface.drawImage((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                    this.defaultFillImg);
            if (this.counter == 0) {
                return;
            }
        }
        if (this.hasStroke) {
            surface.setColor(this.strokeColor);
            surface.drawRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                    (int) this.rec.getWidth(), (int) this.rec.getHeight());
        }

    }
    /**
     * timePassed - notify the block that time has passed.
     * <p>
     * the block doesn't do any action, because it doesn't move.
     * <p>
     *
     *
     *
     */
    public void timePassed() {
        return;
    }

    /**
     * addToGame - add the block to the gameLevel.
     * <p>
     *
     * <p>
     *
     * @param game - the gameLevel to add the block to.
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.getEnvironment().addCollidable(this);
    }



    /**
     * addHitListener - add a hit listener to this block.
     * <p>
     *
     * <p>
     *
     *
     * @param hl - the hit listener to add to this block.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
        return;
    }

    /**
     * removeHitListener - remove a hit listener from this block.
     * <p>
     *
     * <p>
     *
     *
     * @param hl - the hit listener to remove from this block.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
        return;
    }

    /**
     * notifyHit - notify all the hit listeners of this block about a hit.
     * <p>
     *
     * <p>
     *
     *
     * @param hitter - the ball that took part in the hit.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * getHitPoints - return the amount of remaining hit points of this block.
     * <p>
     *
     * <p>
     *
     * @return the counter field of this block.
     */
    public int getHitPoints() {
        return this.counter;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return (int) this.rec.getWidth();
    }
}