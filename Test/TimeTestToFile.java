import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import semisplay.SemiSplayTree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RunWith(ParallelRunner.class)
public class TimeTestToFile {

    private Random random;

    @Before
    public void before(){
        random = new Random();
    }

    @Test
    public void test1(){
        ArrayList<ArrayList<String>> toWrite = new ArrayList<>();  
        Integer splaySizesToTest[] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63};
        Integer amountToAdd = 1000000;
        int testsPerSplay = 50;
        for (Integer splay:splaySizesToTest) {
            Long average = 0L;
            for (int i = 0; i < testsPerSplay; i++) {
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(splay);
                HashSet<Integer> randomNumbers = new HashSet<>();
                while (randomNumbers.size() < amountToAdd){
                    int integer = random.nextInt();
                    randomNumbers.add(integer);
                }
                long start = System.currentTimeMillis();
                for (int getal:randomNumbers) {
                    tree.add(getal);
                }
                long end = System.currentTimeMillis();
                average += end-start;
            }
            average = average/testsPerSplay;
            toWrite.add(new ArrayList<>(Arrays.asList(splay.toString(),average.toString(),amountToAdd.toString())));
        }
        writetest1(toWrite, "TestOutput/addTest.csv");
        System.out.println("Test1 has been written");
    }

    public void writetest1(ArrayList<ArrayList<String>> rows, String fileName){
        try (FileWriter csvWriter = new FileWriter(fileName)){
            csvWriter.append("Splaygrootte");
            csvWriter.append(",");
            csvWriter.append("Tijd");
            csvWriter.append(",");
            csvWriter.append("Aantal elementen");
            csvWriter.append("\n");
            for (ArrayList<String> rowData : rows) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }
            csvWriter.flush();
        }catch (IOException ex){
            System.err.println("problem with writing to file");
        }
    }

    @Test
    public void test2(){
        ArrayList<ArrayList<String>> toWrite = new ArrayList<>();
        Integer amountTosearchALotArr[] = {2000,5000,7500,10000,20000,50000,100000,250000};
        for (Integer amountToSearchALot:amountTosearchALotArr) {
            Integer amountToAdd = 1000000;
            Integer splaySizesToTest[] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63};
            for (Integer splay:splaySizesToTest) {
                HashSet<Integer> searchALot = new HashSet<>();
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(splay);
                while (searchALot.size() < amountToSearchALot){
                    searchALot.add(random.nextInt());
                }
                HashSet<Integer> randomNumbers = new HashSet<>(searchALot);
                while (randomNumbers.size() < amountToAdd){
                    randomNumbers.add(random.nextInt());
                }
                for (int getal:randomNumbers) {
                    tree.add(getal);
                }
                int amount = 100;
                Long average = 0L;
                for (int i = 0; i < amount ; i++) {
                    long start  = System.currentTimeMillis();
                    for (int getal:searchALot) {
                        Assert.assertTrue(tree.contains(getal));
                    }
                    long end = System.currentTimeMillis();
                    average += end-start;
                    System.out.println(i+"th time for searching " + searchALot.size() + " elements in semisplaytree with "+amountToAdd+" elements and splaysize "+splay+": " + (end-start) + "milliseconds");
                }
                average = average/amount;
                toWrite.add(new ArrayList<>(Arrays.asList(splay.toString(),average.toString(),amountToAdd.toString(),amountToSearchALot.toString())));
            }
        }
        writetest2(toWrite, "TestOutput/searchLittle.csv");
        System.out.println("test2 has been written");
    }

    public void writetest2(ArrayList<ArrayList<String>> rows, String fileName){
        try (FileWriter csvWriter = new FileWriter(fileName)){
            csvWriter.append("Splaygrootte");
            csvWriter.append(",");
            csvWriter.append("Tijd");
            csvWriter.append(",");
            csvWriter.append("Aantal elementen");
            csvWriter.append(",");
            csvWriter.append("Aantal vaak gezochte elementen");
            csvWriter.append("\n");
            for (ArrayList<String> rowData : rows) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }
            csvWriter.flush();
        }catch (IOException ex){
            System.err.println("problem with writing to file");
        }
    }

    @Test
    public void test3(){
        ArrayList<ArrayList<String>> toWrite = new ArrayList<>();
        Integer splaySizesToTest[] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63};
        Integer amountToAdd = 1000000;
        int testsPerSplay = 50;
        for (Integer splay:splaySizesToTest) {
            Long average = 0L;
            for (int i = 0; i < testsPerSplay; i++) {
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(splay);
                HashSet<Integer> randomNumbers = new HashSet<>();
                while (randomNumbers.size() < amountToAdd){
                    int integer = random.nextInt();
                    randomNumbers.add(integer);
                }
                for (int getal:randomNumbers) {
                    tree.add(getal);
                }
                average += tree.depth();
            }
            average = average/testsPerSplay;
            toWrite.add(new ArrayList<>(Arrays.asList(splay.toString(),average.toString(),amountToAdd.toString())));
        }
        writetest1(toWrite, "TestOutput/addDepthTest.csv");
        System.out.println("Test1 has been written");
    }

    @Test
    public void test4(){
        ArrayList<ArrayList<String>> toWrite = new ArrayList<>();
        Integer amountToAdd = 1000000;
        Integer splaySizesToTest[] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63};
        for (Integer splay:splaySizesToTest) {
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(splay);
            HashSet<Integer> randomNumbers = new HashSet<>();
            while (randomNumbers.size() < amountToAdd){
                randomNumbers.add(random.nextInt());
            }
            for (int getal:randomNumbers) {
                tree.add(getal);
            }
            int amount = 100;
            Integer average = 0;
            for (int i = 0; i < amount ; i++) {
                for (int getal:randomNumbers) {
                    Assert.assertTrue(tree.contains(getal));
                }
                average += tree.depth();
            }
            average = average/amount;
            toWrite.add(new ArrayList<>(Arrays.asList(splay.toString(),average.toString(),amountToAdd.toString())));
        }
        writetest1(toWrite, "TestTestOutput/searchDepthTest");
    }

}
