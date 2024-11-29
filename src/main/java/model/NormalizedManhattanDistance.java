package model;

/**
 * Cette class permet de normalisé les données des IrisPoint pour calculer la distance Manhattan
 */
public class NormalizedManhattanDistance implements Distance {
    protected double sepalLengthAmplitude;
    protected double sepalWidthAmplitude;
    protected double petalLengthAmplitude;
    protected double petalWidthAmplitude;

    protected double attackAmplitude;
    protected double base_egg_stepsAmplitude;
    protected double capture_rateAmplitude;
    protected double defenseAmplitude;
    protected double experience_growthAmplitude;
    protected double hpAmplitude;
    protected double sp_attackAmplitude;
    protected double sp_defenseAmplitude;
    protected double speedAmplitude;

    /**
     * Constructeur permettant de définir les données d'un IrisPoint
     *
     * @param sepalLengthAmplitude Longueur du Sepal de l'Iris
     * @param sepalWidthAmplitude Largeur du Sepal de l'Iris
     * @param petalLengthAmplitude Longueur de la Pétal de l'Iris
     * @param petalWidthAmplitude Largeur de la Pétal de l'Iris
     */
    public NormalizedManhattanDistance(double sepalLengthAmplitude, double sepalWidthAmplitude, double petalLengthAmplitude, double petalWidthAmplitude, double attackAmplitude, double base_egg_stepsAmplitude, double capture_rateAmplitude, double defenseAmplitude, double experience_growthAmplitude, double hpAmplitude, double sp_attackAmplitude, double sp_defenseAmplitude, double speedAmplitude) {
        this.sepalLengthAmplitude = sepalLengthAmplitude;
        this.sepalWidthAmplitude = sepalWidthAmplitude;
        this.petalLengthAmplitude = petalLengthAmplitude;
        this.petalWidthAmplitude = petalWidthAmplitude;
        this.attackAmplitude = attackAmplitude;
        this.base_egg_stepsAmplitude = base_egg_stepsAmplitude;
        this.capture_rateAmplitude = capture_rateAmplitude;
        this.defenseAmplitude = defenseAmplitude;
        this.experience_growthAmplitude = experience_growthAmplitude;
        this.hpAmplitude = hpAmplitude;
        this.sp_attackAmplitude = sp_attackAmplitude;
        this.sp_defenseAmplitude = sp_defenseAmplitude;
        this.speedAmplitude = speedAmplitude;
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
        return (Math.abs((p1.getSepalLengthDifference(p2))/this.sepalLengthAmplitude) + Math.abs(p1.getSepalWidthDifference(p2)/this.sepalWidthAmplitude)
                + Math.abs((p1.getPetalLengthDifference(p2))/this.petalLengthAmplitude) + Math.abs((p1.getPetalWidthDifference(p2)/this.petalWidthAmplitude)));
    }

    /**
     * Permet de normalisé la distance entre pokemon de toutes les données.
     *
     * @param p1 pokemon à comparer
     * @param p2 pokemon a normalisé avec le premier
     * @return la distance normalisé entre les deux point (double)
     */
    @Override
    public double distance(PokemonPoint p1, PokemonPoint p2) {
        return (Math.abs((p1.getAttackDifference(p2))/this.attackAmplitude) + Math.abs(p1.getBase_egg_stepsDifference(p2)/this.base_egg_stepsAmplitude)
                + Math.abs((p1.getCapture_rateDifference(p2))/this.capture_rateAmplitude) + Math.abs((p1.getDefenseDifference(p2)/this.defenseAmplitude))
                + Math.abs((p1.getExperience_growthDifference(p2))/this.experience_growthAmplitude) + Math.abs(p1.getHpDifference(p2)/this.hpAmplitude)
                + Math.abs((p1.getSp_attackDifference(p2))/this.sp_attackAmplitude) + Math.abs((p1.getSp_defenseDifference(p2)/this.sp_defenseAmplitude)
                + Math.abs((p1.getSpeedDifference(p2))/this.speedAmplitude)));
    }
}
