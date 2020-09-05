package semisplay;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 *
 * A simple implementation of a SemiSplayTree.
 * Pay attention when using this because some functions can give StackOverflow errors
 * if the depth of the tree is too great.
 *
 * @param <E> The type that will be stored in the SemiSplayTree
 */
public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    /**
     * The right subtree, this can be a null pointer if there is none.
     */
    @Nullable
    private SemiSplayTree<E> right;

    /**
     * The left subtree, this can be a null pointer if there is none.
     */
    @Nullable
    private SemiSplayTree<E> left;

    /**
     * The parent of this subtree, this can be null if this object is the root.
     */
    @Nullable
    private SemiSplayTree<E> parent;

    /**
     * The value of the root of this subtree.
     */
    @Nullable
    private E value;

    /**
     * The size with which this class is going to splay.
     * It is not recommended but this can be different within the subtrees.
     */
    @NotNull
    private Integer splaySize;

    /**
     * The size of the subtree, this node included.
     */
    private int size;

    /**
     * Constructor for this class which only takes the splaySize.
     * This is the bare minimum for this class to function.
     * @param splaySize - The size with wich we are going to splay.
     */
    @Contract(pure = true)
    public SemiSplayTree(
            @NotNull Integer splaySize
    ) {
        assert (splaySize >= 3);
        size = 0;
        this.splaySize = splaySize;
    }
    
    @Contract(pure = true)
    private SemiSplayTree(
            @Nullable E value,
            @NotNull Integer splaySize
    ) {
        this(splaySize);
        this.size = 1;
        this.value = value;
        this.splaySize = splaySize;
    }

    @Contract(pure = true)
    private SemiSplayTree(
            @Nullable E value,
            @Nullable SemiSplayTree<E> parent,
            @NotNull Integer splaySize
    ) {
        this(splaySize);
        this.size = 1;
        this.parent = parent;
        this.value = value;
        this.splaySize = splaySize;
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
                this.left = new SemiSplayTree<>(e, this, splaySize);
                this.left.splay();
                if (parent == null) {
                    size++;
                }
                return true;
            } else {
                boolean done = this.left.add(e);
                if (done && parent == null) {
                    size++;
                }
                return done;
            }
        } else if (cmp > 0) {
            if (this.right == null) {
                this.right = new SemiSplayTree<>(e, this, splaySize);
                this.right.splay();
                if (parent == null) {
                    size++;
                }
                return true;
            } else {
                boolean done = this.right.add(e);
                if (done && parent == null) {
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
        } else {
            return false;
        }
        if (cmp == 0) {
            this.splay();
            return true;
        }
        if (cmp < 0) {
            if (left != null) {
                return this.left.contains(e);
            } else {
                return false;
            }
        }
        if (right != null) {
            return this.right.contains(e);
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(E e) {
        int cmp;
        if (this.value != null && e != null) {
            cmp = e.compareTo(this.value);
        } else {
            return false;
        }
        if (cmp == 0) {
            removeThis();
            if (parent == null) {
                size--;
            }
            return true;
        } else if (cmp < 0) {
            if (left != null) {
                boolean done = left.remove(e);
                if (done && parent == null) {
                    size--;
                }
                return done;
            } else {
                return false;
            }
        } else {
            if (right != null) {
                boolean done = right.remove(e);
                if (done && parent == null) {
                    size--;
                }
                return done;
            } else {
                return false;
            }
        }
    }

    private void removeChild(
            @NotNull SemiSplayTree<E> child
    ) {
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
            E grootste;
            if (left == null) {
                grootste = right.searchSmallest();
                this.value = grootste;
                right.remove(grootste);
            } else {
                grootste = left.searchBiggest();
                this.value = grootste;
                left.remove(grootste);
            }
        }
    }

    @Contract(pure = true)
    private E searchSmallest() {
        if (left != null) {
            return left.searchSmallest();
        }
        return this.value;
    }

    @Contract(pure = true)
    private E searchBiggest() {
        if (right != null) {
            return right.searchBiggest();
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

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new SemiSplayTreeIterator<>(this);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Nullable
    public SemiSplayTree<E> getRight() {
        return right;
    }

    public void setRight(@Nullable SemiSplayTree<E> right) {
        this.right = right;
    }

    @Nullable
    public SemiSplayTree<E> getLeft() {
        return left;
    }

    public void setLeft(@Nullable SemiSplayTree<E> left) {
        this.left = left;
    }

    @Nullable
    public E getValue() {
        return value;
    }

    public void setValue(@Nullable E value) {
        this.value = value;
    }

    public @Nullable SemiSplayTree<E> getParent() {
        return parent;
    }

    public void setParent(@Nullable SemiSplayTree<E> parent) {
        this.parent = parent;
    }

    public void splay() {
        if (this.value == null) {
            return;
        }
        E myValue = this.value;
        SemiSplayTree<E> highestNodeToSplay = this;
        int i = 0;
        while (i < splaySize - 1) {
            highestNodeToSplay = highestNodeToSplay.getParent();
            if (highestNodeToSplay == null || highestNodeToSplay.getValue() == null) {
                return;
            }
            i++;
        }

        Object[] sortedListOfSplayElements = new Object[splaySize];
        Object[] subTrees = new Object[splaySize + 1];

        int meestLinks = 0;
        int meestRechts = splaySize - 1;

        SemiSplayTree<E> tussenSplayToGetElements = highestNodeToSplay;
        for (int j = 0; j < splaySize; j++) {

            if (tussenSplayToGetElements == null || tussenSplayToGetElements.getValue() == null) {
                return;
            }

            int cmp = tussenSplayToGetElements.getValue().compareTo(myValue);
            if (cmp > 0) {
                sortedListOfSplayElements[meestRechts] = tussenSplayToGetElements.getValue();
                subTrees[meestRechts + 1] = tussenSplayToGetElements.getRight();
                meestRechts--;
                tussenSplayToGetElements = tussenSplayToGetElements.getLeft();
            } else if (cmp < 0) {
                sortedListOfSplayElements[meestLinks] = tussenSplayToGetElements.getValue();
                subTrees[meestLinks] = tussenSplayToGetElements.getLeft();
                meestLinks++;
                tussenSplayToGetElements = tussenSplayToGetElements.getRight();
            } else {
                sortedListOfSplayElements[meestLinks] = tussenSplayToGetElements.getValue();
                subTrees[meestLinks] = tussenSplayToGetElements.getLeft();
                subTrees[meestRechts + 1] = tussenSplayToGetElements.getRight();
                break;
            }
        }
        SemiSplayTree<E> perfecteBoom = createPerfectBinaryTree(0, subTrees, sortedListOfSplayElements, 0, sortedListOfSplayElements.length - 1).getKey();

        if (highestNodeToSplay.getParent() == null) {
            highestNodeToSplay.setValue(perfecteBoom.getValue());
            setChildren(highestNodeToSplay, perfecteBoom.getLeft(), perfecteBoom.getRight());
            //highestNodeToSplay.setSize(perfecteBoom.getSize()-1);
        } else {
            SemiSplayTree<E> parent = highestNodeToSplay.getParent();
            int cmp = perfecteBoom.getValue().compareTo(parent.getValue());
            if (cmp > 0) {
                setChildren(parent, parent.getLeft(), perfecteBoom);
            } else {
                setChildren(parent, perfecteBoom, parent.getRight());
            }
            perfecteBoom.splay();
        }
    }

    public Pair<SemiSplayTree<E>, Integer> createPerfectBinaryTree(
            int added,
            Object[] subTrees,
            @NotNull Object[] elements,
            int start,
            int end
    ) {
        int index = start + (end - start) / 2;
        E ditElement = (E) elements[index];
        if (start == end) {
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement, splaySize);
            SemiSplayTree<E> left = (SemiSplayTree<E>) subTrees[added];
            added++;
            SemiSplayTree<E> right = (SemiSplayTree<E>) subTrees[added];
            added++;
            setChildren(parentTree, left, right);
            return new Pair<>(parentTree, added);
        } else if (start + 1 == end) {
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement, splaySize);
            SemiSplayTree<E> parentTree2 = new SemiSplayTree<>((E) elements[index + 1], splaySize);
            SemiSplayTree<E> left1 = (SemiSplayTree<E>) subTrees[added];
            added++;
            SemiSplayTree<E> left2 = (SemiSplayTree<E>) subTrees[added];
            added++;
            SemiSplayTree<E> right = (SemiSplayTree<E>) subTrees[added];
            added++;
            setChildren(parentTree2, left2, right);
            setChildren(parentTree, left1, parentTree2);
            return new Pair<>(parentTree, added);
        } else {
            SemiSplayTree<E> parentTree = new SemiSplayTree<>(ditElement, splaySize);

            Pair<SemiSplayTree<E>, Integer> leftPair =
                    createPerfectBinaryTree(added, subTrees, elements, start, index - 1);
            SemiSplayTree<E> left = leftPair.getKey();

            Pair<SemiSplayTree<E>, Integer> rightPair =
                    createPerfectBinaryTree(leftPair.getValue(), subTrees, elements, index + 1, end);
            SemiSplayTree<E> right = rightPair.getKey();

            setChildren(parentTree, left, right);

            return new Pair<>(parentTree, rightPair.getValue());
        }
    }

    public void setChildren(@NotNull SemiSplayTree<E> parent, SemiSplayTree<E> left, SemiSplayTree<E> right) {
        parent.setLeft(left);
        if (left != null){
            left.setParent(parent);
        }
        parent.setRight(right);
        if (right != null) {
            right.setParent(parent);
        }
    }
}
