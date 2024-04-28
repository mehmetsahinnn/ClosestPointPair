import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class QuickSort {


    /**
     * Default Contructor
     */
    public QuickSort() {
        //Empty constructor --- do nothing
    }

    /**
     * The main function that implements QuickSort
     *
     * @param arr        --> Array to be sorted
     * @param startIndex --> First index of arr[]
     * @param lastIndex  --> Last index of arr[]
     * @param orderBy    --> compareX or compareY
     *                   compareX: sort minimum to maximum arr[] by X point
     *                   compareY: sort minimum to maximum arr[] by Y point
     */
    public void sort(Point2D.Double[] arr, int startIndex, int lastIndex, String orderBy) {
        if (startIndex < lastIndex) {
            int pivotIndex;
            if (Objects.equals(orderBy, "compareX")) {
                pivotIndex = partitionX(arr, startIndex, lastIndex);
            } else {
                pivotIndex = partitionY(arr, startIndex, lastIndex);
            }

            sort(arr, startIndex, pivotIndex - 1, orderBy);
            sort(arr, pivotIndex + 1, lastIndex, orderBy);
        }
    }

    /**
     * A utility function to swap two elements
     *
     * @param arr --> Array to be sorted
     * @param i   --> first index
     * @param j   --> second index
     */
    private void swap(Point2D.Double[] arr, int i, int j) {
        Point2D.Double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Get Median of 3 order by Point.X
     *
     * @param arr   --> Array to be sorted
     * @param left  --> First index of arr[]
     * @param right --> Last index of arr[]
     * @return
     */
    private Point2D.Double getMedianX(Point2D.Double[] arr, int left, int right) {
        int middle_X = (right + left) / 2;

        Point2D.Double[] points_X = {arr[left], arr[middle_X], arr[right]};

        Arrays.sort(points_X, Comparator.comparingDouble(Point2D.Double::getX));

        return points_X[1];
    }

    /**
     * Get Median of 3 order by Point.Y
     *
     * @param arr   --> Array to be sorted
     * @param left  --> First index of arr[]
     * @param right --> Last index of arr[]
     * @return
     */
    private Point2D.Double getMedianY(Point2D.Double[] arr, int left, int right) {
        int middle_y = (right + left) / 2;
        Point2D.Double[] points_y = {arr[left], arr[middle_y], arr[right]};
        Arrays.sort(points_y, Comparator.comparingDouble(Point2D.Double::getY));
        return points_y[1];
    }

    /**
     * This function takes median of three as pivot, places
     * the pivot element at the end of the sorted
     * array, and places all smaller (smaller than pivot)
     * to left of pivot and all greater elements to right of pivot
     * Sort order by Point.X
     *
     * @param arr        --> Array to be sorted
     * @param startIndex --> First index of arr[]
     * @param lastIndex  --> Last index of arr[]
     * @return pivot index
     */
    private int partitionX(Point2D.Double[] arr, int startIndex, int lastIndex) {
        Point2D.Double pivot = getMedianX(arr, startIndex, lastIndex);
        swap(arr, Arrays.asList(arr).indexOf(pivot), lastIndex);

        int i = startIndex;
        int j = lastIndex - 1;

        while (i <= j) {
            while (i <= j && arr[i].getX() < pivot.getX()) {
                i++;
            }
            while (i <= j && arr[j].getX() > pivot.getX()) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        swap(arr, i, lastIndex);
        return i;
    }

    /**
     * This function takes median of three as pivot, places
     * the pivot element at the end of the sorted
     * array, and places all smaller (smaller than pivot)
     * to left of pivot and all greater elements to right of pivot
     * Sort order by Point.Y
     *
     * @param arr        --> Array to be sorted
     * @param startIndex --> First index of arr[]
     * @param lastIndex  --> Last index of arr[]
     * @return pivot index
     */
    private int partitionY(Point2D.Double[] arr, int startIndex, int lastIndex) {
        Point2D.Double pivot = getMedianY(arr, startIndex, lastIndex);
        swap(arr, Arrays.asList(arr).indexOf(pivot), lastIndex);

        int i = startIndex;
        int j = lastIndex - 1;

        while (i <= j) {
            while (i <= j && arr[i].getY() < pivot.getY()) {
                i++;
            }
            while (i <= j && arr[j].getY() > pivot.getY()) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        swap(arr, i, lastIndex);
        return i;
    }

}
