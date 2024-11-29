package model;

public class ManhattanDistance implements Distance {
    /**
     * Calcule la distance de Manhattan entre deux points Iris.
     *
     * @param p1 le premier point de données.
     * @param p2 le second point de données.
     * @return la distance de Manhattan entre les deux points.
     */
    @Override
    public double distance(IrisPoint p1, IrisPoint p2) {
        return p1.getPetalWidthDifference(p2) + p1.getPetalLengthDifference(p2) +
                p1.getSepalWidthDifference(p2) + p1.getSepalLengthDifference(p2);
    }

    /**
     * Calcule la distance de Manhattan entre deux points Pokemon.
     *
     * @param p1 le premier point de données.
     * @param p2 le second point de données.
     * @return la distance de Manhattan entre les deux points.
     */
    
    public double distance(PokemonPoint p1, PokemonPoint p2) {
        return p1.getAttackDifference(p2) + p1.getBase_egg_stepsDifference(p2) + p1.getCapture_rateDifference(p2) + p1.getDefenseDifference(p2) + p1.getExperience_growthDifference(p2) + p1.getHpDifference(p2) + p1.getSp_attackDifference(p2) + p1.getSp_defenseDifference(p2) + p1.getSpeedDifference(p2);
    }
}
