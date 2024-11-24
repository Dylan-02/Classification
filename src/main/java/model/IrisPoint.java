package model;

import java.util.Objects;

/**
 * Représente un point de données dans le cadre de l'analyse des caractéristiques d'une fleur d'iris.
 * Ce point est défini par des mesures (longueur et largeur des sépales et des pétales) et peut être associé
 * à une catégorie (espèce) particulière d'iris.
 */
public class IrisPoint {
    private Category category;
    final private double SEPAL_LENGTH;
    final private double SEPAL_WIDTH;
    final private double PETAL_LENGTH;
    final private double PETAL_WIDTH;

    /**
     * Constructeur avec toutes les propriétés, y compris la catégorie de la fleur d'iris.
     *
     * @param category     La catégorie (espèce) de la fleur d'iris.
     * @param sepalLength Longueur du sépal en centimètres.
     * @param sepalWidth  Largeur du sépal en centimètres.
     * @param petalLength Longueur du pétale en centimètres.
     * @param petalWidth  Largeur du pétale en centimètres.
     */
    public IrisPoint(Category category, double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        this.category = category;
        this.SEPAL_LENGTH = sepalLength;
        this.SEPAL_WIDTH = sepalWidth;
        this.PETAL_LENGTH = petalLength;
        this.PETAL_WIDTH = petalWidth;
    }

    /**
     * Constructeur sans catégorie, utile lorsque la catégorie n'est pas encore connue.
     *
     * @param sepalLength Longueur du sépal en centimètres.
     * @param sepalWidth  Largeur du sépal en centimètres.
     * @param petalLength Longueur du pétale en centimètres.
     * @param petalWidth  Largeur du pétale en centimètres.
     */
    public IrisPoint(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        this.SEPAL_LENGTH = sepalLength;
        this.SEPAL_WIDTH = sepalWidth;
        this.PETAL_LENGTH = petalLength;
        this.PETAL_WIDTH = petalWidth;
    }

    /**
     * Obtient la catégorie (espèce) de la fleur d'iris.
     *
     * @return La catégorie de la fleur d'iris.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Définit la catégorie (espèce) de la fleur d'iris.
     *
     * @param category La catégorie à associer à ce point.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Obtient la longueur du sépal.
     *
     * @return Longueur du sépal en centimètres.
     */
    public double getSepalLength() {
        return SEPAL_LENGTH;
    }

    /**
     * Obtient la largeur du sépal.
     *
     * @return Largeur du sépal en centimètres.
     */
    public double getSepalWidth() {
        return SEPAL_WIDTH;
    }

    /**
     * Obtient la longueur du pétale.
     *
     * @return Longueur du pétale en centimètres.
     */
    public double getPetalLength() {
        return PETAL_LENGTH;
    }

    /**
     * Obtient la largeur du pétale.
     *
     * @return Largeur du pétale en centimètres.
     */
    public double getPetalWidth() {
        return PETAL_WIDTH;
    }

    /**
     * Permet de calculer la différence de longueur de Petal entre deux IrisPoint
     * @param p le point avec le quel comparer la longueur.
     * @return longueur entre les deux IrisPoint
     */
    public double getPetalLengthDifference(IrisPoint p) {
        return Math.abs(this.getPetalLength() - p.getPetalLength());
    }
    /**
     * Permet de calculer la différence de largeur de Petal entre deux IrisPoint
     * @param p le point avec le quel comparer la largeur.
     * @return largeur entre les deux IrisPoint
     */
    public double getPetalWidthDifference(IrisPoint p) {
        return Math.abs(this.getPetalWidth() - p.getPetalWidth());
    }
    /**
     * Permet de calculer la différence de largeur de Sepal entre deux IrisPoint
     * @param p le point avec le quel comparer la largeur.
     * @return largeur entre les deux IrisPoint
     */
    public double getSepalWidthDifference(IrisPoint p) {
        return Math.abs(this.getSepalWidth() - p.getSepalWidth());
    }
    /**
     * Permet de calculer la différence de longueur de Sepal entre deux IrisPoint
     * @param p le point avec le quel comparer la longueur.
     * @return longueur entre les deux IrisPoint
     */
    public double getSepalLengthDifference(IrisPoint p) {
        return Math.abs(this.getSepalLength() - p.getSepalLength());
    }

    /**
     * Compare cet objet avec un autre pour déterminer s'ils sont égaux.
     *
     * @param o L'objet à comparer avec cette instance.
     * @return {@code true} si les objets sont égaux, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IrisPoint irisPoint = (IrisPoint) o;
        return Double.compare(SEPAL_LENGTH, irisPoint.SEPAL_LENGTH) == 0 &&
                Double.compare(SEPAL_WIDTH, irisPoint.SEPAL_WIDTH) == 0 &&
                Double.compare(PETAL_LENGTH, irisPoint.PETAL_LENGTH) == 0 &&
                Double.compare(PETAL_WIDTH, irisPoint.PETAL_WIDTH) == 0 &&
                category == irisPoint.category;
    }

    /**
     * Retourne le code de hachage de l'objet.
     *
     * @return Code de hachage basé sur les attributs de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(category, SEPAL_LENGTH, SEPAL_WIDTH, PETAL_LENGTH, PETAL_WIDTH);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet.
     *
     * @return Une chaîne de caractères décrivant l'objet.
     */
    @Override
    public String toString() {
        return "IrisPoint{" +
                "category=" + category +
                ", SEPAL_LENGTH=" + SEPAL_LENGTH +
                ", SEPAL_WIDTH=" + SEPAL_WIDTH +
                ", PETAL_LENGTH=" + PETAL_LENGTH +
                ", PETAL_WIDTH=" + PETAL_WIDTH +
                '}';
    }
}