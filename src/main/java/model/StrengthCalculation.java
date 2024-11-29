package model;

import model.data.IrisDataSet;
import model.data.PokemonDataSet;

import java.util.ArrayList;
import java.util.List;

public class StrengthCalculation {
    public static void main(String[] args) {
        PokemonDataSet pkmnDataSet = new PokemonDataSet();
        pkmnDataSet.loadCSV(StrengthCalculation.class.getResource("/model/pokemon_train.csv").getFile());

        IrisDataSet irisDataSet = new IrisDataSet();
        irisDataSet.loadCSV(StrengthCalculation.class.getResource("/model/iris.csv").getFile());

        EuclidianDistance ed = new EuclidianDistance();
        ManhattanDistance md = new ManhattanDistance();
        NormalizedManhattanDistance nmd = new NormalizedManhattanDistance(irisDataSet.getSepalLengthAmplitude(), irisDataSet.getSepalWidthAmplitude(), irisDataSet.getPetalLengthAmplitude(), irisDataSet.getPetalWidthAmplitude(),
                pkmnDataSet.getAttackAmplitude(), pkmnDataSet.getBaseEggsStepsAmplitude(), pkmnDataSet.getCaptureRateAmplitude(), pkmnDataSet.getDefenseAmplitude(), pkmnDataSet.getExperienceGrowthAmplitude(), pkmnDataSet.getHPAmplitude(),
                pkmnDataSet.getSpAttackAmplitude(), pkmnDataSet.getSpDefenseAmplitude(), pkmnDataSet.getSpeedAmplitude());

        NormalizedEuclidianDistance ned = new NormalizedEuclidianDistance(irisDataSet.getSepalLengthAmplitude(), irisDataSet.getSepalWidthAmplitude(), irisDataSet.getPetalLengthAmplitude(), irisDataSet.getPetalWidthAmplitude(),
                pkmnDataSet.getAttackAmplitude(), pkmnDataSet.getBaseEggsStepsAmplitude(), pkmnDataSet.getCaptureRateAmplitude(), pkmnDataSet.getDefenseAmplitude(), pkmnDataSet.getExperienceGrowthAmplitude(), pkmnDataSet.getHPAmplitude(),
                pkmnDataSet.getSpAttackAmplitude(), pkmnDataSet.getSpDefenseAmplitude(), pkmnDataSet.getSpeedAmplitude());

        Distance[] distances = new Distance[]{ed, md, nmd, ned};
        KNNClassifier knn = new KNNClassifier();

        System.out.println("Robustesse Iris\n");
        calculateRobustnessForIris(irisDataSet.getPoints(), knn, distances);

        // Calculate robustness for Pokemon dataset
        System.out.println("Robustesse Pokémon\n");
        calculateRobustnessForPokemon(pkmnDataSet.getPoints(), knn, distances);
    }

    private static void calculateRobustnessForIris(List<IrisPoint> dataset, KNNClassifier knn, Distance[] distances) {
        int maxK = Math.min(31, dataset.size() / 2);
        for (Distance d : distances) {
            System.out.printf("Testing for Distance: %s (Iris Dataset)%n", d.getClass().getSimpleName());

            int bestK = 0;
            double bestAccuracy = 0;

            for (int k = 3; k <= maxK; k += 2) {
                int correctlyClassified = 0;

                for (IrisPoint pt : dataset) {
                    Category predictedCategory = classifyPointForIris(pt, dataset, knn, d, k);
                    if (pt.getCategory().equals(predictedCategory)) {
                        correctlyClassified++;
                    }
                }

                double accuracy = (double) correctlyClassified / dataset.size() * 100;
                System.out.printf("  k=%d: Accuracy=%.2f%%%n", k, accuracy);

                if (accuracy > bestAccuracy) {
                    bestAccuracy = accuracy;
                    bestK = k;
                }
            }
            System.out.printf("Best k for %s: %d with Accuracy: %.2f%%%n%n", d.getClass().getSimpleName(), bestK, bestAccuracy, knn.getBestKValue(d, dataset));
        }
    }

    private static void calculateRobustnessForPokemon(List<PokemonPoint> dataset, KNNClassifier knn, Distance[] distances) {
        int maxK = Math.min(51, dataset.size() / 2);
        for (Distance d : distances) {
            System.out.printf("Testing for Distance: %s (Pokemon Dataset)%n", d.getClass().getSimpleName());

            int bestK = 0;
            double bestAccuracy = 0;

            for (int k = 3; k <= maxK; k += 2) {
                int correctlyClassified = 0;

                for (PokemonPoint pt : dataset) {
                    CategoryPokemon predictedCategory = classifyPointForPokemon(pt, dataset, knn, d, k);
                    if (pt.getCategory().equals(predictedCategory)) {
                        correctlyClassified++;
                    }
                }

                double accuracy = (double) correctlyClassified / dataset.size() * 100;
                System.out.printf("  k=%d: Accuracy=%.2f%%%n", k, accuracy);

                if (accuracy > bestAccuracy) {
                    bestAccuracy = accuracy;
                    bestK = k;
                }
            }

            System.out.printf("Best k for %s: %d with Accuracy: %.2f%%%n%n", d.getClass().getSimpleName(), bestK, bestAccuracy, knn.getBestKValuePokemon(d, dataset));
        }
    }

    private static Category classifyPointForIris(IrisPoint pt, List<IrisPoint> dataset, KNNClassifier knn, Distance d, int k) {
        List<IrisPoint> trainingSet = new ArrayList<>(dataset);
        trainingSet.remove(pt);

        return knn.determineCategory(pt, k, d, trainingSet);
    }

    private static CategoryPokemon classifyPointForPokemon(PokemonPoint pt, List<PokemonPoint> dataset, KNNClassifier knn, Distance d, int k) {
        List<PokemonPoint> trainingSet = new ArrayList<>(dataset);
        trainingSet.remove(pt);

        return knn.determineCategory(pt, k, d, trainingSet);
    }
}
