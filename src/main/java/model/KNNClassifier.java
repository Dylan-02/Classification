package model;

import java.util.*;

public class KNNClassifier {
    private final static Random RAND = new Random();

    /**
     * Associe une catégorie aléatoire à un point donné.
     *
     * @param pt le point à classifier aléatoirement.
     */
    public void randomClassify(IrisPoint pt) {
        Category[] values = Category.values();
        pt.setCategory(values[RAND.nextInt(values.length)]);
    }

    /**
     * Classifie un point donné en utilisant l'algorithme des K plus proches voisins.
     *
     * @param point le point à classifier.
     * @param k le nombre de voisins à considérer.
     * @param d la distance à utiliser pour mesurer la proximité (euclidienne, manhattan).
     * @param datas la liste des points de données déjà classifiés.
     */
    public void classify(IrisPoint point, int k, Distance d, List<IrisPoint> datas) {
        point.setCategory(this.determineCategory(point, k, d, datas));
    }

    /**
     * Trouve les K plus proches voisins d'un point donné en fonction d'une distance.
     *
     * @param k le nombre de voisins à rechercher.
     * @param p le point pour lequel trouver les voisins.
     * @param d la distance à utiliser pour mesurer la proximité.
     * @param datas la liste des points de données à examiner.
     * @return un tableau contenant les K plus proches voisins.
     */
    public IrisPoint[] getKNearestNeighbors(int k, IrisPoint p, Distance d, List<IrisPoint> datas) {
        SortedMap<Double, List<IrisPoint>> distances = new TreeMap<>();

        for (IrisPoint iris : datas) {
            if (!p.equals(iris) && iris.getCategory() != null) {
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

    /**
     * Calcule la meilleure valeur de K pour maximiser le taux de classification correct.
     *
     * @param d la distance à utiliser pour mesurer la proximité.
     * @param data la liste des points de données à utiliser pour le calcul.
     * @return la valeur optimale de K.
     */
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

    public int getBestKValue(Distance d, List<PokemonPoint> data) {
        int k = 3;
        double maxSuccessRate = 0;
        int bestKValue = 0;
        while (k < data.size()/2) {
            int correctClassfications = 0;
            for (PokemonPoint pt : data) {
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

    /**
     * Détermine la catégorie d'un point donné en fonction des K plus proches voisins.
     *
     * @param p le point à classifier.
     * @param k le nombre de voisins à considérer.
     * @param d la distance à utiliser pour mesurer la proximité.
     * @param datas la liste des points de données à examiner.
     * @return la catégorie déterminée pour le point.
     */
    public Category determineCategory(IrisPoint p, int k, Distance d, List<IrisPoint> datas) {
        IrisPoint[] neighbors = getKNearestNeighbors(k, p, d, datas);
        HashMap<Category, Integer> numberPerCategory = new HashMap<>();
        for (IrisPoint iris : neighbors) {
            Category category = iris.getCategory();
            if (!numberPerCategory.containsKey(category)) numberPerCategory.put(category, 1);
            else {
                int value = numberPerCategory.get(category);
                numberPerCategory.put(category, value + 1);
            }
        }
        int max = 0;
        Category category = null;
        for (Category key : numberPerCategory.keySet()) {
            int count = numberPerCategory.get(key);
            if (count > max) {
                category = key;
                max = count;
            }
        }
        return category;
    }
}