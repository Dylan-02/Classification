package model;

public class NormalizedManhattanDistance implements Distance {
    protected double sepalLengthAmplitude;
    protected double sepalWidthAmplitude;
    protected double petalLengthAmplitude;
    protected double petalWidthAmplitude;

    public NormalizedManhattanDistance(double sepalLengthAmplitude, double sepalWidthAmplitude, double petalLengthAmplitude, double petalWidthAmplitude) {
        this.sepalLengthAmplitude = sepalLengthAmplitude;
        this.sepalWidthAmplitude = sepalWidthAmplitude;
        this.petalLengthAmplitude = petalLengthAmplitude;
        this.petalWidthAmplitude = petalWidthAmplitude;
    }

    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        return (Math.abs((p1.getSepalLengthDifference(p2))/this.sepalLengthAmplitude) + Math.abs(p1.getSepalWidthDifference(p2)/this.sepalWidthAmplitude)
                + Math.abs((p1.getPetalLengthDifference(p2))/this.petalLengthAmplitude) + Math.abs((p1.getPetalWidthDifference(p2)/this.petalWidthAmplitude)));
    }
}
