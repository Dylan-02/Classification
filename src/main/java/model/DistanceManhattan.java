package model;

public class DistanceManhattan implements Distance {
    @Override
    public double distance(PointIris p1, PointIris p2) {
        return Math.abs(p1.getLargeurPetal() - p2.getLargeurPetal()) + Math.abs(p1.getLongueurPetal() - p2.getLongueurPetal())
                + Math.abs(p1.getLargeurSepal() - p2.getLargeurSepal()) + Math.abs(p1.getLongueurSepal() - p2.getLongueurSepal());
    }
}
