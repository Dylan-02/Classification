package model;

import java.util.Objects;

/**
 * Représente un point de données dans le cadre de l'analyse des caractéristiques d'une fleur d'iris.
 * Ce point est défini par des mesures (longueur et largeur des sépales et des pétales) et peut être associé
 * à une catégorie (espèce) particulière d'iris.
 */
public class PointIris {
    private Categorie categorie;
    final private double LONGUEUR_SEPAL;
    final private double LARGEUR_SEPAL;
    final private double LONGUEUR_PETAL;
    final private double LARGEUR_PETAL;

    /**
     * Constructeur avec toutes les propriétés, y compris la catégorie de la fleur d'iris.
     *
     * @param categorie     La catégorie (espèce) de la fleur d'iris.
     * @param longueurSepal Longueur du sépal en centimètres.
     * @param largeurSepal  Largeur du sépal en centimètres.
     * @param longueurPetal Longueur du pétale en centimètres.
     * @param largeurPetal  Largeur du pétale en centimètres.
     */
    public PointIris(Categorie categorie, double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        this.categorie = categorie;
        this.LONGUEUR_SEPAL = longueurSepal;
        this.LARGEUR_SEPAL = largeurSepal;
        this.LONGUEUR_PETAL = longueurPetal;
        this.LARGEUR_PETAL = largeurPetal;
    }

    /**
     * Constructeur sans catégorie, utile lorsque la catégorie n'est pas encore connue.
     *
     * @param longueurSepal Longueur du sépal en centimètres.
     * @param largeurSepal  Largeur du sépal en centimètres.
     * @param longueurPetal Longueur du pétale en centimètres.
     * @param largeurPetal  Largeur du pétale en centimètres.
     */
    public PointIris(double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        this.LONGUEUR_SEPAL = longueurSepal;
        this.LARGEUR_SEPAL = largeurSepal;
        this.LONGUEUR_PETAL = longueurPetal;
        this.LARGEUR_PETAL = largeurPetal;
    }

    /**
     * Obtient la catégorie (espèce) de la fleur d'iris.
     *
     * @return La catégorie de la fleur d'iris.
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Définit la catégorie (espèce) de la fleur d'iris.
     *
     * @param categorie La catégorie à associer à ce point.
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Obtient la longueur du sépal.
     *
     * @return Longueur du sépal en centimètres.
     */
    public double getLongueurSepal() {
        return LONGUEUR_SEPAL;
    }

    /**
     * Obtient la largeur du sépal.
     *
     * @return Largeur du sépal en centimètres.
     */
    public double getLargeurSepal() {
        return LARGEUR_SEPAL;
    }

    /**
     * Obtient la longueur du pétale.
     *
     * @return Longueur du pétale en centimètres.
     */
    public double getLongueurPetal() {
        return LONGUEUR_PETAL;
    }

    /**
     * Obtient la largeur du pétale.
     *
     * @return Largeur du pétale en centimètres.
     */
    public double getLargeurPetal() {
        return LARGEUR_PETAL;
    }

    public double getDifferenceLongueurPetal(PointIris p) {
        return Math.abs(this.getLongueurPetal() - p.getLongueurPetal());
    }

    public double getDifferenceLargeurPetal(PointIris p) {
        return Math.abs(this.getLargeurPetal() - p.getLargeurPetal());
    }

    public double getDifferenceLargeurSepal(PointIris p) {
        return Math.abs(this.getLargeurSepal() - p.getLargeurSepal());
    }

    public double getDifferenceLongueurSepal(PointIris p) {
        return Math.abs(this.getLongueurSepal() - p.getLongueurSepal());
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
        PointIris pointIris = (PointIris) o;
        return Double.compare(LONGUEUR_SEPAL, pointIris.LONGUEUR_SEPAL) == 0 &&
                Double.compare(LARGEUR_SEPAL, pointIris.LARGEUR_SEPAL) == 0 &&
                Double.compare(LONGUEUR_PETAL, pointIris.LONGUEUR_PETAL) == 0 &&
                Double.compare(LARGEUR_PETAL, pointIris.LARGEUR_PETAL) == 0 &&
                categorie == pointIris.categorie;
    }

    /**
     * Retourne le code de hachage de l'objet.
     *
     * @return Code de hachage basé sur les attributs de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(categorie, LONGUEUR_SEPAL, LARGEUR_SEPAL, LONGUEUR_PETAL, LARGEUR_PETAL);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet.
     *
     * @return Une chaîne de caractères décrivant l'objet.
     */
    @Override
    public String toString() {
        return "PointIris{" +
                "categorie=" + categorie +
                ", longueurSepal=" + LONGUEUR_SEPAL +
                ", largeurSepal=" + LARGEUR_SEPAL +
                ", longueurPetal=" + LONGUEUR_PETAL +
                ", largeurPetal=" + LARGEUR_PETAL +
                '}';
    }
}