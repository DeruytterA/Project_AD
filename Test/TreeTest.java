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
        Assert.assertTrue(tree.contains(16));
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(list.length, tree.size());
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
            System.out.println(integer);
            tree.remove(integer);
        }

        Assert.assertEquals(0, tree.size());
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
    }

}
