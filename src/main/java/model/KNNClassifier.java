package model;

import java.util.*;

public class KNNClassifier {
    public void classify(IrisPoint point, int k, Distance d, List<IrisPoint> datas) {
        point.setCategory(this.determinerCategorie(point, k, d, datas));
    }

    public IrisPoint[] kPlusProchesVoisins(int k, IrisPoint p, Distance d, List<IrisPoint> datas) {
        Map<Double, IrisPoint> distances = new HashMap<>();
        for (IrisPoint iris : datas) {
            if (!p.equals(iris)) {
                double distance = d.distance(p, iris); // Perte d'un point si deux points ont la même distance par rapport à// p.
                distances.put(distance, iris);    // Possible de remédier à ça en utilisant un treeSet, et 4 comparateurs (un pour chaque distance) et passer un comparateur en fonction du choix passé en paramètre.
            }
        }
        IrisPoint[] voisins = new IrisPoint[k];
        for (int idx = 0; idx < k; idx ++) {
            double min = Collections.min(distances.keySet());
            voisins[idx] = distances.get(min);
            distances.remove(min);
        }
        return voisins;
    }

    public Category determinerCategorie(IrisPoint p, int k, Distance d, List<IrisPoint> datas) {
        IrisPoint[] voisins = kPlusProchesVoisins(k, p, d, datas);
        HashMap<Category, Integer> nombreParCategorie = new HashMap<>();
        for (IrisPoint iris : voisins) {
            Category category = iris.getCategory();
            if (!nombreParCategorie.containsKey(category)) nombreParCategorie.put(category, 1);
            else {
                int value = nombreParCategorie.get(category);
                nombreParCategorie.put(category, value + 1);
            }
        }
        int max = 0;
        Category category = null;
        for (Category key : nombreParCategorie.keySet()) {
            if (max < nombreParCategorie.get(key)) {
                category = key;
                max = nombreParCategorie.get(key);
            }
        }
        return category;
    }
}