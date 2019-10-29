package Tree;

import java.util.Iterator;
import java.lang.Math;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    public SemiSplayTree<E> right;
    public SemiSplayTree<E> left;
    private SemiSplayTree<E> parent;

    public E value;
    private int splayGrootte;
    private int size;

    public SemiSplayTree(int splayGrootte) {
        assert (splayGrootte >= 3);
        size = 0;
        this.splayGrootte = splayGrootte;
    }

    private SemiSplayTree(E value, SemiSplayTree<E> parent, int splayGrootte) {
        this(splayGrootte);
        this.parent = parent;
        this.value = value;
    }

    @Override
    public boolean add(E e) {
        if (value == null) {
            value = e;
            size++;
            return true;
        }
        int cmp = e.compareTo(this.value);
        if (cmp < 0) {
            if (this.left == null) {
                this.left = new SemiSplayTree<>(e, this, splayGrootte);
                return true;
            } else {
                boolean done = this.left.add(e);
                if (done)
                    size++;
                return done;
            }
        } else if (cmp > 0) {
            if (this.right == null) {
                this.right = new SemiSplayTree<>(e, this, splayGrootte);
                size++;
                return true;
            } else {
                boolean done = this.right.add(e);
                if (done)
                    size++;
                return done;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean contains( E e) {
        int cmp = e.compareTo(this.value);
        if (cmp == 0) return true;

        if (cmp < 0) return this.left.contains(e);
        return this.right.contains(e);
    }

    @Override
    public boolean remove( E e) {
        int cmp = e.compareTo(this.value);
        if (cmp == 0) {
            removeThis();
            return true;
        }
        if (cmp < 0) {
            if (left != null) {
                boolean done = left.remove(e);
                if (done) {
                    this.size--;
                }
                return done;
            } else {
                return false;
            }
        }
        if (right != null) {
            boolean done = right.remove(e);
            if (done) {
                this.size--;
            }
            return done;
        }
        return false;
    }

    private void removeChild( SemiSplayTree<E> child) {
        if (child.equals(right)) right = null;
        if (child.equals(left)) left = null;
    }

    private void removeThis() {
        if (right == null && left == null) {
            if (parent == null) {
                value = null;
                this.size--;
            } else {
                parent.removeChild(this);
            }
        } else {
            E grootste;
            if (left == null) {
                grootste = right.zoekKleinste();
            } else {
                grootste = left.zoekGrootste();
            }
            remove(grootste);
            this.value = grootste;
        }
    }

    private E zoekKleinste() {
        if (left != null) {
            return left.zoekKleinste();
        }
        return this.value;
    }

    private E zoekGrootste() {
        if (right != null) {
            return right.zoekGrootste();
        }
        return this.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int depth() {
        int depth_right = 0;
        int depth_left = 0;
        if (right != null) {
            depth_right = right.depth();
        }
        if (left != null) {
            depth_left = left.depth();
        }
        if (parent != null) {
            return Math.max(depth_left, depth_right) + 1;
        } else {
            return Math.max(depth_left, depth_right);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new SemiSplayTreeIterator<>(this);
    }
}