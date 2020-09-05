import semisplay.SemiSplayTree;
import org.junit.Assert;
import org.junit.Test;

public class TreeTest {

    @Test
    public void test1() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Integer list[] = {16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31};
        for (Integer integer : list) {
            tree.add(integer);
        }
        Assert.assertTrue(tree.contains(16));
        Assert.assertEquals(list.length, tree.size());
    }

    @Test
    public void testIterator() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Integer list[] = {16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31};

        for (Integer integer : list) {
            System.out.println("tree size: " + tree.size() + " number added: " + integer);
            tree.add(integer);
        }
        System.out.println("remove");
        for (Integer integer : list) {
            boolean bool = tree.remove(integer);
            System.out.println(tree.size() + " : " + Boolean.toString(bool));
        }
        System.out.println(tree.size());
        tree.add(1);
        System.out.println(tree.getValue());
        System.out.println(tree.size());
        Assert.assertEquals(1, tree.size());
        tree.remove(1);
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

    @Test
    public void containTest(){
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
        tree.add(11);
        tree.add(12);
        tree.add(13);
        tree.add(14);
        tree.add(15);
        tree.add(16);
        tree.add(17);
        tree.add(18);
        tree.add(19);
        tree.add(20);
        tree.add(21);
        tree.add(22);
        tree.add(23);
        tree.add(24);
        tree.add(25);
        tree.add(26);
        tree.add(27);
        tree.add(28);
        tree.add(29);
        tree.add(30);
        tree.add(31);
        for (Integer value:tree) {
            System.out.println(value);
        }
        for (int i = 1; i < 32; i++) {
            Assert.assertTrue("tree must contain "+ i,tree.contains(i));
        }
    }

    @Test
    public void removeAndContainTest(){
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Integer list[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70};
        for (Integer integer : list) {
            tree.add(integer);
        }
        for (int i = 0; i < list.length; i++) {
            Assert.assertTrue("tree must contain " + i,tree.contains(i));
        }
        for (int i = 0; i < list.length; i += 2) {
            Assert.assertTrue("tree must contain " + i, tree.contains(i));
            //System.out.println("remove " + i);
            tree.remove(i);
        }
        for (int i = 1; i < list.length; i += 2){
            Assert.assertTrue("tree must contain " + i, tree.contains(i));
            //System.out.println("remove " + i);
            tree.remove(i);
        }
    }

    @Test
    public void sizeTest(){
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Assert.assertEquals(0, tree.size());
        tree.add(1);
        Assert.assertEquals(1, tree.size());
        tree.remove(1);
        Assert.assertEquals(0, tree.size());
        tree.add(1);
        Assert.assertEquals(1, tree.size());
        tree.add(2);
        Assert.assertEquals(2, tree.size());
        tree.add(3);
        Assert.assertEquals(3, tree.size());
    }

    @Test
    public void containTest2(){
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Assert.assertFalse(tree.contains(1));
        tree.add(1);
        Assert.assertTrue(tree.contains(1));
        tree.add(2);
        Assert.assertTrue(tree.contains(1) && tree.contains(2));
        tree.add(3);
        Assert.assertTrue(tree.contains(1) && tree.contains(2) && tree.contains(3));
    }
}
