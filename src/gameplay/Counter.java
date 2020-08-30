package gameplay;

/**
 * @author Alon Shoval <alon_shoval@hotmail.com>
 * @version 1.0
 * @since 2019-03-03
 */
public class Counter {
    //fields of Counter
    private int count;

    /**
     * Counter - construct a Counter object.
     * <p>
     *
     * <p>
     *
     */
    public Counter() {
        this.count = 0;
        return;
    }
    /**
     * increase - add number to the current count.
     * <p>
     *
     * <p>
     *
     * @param number - the amount to be added to this counter
     */
    public void increase(int number) {
        this.count = this.count + number;
        return;
    }
    /**
     * decrease - subtract number from current count.
     * <p>
     *
     * <p>
     *
     * @param number - the amount to be subtracted from this counter
     */
    public void decrease(int number) {
        this.count = this.count - number;
        return;
    }

    /**
     * getValue - return the value of this counter.
     * <p>
     *
     * <p>
     *
     * @return the count field of this counter
     */
    public int getValue() {
        return this.count;
    }
}