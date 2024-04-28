import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FindClosest {

    private PointPair closestPointPair;
    private final QuickSort quicksort = new QuickSort();

    /**
     * Constructor
     *
     * @param points --> point array
     */
    public FindClosest(Point2D.Double[] points) {
        //Sort points by X coordinate
        quicksort.sort(points, 0, points.length - 1, "compareX");
        this.closestPointPair = calculateClosestPointPair(points, 0, points.length - 1);
        //*********************************do nothing***************************************//
    }

    /**
     * Get closest Point Pair
     *
     * @return closestPointPair
     */
    public PointPair getClosestPointPair() {
        return this.closestPointPair;
    }

    /**
     * Main method for calculate and return closest point pair
     *
     * @param p          --> point array
     * @param startIndex --> First index of p[]
     * @param lastIndex  --> last index of p[]
     * @return
     */
    private PointPair calculateClosestPointPair(Point2D.Double[] p, int startIndex, int lastIndex) {
        int midPoint = (lastIndex - startIndex) / 2;
        int arrayLen = p.length;

        if (arrayLen == 3) {
            return getClosestPointPair(p[0],p[1],p[2]);
        } else if (arrayLen == 2) {
            return getClosestPointPair(p[0],p[1],null);
        }

        Point2D.Double[] leftOfArr = new Point2D.Double[p.length / 2];
        Point2D.Double[] rightOfArr = new Point2D.Double[p.length - leftOfArr.length];

        for (int i = 0; i < p.length; i++) {
            if (i < leftOfArr.length) {
                leftOfArr[i] = p[i];
            } else {
                rightOfArr[i - leftOfArr.length] = p[i];
            }
        }

        PointPair leftOne = calculateClosestPointPair(leftOfArr, 0, leftOfArr.length-1);
        PointPair rightOne = calculateClosestPointPair(rightOfArr, 0, rightOfArr.length-1);
        PointPair points = getClosestPointPair(leftOne, rightOne);

        List<Point2D.Double> stripList = new ArrayList<>();
        for (Point2D.Double point : p) {
            if (Math.abs(point.getX() - p[midPoint].getX()) < points.getDistance()) {
                stripList.add(point);
            }
        }
        Point2D.Double[] strip = stripList.toArray(new Point2D.Double[0]);
        return getClosestPointPair(points, stripClosest(strip, strip.length, points));
    }

    /**
     * calculate and return closest point pair from 3 points
     *
     * @param p1 --> point 1
     * @param p2 --> point 2
     * @param p3 --> point 3
     * @return
     */
    private PointPair getClosestPointPair(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        if (p1 == null || p2 == null) {
            return null; 
        }

        double distanceP1P2 = p1.distance(p2);
        double distanceP2P3 = p3 != null ? p2.distance(p3) : Double.MAX_VALUE;
        double distanceP1P3 = p3 != null ? p1.distance(p3) : Double.MAX_VALUE;

        if (distanceP1P2 <= distanceP2P3 && distanceP1P2 <= distanceP1P3) {
            return new PointPair(p1, p2);
        } else if (distanceP2P3 <= distanceP1P2 && distanceP2P3 <= distanceP1P3) {
            return new PointPair(p2, p3);
        } else {
            return new PointPair(p1, p3);
        }
    }

    private PointPair getClosestPointPair(PointPair p1, PointPair p2) {
        double distanceOfP1 = p1.getPoint1().distance(p1.getPoint2());
        double distanceOfP2 = p2.getPoint1().distance(p2.getPoint2());

        if (distanceOfP1 < distanceOfP2)
            return p1;
        else
            return p2;
    }

    /**
     * A utility function to find the distance between the closest points of
     * strip of given size. All points in strip[] are sorted according to
     * y coordinate. They all have an upper bound on minimum distance as d.
     * Note that this method seems to be a O(n^2) method, but it's a O(n)
     * method as the inner loop runs at most 6 times
     *
     * @param strip        --> point array
     * @param size         --> strip array element count
     * @param shortestLine --> shortest line calculated so far
     * @return --> new shortest line
     */
    private PointPair stripClosest(Point2D.Double strip[], int size, PointPair shortestLine) {
        double shortestDistance = shortestLine.getDistance();
        PointPair nearestPair = shortestLine;
        for (int i = 0; i < size; ++i)
            for (int j = i + 1; j < size && (strip[j].getY() - strip[i].getY()) < shortestDistance; ++j)
                if (strip[i].distance(strip[j]) < shortestDistance) {
                    shortestDistance = strip[i].distance(strip[j]);
                    nearestPair = new PointPair(strip[i], strip[j]);
                }
        return nearestPair;
    }
}