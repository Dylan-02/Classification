package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ChargementDonneesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestChargementDonnesUtil {
    List<FormatDonneeBrut> donneesBrutes;
    PointIris p1, p2, p3;

    @BeforeEach
    public void setup() throws IOException {
        donneesBrutes = ChargementDonneesUtil.charger(getClass().getResource("/model/iris.csv").getFile());
        p1 = new PointIris(Categorie.SETOSA, 5.1, 3.5, 1.4, 0.2);
        p2 = new PointIris(Categorie.VERSICOLOR, 5.9, 3.2, 4.8, 1.8);
        p3 = new PointIris(Categorie.VIRGINICA, 5.9, 3, 5.1, 1.8);
    }

    @Test
    public void testCharger() throws IOException {
        assertNotNull(donneesBrutes);
        assertFalse(donneesBrutes.isEmpty());
        assertEquals(150, donneesBrutes.size());
        donneesBrutes = ChargementDonneesUtil.charger("");
        assertTrue(donneesBrutes.isEmpty());
    }

    @Test
    public void testCreerPointIris() {
        assertEquals(p1.getLongueurPetal(), ChargementDonneesUtil.creerPointIris(donneesBrutes.get(0)).getLongueurPetal());
        assertEquals(p1, ChargementDonneesUtil.creerPointIris(donneesBrutes.get(0)));
        assertEquals(p2, ChargementDonneesUtil.creerPointIris(donneesBrutes.get(70)));
        assertEquals(p3, ChargementDonneesUtil.creerPointIris(donneesBrutes.get(donneesBrutes.size() - 1)));
    }

    @Test
    public void testCreerEnsemblePointsIris() {
        List<PointIris> listPoints = new ArrayList<>();
        p2 = new PointIris(Categorie.SETOSA, 4.9, 3, 1.4, 0.2);
        p3 = new PointIris(Categorie.SETOSA, 4.7, 3.2, 1.3, 0.2);
        listPoints.add(p1);
        listPoints.add(p2);
        listPoints.add(p3);
        assertEquals(listPoints, ChargementDonneesUtil.creerEnsemblePointIris(donneesBrutes.subList(0, 3)));
    }
}
