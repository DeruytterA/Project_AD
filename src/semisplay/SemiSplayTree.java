package semisplay;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.lang.Math;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    @Nullable
    private SemiSplayTree<E> right;

    @Nullable
    private SemiSplayTree<E> left;

    @Nullable
    private SemiSplayTree<E> parent;

    @Nullable
    private E value;

    @NotNull
    private Integer splayGrootte;

    private int size;

    @Contract(pure = true)
    public SemiSplayTree(Integer splayGrootte) {
        assert (splayGrootte >= 3);
        size = 0;
        this.splayGrootte = splayGrootte;
    }

    @Contract(pure = true)
    private SemiSplayTree(E value, Integer splayGrootte) {
        this(splayGrootte);
        this.size = 1;
        this.value = value;
        this.splayGrootte = splayGrootte;
    }

    @Contract(pure = true)
    private SemiSplayTree(E value, SemiSplayTree<E> parent, Integer splayGrootte) {
        this(splayGrootte);
        this.size = 1;
        this.parent = parent;
        this.value = value;
        this.splayGrootte = splayGrootte;
    }

    @Override
    public boolean add(E e) {
        if (value == null) {
            value = e;
            this.splay();
            this.size++;
            return true;
        }
        int cmp = e.compareTo(this.value);
        if (cmp < 0) {
            if (this.left == null) {
                this.left = new SemiSplayTree<>(e, this, splayGrootte);
                this.left.splay();
                if (parent == null){
                    size++;
                }
                return true;
            } else {
                boolean done = this.left.add(e);
                if (done && parent == null){
                    size++;
                }
                return done;
            }
        } else if (cmp > 0) {
            if (this.right == null) {
                this.right = new SemiSplayTree<>(e, this, splayGrootte);
                this.right.splay();
                if (parent == null){
                    size++;
                }
                return true;
            } else {
                boolean done = this.right.add(e);
                if (done && parent == null){
                    size++;
                }
                return done;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(E e) {
        int cmp;
        if (this.value != null) {
            cmp = e.compareTo(this.value);
        }else{
            return false;
        }
        if (cmp == 0){
            this.splay();
            return true;
        }
        if (cmp < 0){
            if (left != null){
                return this.left.contains(e);
            }else {
                return false;
            }
        }
        if (right != null){
            return this.right.contains(e);
        }else {
            return false;
        }
    }

    @Override
    public boolean remove(E e) {
        int cmp;
        if (this.value != null && e != null) {
            cmp = e.compareTo(this.value);
        }else {
            return false;
        }
        if (cmp == 0) {
            removeThis();
            if (parent == null){
                size--;
            }
            return true;
        }else if (cmp < 0) {
            if (left != null) {
                boolean done = left.remove(e);
                if (done && parent == null){
                    size--;
                }
                return done;
            } else {
                return false;
            }
        }else {
            if (right != null) {
                boolean done = right.remove(e);
                if (done && parent == null){
                    size--;
                }
                return done;
            }else {
                return false;
            }
        }
    }

    private void removeChild(@NotNull SemiSplayTree<E> child) {
        if (child.equals(right)) right = null;
        if (child.equals(left)) left = null;
    }

    private void removeThis() {
        if (right == null && left == null) {
            if (parent == null) {
                value = null;
            } else {
                parent.removeChild(this);
                parent.splay();
            }
        } else {
            boolean done;
            E grootste;
            if (left == null) {
                grootste = right.zoekKleinste();
                this.value = grootste;
                done = right.remove(grootste);
            } else {
                grootste = left.zoekGrootste();
                this.value = grootste;
                done = left.remove(grootste);
            }
        }
    }

    @Contract(pure = true)
    private E zoekKleinste() {
        if (left != null) {
            return left.zoekKleinste();
        }
        return this.value;
    }

    @Contract(pure = true)
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

    /*
    public void calculateSize() {
        int size = 1;
        if (value == null)
            size = 0;
        if (right != null) right.calculateSize();
        if (left != null) left.calculateSize();
        if (right != null) size += right.getSize();
        if (left != null) size += left.getSize();
        this.size = size;
    }
     */

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

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new SemiSplayTreeIterator<>(this);
    }

    public int getSize() {
        return size;
    }

    @Nullable public SemiSplayTree<E> getRight() {
        return right;
    }

    @Nullable public SemiSplayTree<E> getLeft() {
        return left;
    }

    @Nullable public E getValue() {
        return value;
    }

    public void setRight(SemiSplayTree<E> right) {
        this.right = right;
    }

    public void setLeft(SemiSplayTree<E> left) {
        this.left = left;
    }

    public void setParent(SemiSplayTree<E> parent) {
        this.parent = parent;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SemiSplayTree<E> getParent() {
        return parent;
    }

    public void splay(){
        if (this.value == null){
            return;
        }
        E myValue = this.value;
        SemiSplayTree<E> highestNodeToSplay = this;
        int i = 0;
        while (i < splayGrootte - 1){
            highestNodeToSplay = highestNodeToSplay.getParent();
            if (highestNodeToSplay == null || highestNodeToSplay.getValue() == null) {
                return;
            }
            i++;
        }

        Object[] sortedListOfSplayElements = new Object[splayGrootte];
        Object[] subTrees = new Object[splayGrootte+1];

        int meestLinks = 0;
        int meestRechts = splayGrootte-1;

        SemiSplayTree<E> tussenSplayToGetElements = highestNodeToSplay;
        for (int j = 0; j < splayGrootte; j++) {

            if (tussenSplayToGetElements == null || tussenSplayToGetElements.getValue() == null){
                return;
            }

            int cmp = tussenSplayToGetElements.getValue().compareTo(myValue);
            if (cmp > 0){
                sortedListOfSplayElements[meestRechts] = tussenSplayToGetElements.getValue();
                subTrees[meestRechts + 1] = tussenSplayToGetElements.getRight();
                meestRechts--;
                tussenSplayToGetElements = tussenSplayToGetElements.getLeft();
            }else if (cmp < 0){
                sortedListOfSplayElements[meestLinks] = tussenSplayToGetElements.getValue();
                subTrees[meestLinks] = tussenSplayToGetElements.getLeft();
                meestLinks++;
                tussenSplayToGetElements = tussenSplayToGetElements.getRight();
            }else {
                sortedListOfSplayElements[meestLinks] = tussenSplayToGetElements.getValue();
                subTrees[meestLinks] = tussenSplayToGetElements.getLeft();
                subTrees[meestRechts + 1] = tussenSplayToGetElements.getRight();
                break;
            }
        }
        SemiSplayTree<E> perfecteBoom = createPerfectBinaryTree(0, subTrees, sortedListOfSplayElements, 0, sortedListOfSplayElements.length - 1).getKey();

        if (highestNodeToSplay.getParent() == null){
            highestNodeToSplay.setValue(perfecteBoom.getValue());
            setChildren(highestNodeToSplay, perfecteBoom.getLeft(), perfecteBoom.getRight());
            //highestNodeToSplay.setSize(perfecteBoom.getSize()-1);
        }else {
            SemiSplayTree<E> parent = highestNodeToSplay.getParent();
            int cmp = perfecteBoom.getValue().compareTo(parent.getValue());
            if (cmp > 0){
                setChildren(parent, parent.getLeft(), perfecteBoom);
            }else {
                setChildren(parent, perfecteBoom, parent.getRight());
            }
            perfecteBoom.splay();
        }
    }

    public Pair<SemiSplayTree<E>, Integer>  createPerfectBinaryTree(int added, Object[] subTrees, @NotNull Object[] Elements, int start, int eind) {
        int index = start + (eind-start)/2;
        E ditElement = (E) Elements[index];
        if (start == eind){
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
            SemiSplayTree<E> left = (SemiSplayTree<E>) subTrees[added];
            added++;
            SemiSplayTree<E> right = (SemiSplayTree<E>) subTrees[added];
            added++;
            setChildren(parentTree, left, right);
            return new Pair<>(parentTree, added);
        }else if (start+1 == eind){
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
            SemiSplayTree<E> parentTree2 = new SemiSplayTree<>( (E) Elements[index+1],splayGrootte);
            SemiSplayTree<E> left1 = (SemiSplayTree<E>) subTrees[added];
            added++;
            SemiSplayTree<E> left2 = (SemiSplayTree<E>) subTrees[added];
            added++;
            SemiSplayTree<E> right = (SemiSplayTree<E>) subTrees[added];
            added++;
            setChildren(parentTree2, left2, right);
            setChildren(parentTree, left1, parentTree2);
            return new Pair<>(parentTree, added);
        }else{
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);

            Pair<SemiSplayTree<E>, Integer> leftPair = createPerfectBinaryTree(added, subTrees, Elements, start,index - 1);
            SemiSplayTree<E> left = leftPair.getKey();

            Pair<SemiSplayTree<E>, Integer> rightPair = createPerfectBinaryTree(leftPair.getValue(), subTrees, Elements,index + 1, eind);
            SemiSplayTree<E> right = rightPair.getKey();

            setChildren(parentTree, left, right);

            return new Pair<>(parentTree, rightPair.getValue());
        }
    }

    public void setChildren(@NotNull SemiSplayTree<E> parent, SemiSplayTree<E> left, SemiSplayTree<E> right){
        parent.setLeft(left);
        int size = 0;
        if (left != null){
            left.setParent(parent);
            size += left.getSize();
        }
        parent.setRight(right);
        if (right != null){
            right.setParent(parent);
            size += right.getSize();
        }
        if (parent.getValue() != null){
            size++;
        }
        //parent.setSize(size);
    }

}