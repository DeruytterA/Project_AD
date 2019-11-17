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
    public SemiSplayTree(@NotNull Integer splayGrootte) {
        assert (splayGrootte >= 3);
        size = 0;
        this.splayGrootte = splayGrootte;
    }

    @Contract(pure = true)
    private SemiSplayTree(@NotNull E value, @NotNull Integer splayGrootte) {
        this(splayGrootte);
        this.value = value;
        this.splayGrootte = splayGrootte;
    }

    @Contract(pure = true)
    private SemiSplayTree(@NotNull E value, @NotNull SemiSplayTree<E> parent, @NotNull Integer splayGrootte) {
        this(splayGrootte);
        this.parent = parent;
        this.value = value;
        this.splayGrootte = splayGrootte;
    }

    @Override
    public boolean add(E e) {
        if (value == null) {
            value = e;
            size++;
            splay();
            return true;
        }
        int cmp = e.compareTo(this.value);
        if (cmp < 0) {
            if (this.left == null) {
                this.left = new SemiSplayTree<>(e, this, splayGrootte);
                size++;
                splay();
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
                splay();
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
    public boolean contains(@NotNull E e) {
        int cmp;
        if (this.value != null) {
            cmp = e.compareTo(this.value);
        }else{
            return false;
        }
        if (cmp == 0){
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
    public boolean remove(@NotNull E e) {
        int cmp;
        if (this.value != null) {
            cmp = e.compareTo(this.value);
        }else {
            return false;
        }
        if (cmp == 0) {
            removeThis();
            splay();
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

    private void removeChild(@NotNull SemiSplayTree<E> child) {
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

    public int calculateSize() {
        int size = 1;
        if (right != null) size += right.calculateSize();
        if (left != null) size += left.calculateSize();
        return size;
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
        System.out.println("diepte links:");
        System.out.println(depth_left);
        System.out.println("diepte rechts:");
        System.out.println(depth_right);
        if (parent != null) {
            System.out.println("return value:");
            System.out.println(Math.max(depth_left, depth_right) + 1);
            return Math.max(depth_left, depth_right) + 1;
        } else {
            System.out.println("Parent NULL return value:");
            System.out.println(Math.max(depth_left, depth_right));
            return Math.max(depth_left, depth_right);
        }
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new SemiSplayTreeIterator<>(this);
    }

    @Nullable public SemiSplayTree<E> getRight() {
        return right;
    }

    @Nullable public SemiSplayTree<E> getLeft() {
        return left;
    }

    @Nullable
    public SemiSplayTree<E> getParent() {
        return parent;
    }

    @Nullable public E getValue() {
        return value;
    }

    public void splay(){
        if (value == null){
            return;
        }
        E myValue = this.value;
        SemiSplayTree<E> highestNodeToSplay = this;
        boolean cantSplay = false;
        int i = 0;
        while (i < splayGrootte - 1){
            highestNodeToSplay = highestNodeToSplay.parent;
            if (highestNodeToSplay == null || highestNodeToSplay.value == null) {
                cantSplay = true;
                break;
            }
            System.out.print(highestNodeToSplay.value + " ");
            i++;
        }
        System.out.println();
        if (cantSplay){
            return;
        }
        Object[] sortedListOfSplayElements = new Object[splayGrootte];
        Object[] subTrees = new Object[splayGrootte+1];
        int meestLinks = 0;
        int meestRechts = splayGrootte-1;
        SemiSplayTree<E> tussenSplayToGetElements = highestNodeToSplay;
        for (int j = 0; j < splayGrootte; j++) {

            if (tussenSplayToGetElements == null || tussenSplayToGetElements.value == null){
                return;
            }

            int cmp = tussenSplayToGetElements.value.compareTo(myValue);
            if (cmp < 0){
                sortedListOfSplayElements[meestRechts] = tussenSplayToGetElements.value;
                subTrees[meestRechts + 1] = tussenSplayToGetElements.right;
                meestRechts--;
                tussenSplayToGetElements = tussenSplayToGetElements.left;
            }else if (cmp > 0){
                sortedListOfSplayElements[meestLinks] = tussenSplayToGetElements.value;
                subTrees[meestLinks] = tussenSplayToGetElements.left;
                meestLinks++;
                tussenSplayToGetElements = tussenSplayToGetElements.right;
            }else {
                sortedListOfSplayElements[meestLinks] = tussenSplayToGetElements.value;
                subTrees[meestLinks] = tussenSplayToGetElements.left;
                subTrees[meestRechts + 1] = tussenSplayToGetElements.right;
                break;
            }
        }
        int added = 0;
        SemiSplayTree<E> perfecteBoom = createPerfectBinaryTree(added, subTrees, sortedListOfSplayElements, 0, sortedListOfSplayElements.length - 1).getKey();
        if (highestNodeToSplay.parent == null){
            highestNodeToSplay.value = perfecteBoom.value;
            highestNodeToSplay.left = perfecteBoom.left;
            highestNodeToSplay.right = perfecteBoom.right;
        }else {
            if (highestNodeToSplay.parent.right != null && highestNodeToSplay.parent.right.equals(highestNodeToSplay)){
                highestNodeToSplay.parent.right = perfecteBoom;
            }else{
                highestNodeToSplay.parent.left = perfecteBoom;
            }
            highestNodeToSplay.splay();
        }
        size = calculateSize();
    }

    public Pair<SemiSplayTree<E>, Integer>  createPerfectBinaryTree(int added, Object[] subTrees, Object[] Elements, int start, int eind){
        int index = start + (eind-start)/2;
        E ditElement = (E) Elements[index];
        if (start == eind){
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
            parentTree.left = (SemiSplayTree<E>) subTrees[added];
            if (parentTree.left != null){
                parentTree.left.parent = parentTree;
            }
            added++;
            parentTree.right = (SemiSplayTree<E>) subTrees[added];
            if (parentTree.right != null){
                parentTree.right.parent = parentTree;
            }
            added++;
            return new Pair<>(parentTree, added);
        }else if (start+1 == eind){
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
            SemiSplayTree<E> parentTree2 = new SemiSplayTree<>( (E) Elements[index+1],splayGrootte);
            parentTree.right = parentTree2;
            parentTree2.parent = parentTree;
            parentTree.left =(SemiSplayTree<E>) subTrees[added];
            parentTree.left.parent = parentTree;
            added++;
            parentTree2.left = (SemiSplayTree<E>) subTrees[added];
            parentTree2.left.parent = parentTree2;
            added++;
            parentTree2.right = (SemiSplayTree<E>) subTrees[added];
            parentTree2.right.parent = parentTree2;
            added++;
            return new Pair<>(parentTree, added);
        }
        SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
        Pair<SemiSplayTree<E>, Integer> leftPair = createPerfectBinaryTree(added, subTrees, Elements, start,index - 1);
        Pair<SemiSplayTree<E>, Integer> rightPair = createPerfectBinaryTree(leftPair.getValue(), subTrees, Elements,index + 1, eind);
        parentTree.right = rightPair.getKey();
        parentTree.left = leftPair.getKey();
        parentTree.right.parent = parentTree;
        parentTree.left.parent = parentTree;
        return new Pair<>(parentTree, rightPair.getValue());
    }

}