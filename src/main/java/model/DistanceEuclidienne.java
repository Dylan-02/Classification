package model;

public class DistanceEuclidienne implements Distance {
    @Override
    public double distance(PointIris p1, PointIris p2) {
        double largeurPetalDiff = p1.getDifferenceLargeurPetal(p2);
        double longueurPetalDiff = p1.getDifferenceLongueurPetal(p2);
        double largeurSepalDiff = p1.getDifferenceLargeurSepal(p2);
        double longueurSepalDiff = p1.getDifferenceLongueurSepal(p2);

        return Math.sqrt(Math.pow(largeurPetalDiff, 2) + Math.pow(longueurPetalDiff, 2)
                + Math.pow(largeurSepalDiff, 2) + Math.pow(longueurSepalDiff, 2));
    }
}
