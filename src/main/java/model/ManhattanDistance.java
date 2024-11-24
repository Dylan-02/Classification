package model;

public class ManhattanDistance implements Distance {
    /**
     * Calcule la distance de Manhattan entre deux points Iris.
     *
     * @param p1 le premier point de données.
     * @param p2 le second point de données.
     * @return la distance de Manhattan entre les deux points.
     */
    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        return p1.getPetalWidthDifference(p2) + p1.getPetalLengthDifference(p2) +
                p1.getSepalWidthDifference(p2) + p1.getSepalLengthDifference(p2);
    }
}
