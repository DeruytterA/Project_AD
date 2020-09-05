package semisplay;

import org.jetbrains.annotations.Contract;

/**
 *
 * Simple Pair class to hold a key and value pair.
 * The key and value are both final and cannot be edited after being instantiated.
 *
 * @param <T> the type of the key of the pair.
 * @param <E> the type of the value of the pair.
 */
public class Pair<T, E> {

    private final T key;
    private final E value;

    @Contract(pure = true)
    public Pair(T key, E value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }

}
