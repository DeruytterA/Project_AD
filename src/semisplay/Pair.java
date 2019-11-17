package semisplay;

import org.jetbrains.annotations.Contract;

public class Pair<T, E> {
    private T key;
    private E value;

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
