package model.data;

import model.Distance;
import model.IrisPoint;
import model.KNNClassifier;
import utils.DataLoadUtil;
import utils.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * La classe IrisDataSet représente un ensemble de points de données de type IrisPoint.
 * Elle permet d'ajouter des points, de classifier ces points avec un classificateur KNN
 * et de charger des données à partir d'un fichier CSV.
 * Cette classe hérite de {@link Observable} pour notifier les observateurs en cas de changement.
 */
public class IrisDataSet extends Observable {
    final public DataType type = DataType.IRIS;
    final protected List<IrisPoint> points = new ArrayList<>();
    final protected KNNClassifier knn = new KNNClassifier();

    public List<IrisPoint> getPoints() {
        return points;
    }

    /**
     * Ajoute un nouveau point de données au DataSet en utilisant les valeurs des caractéristiques de l'iris.
     * Notifie les observateurs après l'ajout du point.
     *
     * @param sepalLength la longueur du sépale
     * @param sepalWidth  la largeur du sépale
     * @param petalLength la longueur du pétale
     * @param petalWidth  la largeur du pétale
     */
    public void addPoint(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        IrisPoint pt = new IrisPoint(sepalLength, sepalWidth, petalLength, petalWidth);
        this.points.add(pt);
        this.notifyObservers(pt);
    }

    /**
     * Ajoute une collection de points de données au DataSet et notifie les observateurs après l'ajout.
     *
     * @param pointList collection de points de type IrisPoint à ajouter.
     */
    public void addPoints(Collection<IrisPoint> pointList) {
        this.points.addAll(pointList);
        this.notifyObservers();
    }

    private double calculateAmplitude(ToDoubleFunction<IrisPoint> extractor) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (IrisPoint pt : this.points) {
            double value = extractor.applyAsDouble(pt);
            if (value < min) min = value;
            if (value > max) max = value;
        }

        return max - min;
    }

    /**
     * Permet de récupérer l'amplitude de toutes les longueurs de sépals du DataSet.
     * @return Valeur double de l'amplitude.
     */
    public double getSepalLengthAmplitude() {
        return calculateAmplitude(IrisPoint::getSepalLength);
    }

    /**
     * Permet de récupérer l'amplitude de toutes les largeurs de sépals du DataSet.
     * @return Valeur double de l'amplitude.
     */
    public double getSepalWidthAmplitude() {
        return calculateAmplitude(IrisPoint::getSepalWidth);
    }

    /**
     * Permet de récupérer l'amplitude de toutes les longueurs de pétals du DataSet.
     * @return Valeur double de l'amplitude.
     */
    public double getPetalLengthAmplitude() {
        return calculateAmplitude(IrisPoint::getPetalLength);
    }

    /**
     * Permet de récupérer l'amplitude de toutes les largeurs de pétals du DataSet.
     * @return Valeur double de l'amplitude.
     */
    public double getPetalWidthAmplitude() {
        return calculateAmplitude(IrisPoint::getPetalWidth);
    }

    /**
     * Classifie un point de données en utilisant un classificateur KNN.
     * Notifie les observateurs après la classification.
     *
     * @param point le point de données à classifier.
     */
    public void classifyPoint(IrisPoint point, Distance distance, int k) {
        this.knn.classify(point, k, distance, this.points);
        this.notifyObservers(point);
    }

    /**
     * Classifie tous les points de données présents dans le DataSet en utilisant un classificateur KNN.
     * Notifie les observateurs après chaque classification.
     */
    public void classifyPoints(Distance distance, int k) {
        for (IrisPoint pt : this.points) classifyPoint(pt, distance, k);
    }

    /**
     * Charge les données à partir d'un fichier CSV et les ajoute au DataSet.
     * Notifie les observateurs après le chargement des données.
     *
     * @param file le chemin du fichier CSV à charger.
     */
    public void loadCSV(String file) {
        try {
            List<RawIrisDataFormat> data = DataLoadUtil.loadIris(file);
            List<IrisPoint> pointList = DataLoadUtil.createIrisPointList(data);
            this.addPoints(pointList);
            this.notifyObservers();
        } catch (IOException e) {
            System.out.println("Fichier introuvable");
        }
    }

    /**
     * Permet de récuperer le meilleur k pour calculer la distance parmi le dataSet
     * @param d distance du point
     * @return le nombre de K 
     */
    public int getBestKValue(Distance d) {
        return this.knn.getBestKValue(d, this.getPoints());
    }
}
