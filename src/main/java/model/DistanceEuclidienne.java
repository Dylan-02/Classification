package model;

public class DistanceEuclidienne implements Distance {
    @Override
    public double distance(PointIris p1, PointIris p2) {
        double largeurPetalDiff = p1.getLargeurPetal() - p2.getLargeurPetal();
        double longueurPetalDiff = p1.getLongueurPetal() - p2.getLongueurPetal();
        double largeurSepalDiff = p1.getLargeurSepal() - p2.getLargeurSepal();
        double longueurSepalDiff = p1.getLongueurSepal() - p2.getLongueurSepal();

        return Math.sqrt(Math.pow(largeurPetalDiff, 2) + Math.pow(longueurPetalDiff, 2)
                + Math.pow(largeurSepalDiff, 2) + Math.pow(longueurSepalDiff, 2));
    }
}
