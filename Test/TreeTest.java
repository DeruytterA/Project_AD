import semisplay.SemiSplayTree;
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
        Assert.assertEquals(true, tree.contains(16));
        printLevelOrder(tree);
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(list.length, tree.size());
    }

    //@Test
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
    }

    @Test
    public void testGemiddeld() {
        Random random = new Random();
        int amountToAdd = 1000;
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
            int toAdd = random.nextInt();
            if (!tree.contains(toAdd)){
                tree.add(toAdd);
            }
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return Math.toIntExact(duration / 1000);
    }

    @Test
    public void printTest(){
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(10);
        for (Integer value:tree) {
            System.out.println(value);
        }
        printLevelOrder(tree);
    }

    private void printLevelOrder(SemiSplayTree<Integer> root) {
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
                SemiSplayTree<Integer> node = q.peek();
                System.out.print(node.getValue() + " ");
                q.remove();
                if (node.getLeft() != null)
                    q.add(node.getLeft());
                if (node.getRight() != null)
                    q.add(node.getRight());
                nodeCount--;
            }
            System.out.println();
        }
    }

}
