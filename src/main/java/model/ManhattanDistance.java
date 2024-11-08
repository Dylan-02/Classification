package model;

public class ManhattanDistance implements Distance {
    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        return p1.getPetalWidthDifference(p2) + p1.getPetalLengthDifference(p2) +
                p1.getSepalWidthDifference(p2) + p1.getSepalLengthDifference(p2);
    }
}
