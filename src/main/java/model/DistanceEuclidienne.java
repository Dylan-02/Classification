package model;

public class DistanceEuclidienne implements Distance {
    @Override
    public double distance(PointIris p1, PointIris p2) {
        return Math.pow(Math.abs(p1.getLargeurPetal()- p2.getLargeurPetal()), 2) + Math.pow(Math.abs(p1.getLongueurPetal()- p2.getLongueurPetal()), 2)
                + Math.pow(Math.abs(p1.getLargeurSepal()- p2.getLargeurSepal()), 2) + Math.pow(Math.abs(p1.getLongueurSepal()- p2.getLongueurSepal()), 2);
    }
}
