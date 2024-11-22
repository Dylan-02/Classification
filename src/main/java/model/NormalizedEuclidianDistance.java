package model;

public class NormalizedEuclidianDistance implements Distance {
    protected double sepalLengthAmplitude;
    protected double sepalWidthAmplitude;
    protected double petalLengthAmplitude;
    protected double petalWidthAmplitude;

    public NormalizedEuclidianDistance(double sepalLengthAmplitude, double sepalWidthAmplitude, double petalLengthAmplitude, double petalWidthAmplitude) {
        this.sepalLengthAmplitude = sepalLengthAmplitude;
        this.sepalWidthAmplitude = sepalWidthAmplitude;
        this.petalLengthAmplitude = petalLengthAmplitude;
        this.petalWidthAmplitude = petalWidthAmplitude;
    }

    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        double result = Math.pow(Math.abs((p1.getSepalLengthDifference(p2))/this.sepalLengthAmplitude), 2) + Math.pow(Math.abs((p1.getSepalWidthDifference(p2))/this.sepalWidthAmplitude), 2)
                        + Math.pow(Math.abs((p1.getPetalLengthDifference(p2))/this.petalLengthAmplitude), 2) + Math.pow(Math.abs((p1.getPetalWidthDifference(p2)/this.petalWidthAmplitude)), 2);
        return Math.sqrt(result);
    }
}
