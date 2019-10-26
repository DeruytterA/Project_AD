package Tree;

import java.util.Iterator;
import java.util.Stack;

public class SemiSplayTreeIterator<E extends Comparable<E>> implements Iterator<E> {

    private Stack<SemiSplayTree<E>> stack;

    SemiSplayTreeIterator(SemiSplayTree<E> root) {
        stack = new Stack<>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public E next() {
        SemiSplayTree<E> tree = stack.pop();
        SemiSplayTree<E> rightTree = tree.right;
        while (rightTree != null) {
            stack.push(rightTree);
            rightTree = rightTree.left;
        }
        return tree.value;
    }
}
