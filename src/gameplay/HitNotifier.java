package gameplay;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03  */
public interface HitNotifier {
    /**
     * addHitListener - add a hit listener to a hitNotifier.
     * <p>
     *
     * <p>
     *
     *
     * @param hl - the hit listener to add to the notifier.
     */
    void addHitListener(HitListener hl);
    /**
     * removeHitListener - remove a hit listener from a hitNotifier.
     * <p>
     *
     * <p>
     *
     *
     * @param hl - the hit listener to remove from the notifier.
     */
    void removeHitListener(HitListener hl);
}