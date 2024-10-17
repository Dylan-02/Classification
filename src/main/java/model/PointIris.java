package model;

import java.util.Objects;

public class PointIris {
    private Categorie categorie;
    private double longueurSepal;
    private double largeurSepal;
    private double longueurPetal;
    private double largeurPetal;

    public PointIris(Categorie categorie, double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        this.categorie = categorie;
        this.longueurSepal = longueurSepal;
        this.largeurSepal = largeurSepal;
        this.longueurPetal = longueurPetal;
        this.largeurPetal = largeurPetal;
    }

    public PointIris(double longueurSepal, double largeurSepal, double longueurPetal, double largeurPetal) {
        this.longueurSepal = longueurSepal;
        this.largeurSepal = largeurSepal;
        this.longueurPetal = longueurPetal;
        this.largeurPetal = largeurPetal;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getLongueurSepal() {
        return longueurSepal;
    }

    public void setLongueurSepal(double longueurSepal) {
        this.longueurSepal = longueurSepal;
    }

    public double getLargeurSepal() {
        return largeurSepal;
    }

    public void setLargeurSepal(double largeurSepal) {
        this.largeurSepal = largeurSepal;
    }

    public double getLongueurPetal() {
        return longueurPetal;
    }

    public void setLongueurPetal(double longueurPetal) {
        this.longueurPetal = longueurPetal;
    }

    public double getLargeurPetal() {
        return largeurPetal;
    }

    public void setLargeurPetal(double largeurPetal) {
        this.largeurPetal = largeurPetal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointIris pointIris = (PointIris) o;
        return Double.compare(longueurSepal, pointIris.longueurSepal) == 0 && Double.compare(largeurSepal, pointIris.largeurSepal) == 0 && Double.compare(longueurPetal, pointIris.longueurPetal) == 0 && Double.compare(largeurPetal, pointIris.largeurPetal) == 0 && categorie == pointIris.categorie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categorie, longueurSepal, largeurSepal, longueurPetal, largeurPetal);
    }

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
