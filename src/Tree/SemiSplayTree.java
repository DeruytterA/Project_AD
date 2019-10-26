package Tree;

import java.util.Iterator;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E>{
    SemiSplayTree<E> right;
    SemiSplayTree<E> left;
    SemiSplayTree<E> parent;
    E value;

    public SemiSplayTree()  {

    }

    public SemiSplayTree(E value, SemiSplayTree<E> parent) {
        this();
        this.parent = parent;
        this.value = value;
    }

    private int splayGrootte;
    //todo assert(splayGrootte >= 3)

    @Override
    public boolean add(E e) {
        if (value == null) {
            value = e;
            return true;
        } else {
            int cmp = e.compareTo(this.value);
            if (cmp < 0) {
                if (this.left == null)
                    this.left = new SemiSplayTree<E>(e, this);
                else
                    return this.left.add(e);
                return true;
            } else if (cmp > 0) {
                if (this.right == null)
                    this.right = new SemiSplayTree<E>(e, this);
                else
                    return this.right.add(e);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean contains(E e) {
        int cmp = e.compareTo(this.value);
        if (cmp == 0) return true;

        if (cmp < 0) return this.left.contains(e);
        return this.right.contains(e);
    }

    @Override
    public boolean remove(E e) {
        int cmp = e.compareTo(this.value);
        if (cmp == 0){
            removeThis();
            return true;
        }
        if (cmp < 0) {
            if (left != null){
                return left.remove(e);
            }else{
                return false;
            }
        }
        if (right != null){
            return right.remove(e);
        }
        return false;
    }

    public void removeChild(SemiSplayTree<E> child){
        if (child.equals(right)) right = null;
        if (child.equals(left)) left = null;
    }

    private void removeThis() {
        if (right == null && left == null){
            parent.removeChild(this);
        }else{
            E grootste = zoekGrootsteLinks();
            this.value = grootste;
            remove(grootste);
        }
    }

    public E zoekGrootsteLinks(){
        if (right != null){
            return right.zoekGrootsteLinks();
        }
        return this.value;
    }

    @Override
    public int size() {
        int size = 1;
        if (right != null) size += right.size();
        if (left != null) size += left.size();
        return size;
    }

    @Override
    public int depth() {
        if (right == null){

        }
        if (left == null){

        }
        return 0;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
