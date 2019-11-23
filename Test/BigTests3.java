import semisplay.SemiSplayTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;

public class BigTests3 {

    @Test
    public void addMillionEllementsTimeTest(){
        int amountNumbers = 1000000;
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Random random = new Random();
        HashSet<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < amountNumbers){
            int integer = random.nextInt();
            randomNumbers.add(integer);
        }
        long start = System.currentTimeMillis();
        for (int getal:randomNumbers) {
            tree.add(getal);
        }
        long end = System.currentTimeMillis();
        System.out.println("time for addMillionElements in semisplaytree with splaysize 3: " + (end-start) + "milliseconds");
        Assert.assertEquals(amountNumbers, tree.size());
        System.out.println("Depth of tree with million elements with splaysize 3: " + tree.depth());
        Assert.assertTrue(tree.depth() < amountNumbers/2);
    }

    @Test
    public void searchInTreeTimeTest(){
        int amountNumbers = 1000000;
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Random random = new Random();
        HashSet<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < amountNumbers){
            int integer = random.nextInt();
            randomNumbers.add(integer);
        }
        for (int getal:randomNumbers) {
            tree.add(getal);
        }
        long start  = System.currentTimeMillis();
        for (int getal:randomNumbers) {
            Assert.assertTrue(tree.contains(getal));
        }
        long end = System.currentTimeMillis();
        System.out.println("time for searching " + amountNumbers + " elements in semisplaytree with splaysize 3: " + (end-start) + "milliseconds");
    }

    @Test
    public void searchSameElements(){
        int splay = 3;
        int amountToSearchALot = 100000;
        int amountNumbers = 1000000;
        Random random = new Random();
        HashSet<Integer> searchALot = new HashSet<>();
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(splay);
        while (searchALot.size() < amountToSearchALot){
            searchALot.add(random.nextInt());
        }
        HashSet<Integer> randomNumbers = new HashSet<>(searchALot);
        while (randomNumbers.size() < amountNumbers){
            randomNumbers.add(random.nextInt());
        }
        for (int getal:randomNumbers) {
            tree.add(getal);
        }
        int amount = 100;
        long average = 0;
        for (int i = 0; i < amount ; i++) {
            long start  = System.currentTimeMillis();
            for (int getal:searchALot) {
                Assert.assertTrue(tree.contains(getal));
            }
            long end = System.currentTimeMillis();
            average += end-start;
            System.out.println(i+"th time for searching " + searchALot.size() + " elements in semisplaytree with "+amountNumbers+" elements and splaysize "+splay+": " + (end-start) + "milliseconds");
        }
        average = average/amount;
        System.out.println("Average time to search "+ searchALot.size() + " elements in semisplaytree with " + amountNumbers + " elements and splaysize " + splay + ": " + average + "milliseconds");
    }
}
