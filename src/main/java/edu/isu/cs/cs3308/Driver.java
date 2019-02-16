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
/*
Answers to Part 1:

        Section 1:
        1.2n^3 - 7n^2 + 100n - 36 is in O(n^3)
        2n^3 - 7n^2 + 100n - 36 < (2 - 7 + 100 - 36)n^3
        59n^3 *IS* in O(n^3) for c > 59, n > 1

        2.10n + 3log(n) is in O(n)
        10n + 3log(n) < (10 + 3)n
        13n *IS* in O(n) for c > 13, n > 2 (since log(1) = 0)

        3.(1/1000)n is in O(1)
        (1/1000)n < n
        n is in O(n) for c > 1, n > 1
                n *IS NOT* in O(1)

                4.log(n)^2 + log(n)/30 is in O(log(n)^2)
                log(n)^2 = 2log(n)
                log(n)/30 = (1/30)log(n)
                2log(n) + (1/30)log(n) = (61/30)log(n)
                log(n)^2 + log(n)/30 < (61/30)log(n)
        (61/30)log(n) is in O(log(n)) for c > (61/30), n > 2
        log(n)^2 + log(n)/30 *IS NOT* in O(log(n)^2)

        5. (n^2)/log(n) + 3n is in O(n^2)
        (n^2)/log(n) + 3n < (1 + 3)n^2
        4n^2 *IS* in O(n^2) for c > 4 , n > 1



        Section 2:
        1. O(n)
        2. O(n^2)
        3. O(n^2)
        4. O(n)
        5. O(c)



        Section 3:
        1. O(n)
        2. O(n^2)
        3. O(nm), (where n is the number of books and m is the number of stars.
        If stars is a ranking then it's doubtful that the star count
        will ever be very large, so the big-O is probably more likely
        to be O(n).)
*/

/**
 * Driver class for the experimental simulator.
 * @author Isaac Griffith
 * @author Blaise Johnson
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

            System.gc();
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
