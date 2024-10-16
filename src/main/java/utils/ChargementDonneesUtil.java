package utils;

import com.opencsv.bean.CsvToBeanBuilder;
import model.Categorie;
import model.FormatDonneeBrut;
import model.PointIris;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ChargementDonneesUtil {
    public static List<FormatDonneeBrut> charger(String fileName) throws IOException {
        return new CsvToBeanBuilder<FormatDonneeBrut>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(FormatDonneeBrut.class)
                .build().parse();
    }

    public static PointIris creerPointIris(FormatDonneeBrut d) {
        Categorie categorie = d.getCategorie();
        double longueurPetal = d.getLongueurPetal();
        double largeurPetal = d.getLargeurPetal();
        double longueurSepal = d.getLongueurSepal();
        double largeurSepal = d.getLargeurSepal();
        return new PointIris(categorie, longueurSepal, largeurSepal, longueurPetal, largeurPetal);
    }

    public static List<PointIris> creerEnsemblePointIris(List<FormatDonneeBrut> donnees) {
        List<PointIris> resultat = new ArrayList<>();
        for (FormatDonneeBrut d : donnees) {
            resultat.add(creerPointIris(d));
        }
        return resultat;
    }

}
