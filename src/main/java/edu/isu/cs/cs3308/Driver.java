package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.algorithms.ArraySearch;
import edu.isu.cs.cs3308.algorithms.impl.BinarySearch;
import edu.isu.cs.cs3308.algorithms.impl.LinearSearch;
import edu.isu.cs.cs3308.algorithms.impl.RecursiveBinarySearch;
import edu.isu.cs.cs3308.algorithms.impl.RecursiveLinearSearch;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;

import static java.lang.System.nanoTime;

/**
 * Driver class for the experimental simulator.
 * @author Isaac Griffith
 */
public class Driver {
    private static Random rand = new Random();
    private static long[] iterLinTimes = new long[4];
    private static long[] recLinTimes = new long[4];
    private static long[] iterBinTimes = new long[4];
    private static long[] recBinTimes = new long[4];

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        LinearSearch linearSearch = new LinearSearch();
        RecursiveBinarySearch recursiveBinarySearch = new RecursiveBinarySearch();
        RecursiveLinearSearch recursiveLinearSearch = new RecursiveLinearSearch();

        for (int i = 0; i < 4; i++) {
            int arraySize = (int)(10 * Math.pow(10, i));

            getAverageSearchTime(binarySearch, iterBinTimes, arraySize, i);
            getAverageSearchTime(linearSearch, iterLinTimes, arraySize, i);
            getAverageSearchTime(recursiveBinarySearch, recBinTimes, arraySize, i);
            getAverageSearchTime(recursiveLinearSearch,recLinTimes, arraySize, i);
        }

        report(iterLinTimes, recLinTimes, iterBinTimes, recBinTimes, 10, 10);
    }

    private static void getAverageSearchTime(ArraySearch algorithm, long[] storageArray, int arraySize, int storageIndex) {
        long totalSearchTime = 0;

        for (int i = 0; i < 2000; i++) {
            Integer[] searchArray = generateRandomArray(arraySize);
            int searchTerm = rand.nextInt(2000);

            long startTime = nanoTime();
            algorithm.search(searchArray, searchTerm);
            long endTime = nanoTime();

            totalSearchTime += (endTime - startTime);
        }

        storageArray[storageIndex] = totalSearchTime / 2000;

        System.out.println(String.format("%s algorithm has completed array size %d", algorithm.getClass().toString(), arraySize));
    }

    /**
     * Generates a random ordered array of integers of the provided size
     *
     * @param size Size of the random array
     * @return An array of the provided size of random numbers in ascending
     * order.
     */
    private static Integer[] generateRandomArray(int size) {
        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(2000);
        }

        Arrays.sort(array);
        return array;
    }

    /**
     * Generates the output to both a Comma Separated Values file called
     * "results.csv" and to the screen.
     *
     * @param iterLinTimes Array of average values for the Iterative Linear
     * Search
     * @param recLinTimes Array of average values for the Recursive Linear
     * Search
     * @param iterBinTimes Array of average values for the Iterative Binary
     * Search
     * @param recBinTimes Array of average values for the Recursive Binary
     * Search
     * @param startIncrement Start increment for array sizes
     * @param increment Increment of array sizes.
     */
    private static void report(long[] iterLinTimes, long[] recLinTimes, long[] iterBinTimes, long[] recBinTimes, int startIncrement, int increment) {
        StringBuilder file = new StringBuilder();
        StringBuilder screen = new StringBuilder();

        screen.append(String.format("N    IterLin\tRecLin\tIterBin\tRecBin%s", System.lineSeparator()));
        file.append(String.format("N,IterLin,RecLin,IterBin,RecBin%s", System.lineSeparator()));

        for (int i = 0; i < iterLinTimes.length; i++) {
            screen.append(String.format("%d %d\t%d\t%d\t%d%s", startIncrement * (int)(Math.pow(increment, i)), iterLinTimes[i], recLinTimes[i], iterBinTimes[i], recBinTimes[i], System.lineSeparator()
            ));
            file.append(String.format("%d,%d,%d,%d,%d%s", startIncrement * (int)(Math.pow(increment, i)), iterLinTimes[i], recLinTimes[i], iterBinTimes[i], recBinTimes[i], System.lineSeparator()
            ));
        }

        System.out.println(screen.toString());

        Path p = Paths.get("results.csv");
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {

        }

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(p, StandardOpenOption.CREATE, StandardOpenOption.WRITE))) {
            pw.println(file.toString());
        } catch (IOException e) {

        }
    }
}
