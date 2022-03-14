import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;


class Main {
    public static List<long[]> grassPlots = new ArrayList<>();
    public static Scanner stdin;
    public static int n;
    public static int m;
    public final static long MAX_D = Long.MAX_VALUE;
    public final static long MIN_D = 1;


    public static void main(String[] args) throws Exception {
        parseInput();

        long D = binarySearch(MIN_D, MAX_D);

        PrintWriter out = new PrintWriter(new FileWriter("src/socdist.out"));
        out.println(D);
        out.close();
        stdin.close();
    }

    public static void parseInput() throws FileNotFoundException {
        stdin = new Scanner(new File("src/socdist.in"));
        n = stdin.nextInt();
        m = stdin.nextInt();

        for (int i = 0; i < m; i++) {
            long left = stdin.nextInt();
            long right = stdin.nextInt();
            long[] interval = {left, right};
            grassPlots.add(interval);
        }
    }

    public static long binarySearch(long left, long right) {
        //base case of binary search
        if (right - left <= 1) {
            return left;
        }
        //set midpoint of binary search
        long mid = ((right-left)/(long)2)+left;

        //check if mid is a valid distance for number of cows.
        if (isValidDist(mid)) {
            return binarySearch(mid, right);
        } else {
            return binarySearch(left, mid-1);
        }
    }

    public static boolean isValidDist(long d) {
        long currentPos = grassPlots.get(0)[0];
        int numCows = 1;
        for (int i = 0; i < m; i++) {
            while (currentPos + d <= grassPlots.get(i)[1]) {
                numCows++;
                currentPos += d;
            }
            while (i < m && currentPos + d > grassPlots.get(i)[1]) {
                i++;
            }
            if (i < m) {
                currentPos = Math.max(currentPos + d, grassPlots.get(i)[0]);
                numCows++;
            }
            if (numCows >= n) return true;
        }
        return false;
    }
}
