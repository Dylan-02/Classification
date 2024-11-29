package model;


public class EuclidianDistance implements Distance {
    /**
     * Permet de classer un Irispoint avec la distance Euclidienne
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

    /**
     * Permet de classer un Pokemon avec la distance Euclidienne
     *
     * @param p1 Représente le premier point à comparer.
     * @param p2 Représente le deuxième point à comparer.
     * @return La distance entre les deux (double).
     */
    @Override
    public double distance(PokemonPoint p1, PokemonPoint p2) {
        double AttackDiff = p1.getAttackDifference(p2);
        double base_egg_stepsDiff = p1.getBase_egg_stepsDifference(p2);
        double capture_rateDiff = p1.getCapture_rateDifference(p2);
        double defenseDiff = p1.getDefenseDifference(p2);
        double experience_growthDiff = p1.getExperience_growthDifference(p2);
        double hpDiff = p1.getHpDifference(p2);
        double sp_attackDiff = p1.getSp_attackDifference(p2);
        double sp_defensehDiff = p1.getSp_defenseDifference(p2);
        double speedDiff = p1.getSpeedDifference(p2);

        return Math.sqrt(Math.pow(AttackDiff, 2) + Math.pow(base_egg_stepsDiff, 2)
                + Math.pow(capture_rateDiff, 2) + Math.pow(defenseDiff, 2) +
                (Math.pow(experience_growthDiff, 2) + Math.pow(hpDiff, 2)
                        + Math.pow(sp_attackDiff, 2) + Math.pow(sp_defensehDiff, 2) +Math.pow(speedDiff, 2)) );
    }
}
