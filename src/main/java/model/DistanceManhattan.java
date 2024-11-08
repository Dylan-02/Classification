package model;

public class DistanceManhattan implements Distance {
    @Override
    public double distance(PointIris p1, PointIris p2) {
        return p1.getDifferenceLargeurPetal(p2) + p1.getDifferenceLongueurPetal(p2) +
                p1.getDifferenceLargeurSepal(p2) + p1.getDifferenceLongueurSepal(p2);
    }
}
