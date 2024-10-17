package model;

import utils.ChargementDonneesUtil;
import utils.Observable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataSet extends Observable {
    protected List<PointIris> points = new ArrayList<>();

    public List<PointIris> getPoints() {
        return points;
    }

    public void ajouterPoint(double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        PointIris pt = new PointIris(longueurSepal, largeurSepal, longueurPetal, largeurPetal);
        this.points.add(pt);
        //this.classifierPoint(pt);
        this.notifyObservers(pt);
    }

    public void ajouterPoints(Collection<PointIris> listePoints) {
        this.points.addAll(listePoints);
        this.notifyObservers();
    }

    public void classifierPoint(PointIris point) {
        KNNClassifier.classify(point);
        this.notifyObservers(point);
    }

    public void classifierPoints() {
        for (PointIris pt : this.points) classifierPoint(pt);
    }

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
