package model;

import java.util.Random;

public class KNNClassifier {
    private static final Random RAND = new Random();
    public static void classify(PointIris point) {
        if (point.getCategorie() == null) {
            point.setCategorie(Categorie.values()[RAND.nextInt(Categorie.values().length)]);
        }
    }
}
