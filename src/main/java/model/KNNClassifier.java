package model;

import java.util.*;

public class KNNClassifier {
    private final static Random RAND = new Random();

    public void randomClassify(IrisPoint pt) {
        Category[] values = Category.values();
        pt.setCategory(values[RAND.nextInt(values.length)]);
    }

    public void classify(IrisPoint point, int k, Distance d, List<IrisPoint> datas) {
        point.setCategory(this.determineCategory(point, k, d, datas));
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

    public int getBestKValue(Distance d, List<IrisPoint> data) {
        int k = 3;
        double maxSuccessRate = 0;
        int bestKValue = 0;
        while (k < data.size()/2) {
            int correctClassfications = 0;
            for (IrisPoint pt : data) {
                if (determineCategory(pt, k, d, data).equals(pt.getCategory())) correctClassfications++;
            }
            double successRate = ((double)correctClassfications/data.size())*100;
            if (successRate > maxSuccessRate) {
                maxSuccessRate = successRate;
                bestKValue = k;
            }
            k+=2;
        }
        return bestKValue;
    }

    public Category determineCategory(IrisPoint p, int k, Distance d, List<IrisPoint> datas) {
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