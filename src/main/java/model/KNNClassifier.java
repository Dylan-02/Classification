package model;

import java.util.*;

public class KNNClassifier {
    public void classify(IrisPoint point, int k, Distance d, List<IrisPoint> datas) {
        point.setCategory(this.determinerCategorie(point, k, d, datas));
    }

    public IrisPoint[] kPlusProchesVoisins(int k, IrisPoint p, Distance d, List<IrisPoint> datas) {
        SortedMap<Double, List<IrisPoint>> distances = new TreeMap<>();

        for (IrisPoint iris : datas) {
            if (!p.equals(iris)) {
                double distance = d.distance(p, iris);
                distances.putIfAbsent(distance, new ArrayList<>());
                distances.get(distance).add(iris);
            }
        }

        IrisPoint[] voisins = new IrisPoint[k];
        int idx = 0;

        for (Map.Entry<Double, List<IrisPoint>> entry : distances.entrySet()) {
            for (IrisPoint neighbor : entry.getValue()) {
                if (idx < k) {
                    voisins[idx++] = neighbor;
                } else {
                    break;
                }
            }
            if (idx >= k) {
                break;
            }
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