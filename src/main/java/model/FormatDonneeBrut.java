package model;

import com.opencsv.bean.CsvBindByName;

public class FormatDonneeBrut {
    @CsvBindByName(column = "variety")
    private Categorie categorie;
    @CsvBindByName(column = "sepal.length")
    private double longueurSepal;
    @CsvBindByName(column = "sepal.width")
    private double largeurSepal;
    @CsvBindByName(column = "petal.length")
    private double longueurPetal;
    @CsvBindByName(column = "petal.width")
    private double largeurPetal;

    public Categorie getCategorie() {
        return categorie;
    }

    public double getLongueurSepal() {
        return longueurSepal;
    }

    public double getLargeurSepal() {
        return largeurSepal;
    }

    public double getLongueurPetal() {
        return longueurPetal;
    }

    public double getLargeurPetal() {
        return largeurPetal;
    }
}
