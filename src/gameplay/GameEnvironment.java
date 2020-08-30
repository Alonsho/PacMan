package gameplay;

import java.util.ArrayList;
import java.util.List;
import biuoop.GUI;
import geometry.Line;
import geometry.Point;
import sprites.Collidable;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public class GameEnvironment {
    //fileds of GameEnvironment
    private List<Collidable> collidables;
    private GUI gui;
    /**
     * GameEnvironment - construct a GameEnvironment object.
     * <p>
     * Create a new list of collidable.
     * <p>
     *
     *
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    /**
     * addCollidable - add the given collidable to the environment.
     * <p>
     *
     * <p>
     *
     * @param c - the new collidable to be added to the list.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * removeCollidable - remove the given collidable from the game's game environment.
     * <p>
     *
     * <p>
     *
     * @param c - the collidable to be removed from the list.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
        return;
    }
    /**
     * getGui - returns the GUI of the game environment.
     * <p>
     *
     * <p>
     *
     * @return the GUI of the game environment.
     */
    public GUI getGui() {
        return this.gui;
    }
    /**
     * setGui - set the given GUI to the game environment.
     * <p>
     *
     * <p>
     *
     *
     * @param newGui - the GUI to be given to the game environment.
     */
    public void setGui(GUI newGui) {
        this.gui = newGui;
    }
    /**
     * getClosestCollision - return the information about the closest collision point.
     * <p>
     * if there is no collision in this step, return null.
     * <p>
     *
     * @param trajectory - the trajectory line of the balls movement in this step.
     * @return the information about the closest collision point.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closest = null;
        Collidable closeBlock = null;
        List<Collidable> collidablesCopy = new ArrayList<>(this.collidables);
      /*
      this loop goes through all blocks in the list of collidables
      and finds the closest collision point.
      */
        for (Collidable c : collidablesCopy) {
            // get the closest collision point with the current collidable. could be null.
            Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            // this condition is for finding the first actual collision that occurs.
            if (closest == null) {
                closest = collision;
                closeBlock = c;
            }
         /* once, an actual collision was found, we need to check if the next collision
         is closer to the start of the trajectory line.
         */
            if (closest != null && collision != null) {
                // check which collision point is closer to the start of the trajectory line
                if (trajectory.getStart().distance(collision) < trajectory.getStart().distance(closest)) {
                    closest = collision;
                    closeBlock = c;
                }
            }
        }
        // if there are no collisions, return null.
        if (closest == null) {
            return null;
        }
        return (new CollisionInfo(closest, closeBlock));
    }
}