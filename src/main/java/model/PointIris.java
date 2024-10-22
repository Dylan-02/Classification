package model;

import java.util.Objects;

/**
 * Représente un point de données dans le cadre de l'analyse des caractéristiques d'une fleur d'iris.
 * Ce point est défini par des mesures (longueur et largeur des sépales et des pétales) et peut être associé
 * à une catégorie (espèce) particulière d'iris.
 */
public class PointIris {
    private Categorie categorie;
    private double longueurSepal;
    private double largeurSepal;
    private double longueurPetal;
    private double largeurPetal;

    /**
     * Constructeur avec toutes les propriétés, y compris la catégorie de la fleur d'iris.
     *
     * @param categorie      La catégorie (espèce) de la fleur d'iris.
     * @param longueurSepal  Longueur du sépal en centimètres.
     * @param largeurSepal   Largeur du sépal en centimètres.
     * @param longueurPetal  Longueur du pétale en centimètres.
     * @param largeurPetal   Largeur du pétale en centimètres.
     */
    public PointIris(Categorie categorie, double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        this.categorie = categorie;
        this.longueurSepal = longueurSepal;
        this.largeurSepal = largeurSepal;
        this.longueurPetal = longueurPetal;
        this.largeurPetal = largeurPetal;
    }

    /**
     * Constructeur sans catégorie, utile lorsque la catégorie n'est pas encore connue.
     *
     * @param longueurSepal  Longueur du sépal en centimètres.
     * @param largeurSepal   Largeur du sépal en centimètres.
     * @param longueurPetal  Longueur du pétale en centimètres.
     * @param largeurPetal   Largeur du pétale en centimètres.
     */
    public PointIris(double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        this.longueurSepal = longueurSepal;
        this.largeurSepal = largeurSepal;
        this.longueurPetal = longueurPetal;
        this.largeurPetal = largeurPetal;
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
        return longueurSepal;
    }

    /**
     * Définit la longueur du sépal.
     *
     * @param longueurSepal La longueur du sépal en centimètres.
     */
    public void setLongueurSepal(double longueurSepal) {
        this.longueurSepal = longueurSepal;
    }

    /**
     * Obtient la largeur du sépal.
     *
     * @return Largeur du sépal en centimètres.
     */
    public double getLargeurSepal() {
        return largeurSepal;
    }

    /**
     * Définit la largeur du sépal.
     *
     * @param largeurSepal La largeur du sépal en centimètres.
     */
    public void setLargeurSepal(double largeurSepal) {
        this.largeurSepal = largeurSepal;
    }

    /**
     * Obtient la longueur du pétale.
     *
     * @return Longueur du pétale en centimètres.
     */
    public double getLongueurPetal() {
        return longueurPetal;
    }

    /**
     * Définit la longueur du pétale.
     *
     * @param longueurPetal La longueur du pétale en centimètres.
     */
    public void setLongueurPetal(double longueurPetal) {
        this.longueurPetal = longueurPetal;
    }

    /**
     * Obtient la largeur du pétale.
     *
     * @return Largeur du pétale en centimètres.
     */
    public double getLargeurPetal() {
        return largeurPetal;
    }

    /**
     * Définit la largeur du pétale.
     *
     * @param largeurPetal La largeur du pétale en centimètres.
     */
    public void setLargeurPetal(double largeurPetal) {
        this.largeurPetal = largeurPetal;
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
        return Double.compare(longueurSepal, pointIris.longueurSepal) == 0 &&
                Double.compare(largeurSepal, pointIris.largeurSepal) == 0 &&
                Double.compare(longueurPetal, pointIris.longueurPetal) == 0 &&
                Double.compare(largeurPetal, pointIris.largeurPetal) == 0 &&
                categorie == pointIris.categorie;
    }

    /**
     * Retourne le code de hachage de l'objet.
     *
     * @return Code de hachage basé sur les attributs de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(categorie, longueurSepal, largeurSepal, longueurPetal, largeurPetal);
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
                ", longueurSepal=" + longueurSepal +
                ", largeurSepal=" + largeurSepal +
                ", longueurPetal=" + longueurPetal +
                ", largeurPetal=" + largeurPetal +
                '}';
    }
}