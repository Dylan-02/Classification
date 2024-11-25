package model;

/**
 * Cette class permet de normalisé les données des IrisPoint pour calculer la distance Euclidiene
 */
public class NormalizedEuclidianDistance implements Distance {
    protected double sepalLengthAmplitude;
    protected double sepalWidthAmplitude;
    protected double petalLengthAmplitude;
    protected double petalWidthAmplitude;

    /**
     * Constructeur permettant de définir les données d'un IrisPoint
     *
     * @param sepalLengthAmplitude Longueur du Sepal de l'Iris
     * @param sepalWidthAmplitude Largeur du Sepal de l'Iris
     * @param petalLengthAmplitude Longueur de la Pétal de l'Iris
     * @param petalWidthAmplitude Largeur de la Pétal de l'Iris
     */
    public NormalizedEuclidianDistance(double sepalLengthAmplitude, double sepalWidthAmplitude, double petalLengthAmplitude, double petalWidthAmplitude) {
        this.sepalLengthAmplitude = sepalLengthAmplitude;
        this.sepalWidthAmplitude = sepalWidthAmplitude;
        this.petalLengthAmplitude = petalLengthAmplitude;
        this.petalWidthAmplitude = petalWidthAmplitude;
    }

    /**
     * Permet de normalisé la distance entre IrisPoint de toutes les données.
     *
     * @param p1 IrisPoint à comparer
     * @param p2 IrisPoint a normalisé avec le premier
     * @return la distance normalisé entre les deux point (double)
     */
    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        double result = Math.pow(Math.abs((p1.getSepalLengthDifference(p2))/this.sepalLengthAmplitude), 2) + Math.pow(Math.abs((p1.getSepalWidthDifference(p2))/this.sepalWidthAmplitude), 2)
                        + Math.pow(Math.abs((p1.getPetalLengthDifference(p2))/this.petalLengthAmplitude), 2) + Math.pow(Math.abs((p1.getPetalWidthDifference(p2)/this.petalWidthAmplitude)), 2);
        return Math.sqrt(result);
    }
}