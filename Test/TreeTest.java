import Tree.SemiSplayTree;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TreeTest {

    @Test
    public void test1() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Integer list[] = {16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31};
        for (Integer integer : list) {
            tree.add(integer);
        }
        tree.remove(16);
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(list.length - 1, tree.size());
        printLevelOrder(tree);
    }

    @Test
    public void testIterator() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Integer list[] = {16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31};

        for (Integer integer : list) {
            tree.add(integer);
        }

        Integer newlist[] = new Integer[tree.size()];
        Iterator<Integer> iterator = tree.iterator();
        int i = 0;

        while (iterator.hasNext()) {
            newlist[i] = iterator.next();
            i++;
        }

        for (Integer integer : newlist) {
            tree.remove(integer);
        }

        Assert.assertEquals(0, tree.size());
        printLevelOrder(tree);
    }

    @Test
    public void testGemiddeld() {
        Random random = new Random();
        int amountToAdd = 10000;
        int amountOfTests = 100;
        int time = 0;
        for (int i = 0; i < amountOfTests; i++) {
            time += testToevogenTime(amountToAdd, random);
        }
        System.out.println("gemiddelde tijd om " + amountToAdd + " nodes toe te voegen aan de boom is " + time / amountOfTests + " microseconden");
    }

    public int testToevogenTime(int amount, Random random) {
        long startTime = System.nanoTime();
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        for (int i = 0; i < amount; i++) {
            tree.add(random.nextInt());
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return Math.toIntExact(duration / 1000);
    }


    private void printLevelOrder(@NotNull SemiSplayTree root) {
        System.out.println("size = " + root.size() + "\n");
        System.out.println("depth = " + root.depth() + "\n");
        if (root == null)
            return;
        Queue<SemiSplayTree> q = new LinkedList<>();
        q.add(root);
        while (true) {
            int nodeCount = q.size();
            if (nodeCount == 0)
                break;

            while (nodeCount > 0) {
                SemiSplayTree node = q.peek();
                System.out.print(node.value + " ");
                q.remove();
                if (node.left != null)
                    q.add(node.left);
                if (node.right != null)
                    q.add(node.right);
                nodeCount--;
            }
            System.out.println();
        }
    }
}
