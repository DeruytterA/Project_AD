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
        this.value = value;
        this.splayGrootte = splayGrootte;
    }

    @Contract(pure = true)
    private SemiSplayTree(E value, SemiSplayTree<E> parent, Integer splayGrootte) {
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
    public boolean contains(E e) {
        int cmp;
        if (this.value != null) {
            cmp = e.compareTo(this.value);
        }else{
            return false;
        }
        if (cmp == 0){
            splay();
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
            splay();
            return true;
        }else if (cmp < 0) {
            if (left != null) {
                boolean done = left.remove(e);
                if (done) {
                    this.size--;
                }
                return done;
            } else {
                return false;
            }
        }else {
            if (right != null) {
                boolean done = right.remove(e);
                if (done) {
                    this.size--;
                }
                return done;
            }else {
                return false;
            }
        }
    }

    private void removeChild(SemiSplayTree<E> child) {
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

    public SemiSplayTree<E> getParent() {
        return parent;
    }

    public void splay(){
        //System.out.println("SPLAY -------------------------------------------------");
        if (this.value == null){
            return;
        }
        E myValue = this.value;
        SemiSplayTree<E> highestNodeToSplay = this;
        boolean cantSplay = false;
        int i = 0;
        //System.out.print(highestNodeToSplay.getValue() + " ");
        while (i < splayGrootte - 1){
            highestNodeToSplay = highestNodeToSplay.getParent();
            if (highestNodeToSplay == null || highestNodeToSplay.getValue() == null) {
                cantSplay = true;
                break;
            }
            //System.out.print(highestNodeToSplay.getValue() + " ");
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
        /*
        System.out.println("sorted");
        for (Object ob:sortedListOfSplayElements) {
            E e = (E) ob;
            System.out.print(e + " ");
        }
        System.out.println("myvalue: " + this.getValue());
         */
        int added = 0;
        SemiSplayTree<E> perfecteBoom = createPerfectBinaryTree(added, subTrees, sortedListOfSplayElements, 0, sortedListOfSplayElements.length - 1).getKey();
        if (highestNodeToSplay.getParent() == null){
            highestNodeToSplay.setValue(perfecteBoom.getValue());
            highestNodeToSplay.setLeft(perfecteBoom.getLeft());
            highestNodeToSplay.setRight(perfecteBoom.getRight());
        }else {
            if (highestNodeToSplay.getParent().getRight() != null && highestNodeToSplay.getParent().getRight().equals(highestNodeToSplay)){
                highestNodeToSplay.getParent().setRight(perfecteBoom);
            }else {
                highestNodeToSplay.getParent().setLeft(perfecteBoom);
            }
            perfecteBoom.setParent(highestNodeToSplay.getParent());
            highestNodeToSplay.splay();
        }
        size = calculateSize();
    }

    public Pair<SemiSplayTree<E>, Integer>  createPerfectBinaryTree(int added, Object[] subTrees, Object[] Elements, int start, int eind){
        int index = start + (eind-start)/2;
        E ditElement = (E) Elements[index];
        if (start == eind){
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
            parentTree.setLeft((SemiSplayTree<E>) subTrees[added]);
            if (parentTree.getLeft() != null){
                parentTree.getLeft().setParent(parentTree);
            }
            added++;
            parentTree.setRight((SemiSplayTree<E>) subTrees[added]);
            if (parentTree.getRight() != null){
                parentTree.getRight().setParent(parentTree);
            }
            added++;
            return new Pair<>(parentTree, added);
        }else if (start+1 == eind){
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
            SemiSplayTree<E> parentTree2 = new SemiSplayTree<>( (E) Elements[index+1],splayGrootte);
            parentTree.setRight(parentTree2);
            parentTree2.setParent(parentTree);
            parentTree.setLeft((SemiSplayTree<E>) subTrees[added]);
            if (parentTree.getLeft() != null) {
                parentTree.getLeft().setParent(parentTree);
            }
            added++;
            parentTree2.setLeft((SemiSplayTree<E>) subTrees[added]);
            if (parentTree2.getLeft() != null) {
                parentTree2.getLeft().setParent(parentTree2);
            }
            added++;
            parentTree2.setRight((SemiSplayTree<E>) subTrees[added]);
            if (parentTree2.getRight() != null) {
                parentTree2.getRight().setParent(parentTree2);
            }
            added++;
            return new Pair<>(parentTree, added);
        }
        SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement,splayGrootte);
        Pair<SemiSplayTree<E>, Integer> leftPair = createPerfectBinaryTree(added, subTrees, Elements, start,index - 1);

        assert leftPair.getKey().getRight() == null || (leftPair.getKey().getRight().getParent() != null);
        assert leftPair.getKey().getLeft() == null || (leftPair.getKey().getLeft().getParent() != null);

        Pair<SemiSplayTree<E>, Integer> rightPair = createPerfectBinaryTree(leftPair.getValue(), subTrees, Elements,index + 1, eind);

        assert rightPair.getKey().getRight() == null || (rightPair.getKey().getRight().getParent() != null);
        assert rightPair.getKey().getLeft() == null || (rightPair.getKey().getLeft().getParent() != null);

        parentTree.setRight(rightPair.getKey());
        parentTree.setLeft(leftPair.getKey());
        assert parentTree.getRight() != null;
        parentTree.getRight().setParent(parentTree);
        assert parentTree.getLeft() != null;
        parentTree.getLeft().setParent(parentTree);
        return new Pair<>(parentTree, rightPair.getValue());
    }

}