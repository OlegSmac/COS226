import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

//import java.util.Hashtable;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private double deltaX(int x, int y) {
        Color right = picture.get(x + 1, y);
        Color left = picture.get(x - 1, y);
        int rx = right.getRed() - left.getRed();
        int gx = right.getGreen() - left.getGreen();
        int bx = right.getBlue() - left.getBlue();

        return rx * rx + gx * gx + bx * bx;
    }

    private double deltaY(int x, int y) {
        Color down = picture.get(x, y + 1);
        Color up = picture.get(x, y - 1);
        int ry = down.getRed() - up.getRed();
        int gy = down.getGreen() - up.getGreen();
        int by = down.getBlue() - up.getBlue();

        return ry * ry + gy * gy + by * by;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1) {
            return 1000;
        }
        double delX = deltaX(x, y);
        double delY = deltaY(x, y);
        return Math.sqrt(delX + delY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] a = new int[picture.width()];
        return a;
    }

    private int[] verticalSeam(int x, double[][] energyArray) {
        int w = picture.width();
        int h = picture.height();
        int[] seam = new int[h];
        double minSum = 0.0;
        minSum += energyArray[x][0];
        seam[0] = x;
        int idx = x;
        for (int j = 1; j < h; j++) {
            double min = Double.POSITIVE_INFINITY;
            int minIdx = x;
            for (int i = -1; i <= 1; i++) {
                if (idx + i >= 0 && idx + i < w) {
                    if (energyArray[idx + i][j] < min) {
                        min = energyArray[idx + i][j];
                        minIdx = idx + i;
                        idx = minIdx;
                        minSum += min;
                    }

                }
            }
            seam[j] = idx;
        }
        energyArray[x][0] = minSum;
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int w = picture.width();
        int h = picture.height();
        int[] a = new int[h];
        double[][] energyArray = new double[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                energyArray[i][j] = energy(i, j);
            }
        }
        double min = Double.POSITIVE_INFINITY;
        int minIdx = 0;
        for (int i = 0; i < w; i++) {
            a = verticalSeam(i, energyArray);
            if (energyArray[i][0] < min) {
                min = energyArray[i][0];
                minIdx = i;
            }
        }

        return verticalSeam(minIdx, energyArray);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }
}
