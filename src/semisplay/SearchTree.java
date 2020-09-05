package semisplay;

public interface SearchTree<E extends Comparable<E>> extends Iterable<E> {

    /**
     * Adds a key to the SearchTree if that key was not present.
     *
     * @return true if the key was actually added.
     */
    boolean add(E e);

    /**
     * Search a key in the SearchTree.
     *
     * @return true if the key was found.
     */
    boolean contains(E e);

    /**
     * Delete a key from the SearchTree.
     *
     * @return true if the key was found and deleted.
     */
    boolean remove(E e);

    /**
     * @return the amount of keys in the SearchTree.
     */
    int size();

    /**
     * @return the depth of the SearchTree.
     */
    int depth();

}
