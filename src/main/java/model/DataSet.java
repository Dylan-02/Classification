package model;

import utils.ChargementDonneesUtil;
import utils.Observable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * La classe DataSet représente un ensemble de points de données de type PointIris.
 * Elle permet d'ajouter des points, de classifier ces points avec un classificateur KNN
 * et de charger des données à partir d'un fichier CSV.
 * Cette classe hérite de {@link Observable} pour notifier les observateurs en cas de changement.
 */
public class DataSet extends Observable {
    protected List<PointIris> points = new ArrayList<>();
    public List<PointIris> getPoints() {
        return points;
    }

    /**
     * Ajoute un nouveau point de données au DataSet en utilisant les valeurs des caractéristiques de l'iris.
     * Notifie les observateurs après l'ajout du point.
     *
     * @param longueurSepal  la longueur du sépale
     * @param largeurSepal   la largeur du sépale
     * @param longueurPetal  la longueur du pétale
     * @param largeurPetal   la largeur du pétale
     */
    public void ajouterPoint(double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        PointIris pt = new PointIris(longueurSepal, largeurSepal, longueurPetal, largeurPetal);
        this.points.add(pt);
        this.notifyObservers(pt);
    }

    /**
     * Ajoute une collection de points de données au DataSet et notifie les observateurs après l'ajout.
     *
     * @param listePoints collection de points de type PointIris à ajouter.
     */
    public void ajouterPoints(Collection<PointIris> listePoints) {
        this.points.addAll(listePoints);
        this.notifyObservers();
    }

    /**
     * Classifie un point de données en utilisant un classificateur KNN.
     * Notifie les observateurs après la classification.
     *
     * @param point le point de données à classifier.
     */
    public void classifierPoint(PointIris point) {
        KNNClassifier.classify(point);
        this.notifyObservers(point);
    }

    /**
     * Classifie tous les points de données présents dans le DataSet en utilisant un classificateur KNN.
     * Notifie les observateurs après chaque classification.
     */
    public void classifierPoints() {
        for (PointIris pt : this.points) classifierPoint(pt);
    }

    /**
     * Charge les données à partir d'un fichier CSV et les ajoute au DataSet.
     * Notifie les observateurs après le chargement des données.
     *
     * @param file le chemin du fichier CSV à charger.
     */
    public void loadCSV(String file) {
        try {
            List<FormatDonneeBrut> data = ChargementDonneesUtil.charger(file);
            List<PointIris> listePoints = ChargementDonneesUtil.creerEnsemblePointIris(data);
            this.ajouterPoints(listePoints);
            this.notifyObservers();
        } catch (IOException e) {
            System.out.println("Fichier introuvable");
        }
    }
}
