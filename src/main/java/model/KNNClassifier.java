package model;

import java.util.*;

public class KNNClassifier {
    public void classify(PointIris point, int k, Distance d, List<PointIris> datas) {
        point.setCategorie(this.determinerCategorie(point, k, d, datas));
    }

    public PointIris[] kPlusProchesVoisins(int k, PointIris p, Distance d, List<PointIris> datas) {
        Map<Double, PointIris> distances = new HashMap<>();
        for (PointIris iris : datas) {
            if (!p.equals(iris)) {
                double distance = d.distance(p, iris); // Perte d'un joueur si deux joueurs ont la même distance par rapport a j.
                distances.put(distance, iris);    // Possible de remedier à ca en utilisant 4 un treeSet, et 4 comparateurs (un pour chaque distance) et passé un comparateur en fonction du choix passé en parametre
            }
        }
        PointIris[] voisins = new PointIris[k];
        for (int idx = 0; idx < k; idx ++) {
            double min = Collections.min(distances.keySet());
            voisins[idx] = distances.get(min);
            distances.remove(min);
        }
        return voisins;
    }

    public Categorie determinerCategorie(PointIris p, int k, Distance d, List<PointIris> datas) {
        PointIris[] voisins = kPlusProchesVoisins(k, p, d, datas);
        HashMap<Categorie, Integer> nombreParCategorie = new HashMap<>();
        for (PointIris iris : voisins) {
            Categorie categorie = iris.getCategorie();
            if (!nombreParCategorie.containsKey(categorie)) nombreParCategorie.put(categorie, 1);
            else {
                int value = nombreParCategorie.get(categorie);
                nombreParCategorie.put(categorie, value + 1);
            }
        }
        int max = 0;
        Categorie categorie = null;
        for (Categorie key : nombreParCategorie.keySet()) {
            if (max < nombreParCategorie.get(key)) {
                categorie = key;
                max = nombreParCategorie.get(key);
            }
        }
        return categorie;
    }
}