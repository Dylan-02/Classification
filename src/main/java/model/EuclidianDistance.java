package model;


public class EuclidianDistance implements Distance {
    /**
     * Permet de classer un point avec la distance Euclidienne
     *
     * @param p1 Représente le premier point à comparer.
     * @param p2 Représente le deuxième point à comparer.
     * @return La distance entre les deux (double).
     */
    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        double petalWidthDiff = p1.getPetalWidthDifference(p2);
        double petalLengthDiff = p1.getPetalLengthDifference(p2);
        double sepalWidthDiff = p1.getSepalWidthDifference(p2);
        double sepalLengthDiff = p1.getSepalLengthDifference(p2);

        return Math.sqrt(Math.pow(petalWidthDiff, 2) + Math.pow(petalLengthDiff, 2)
                + Math.pow(sepalWidthDiff, 2) + Math.pow(sepalLengthDiff, 2));
    }
}
