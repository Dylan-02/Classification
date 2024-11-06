package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataSet {
    DataSet data;

    @BeforeEach
    public void setup() throws IOException {
        data = new DataSet();
    }

    @Test
    public void testLoadCSV() throws IOException {
        assertTrue(data.getPoints().isEmpty());
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        assertFalse(data.getPoints().isEmpty());
        assertEquals(150, data.getPoints().size());
    }

    @Test
    public void testClassifierPoint() {
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        PointIris p1 = new PointIris(5, 5, 5, 5);
        assertNull(p1.getCategorie());
        data.classifierPoint(p1, new DistanceEuclidienne(), 5);
        assertNotNull(p1.getCategorie());
        assertTrue(Arrays.asList(Categorie.values()).contains(p1.getCategorie()));
    }

    @Test
    public void testAjouterPoint() {
        assertTrue(data.getPoints().isEmpty());
        data.ajouterPoint(1, 1, 1, 1);
        assertFalse(data.getPoints().isEmpty());
        assertEquals(1, data.getPoints().size());
    }

    @Test
    public void testClassifierPoints() {
        data.ajouterPoint(1, 1, 1, 1);
        data.ajouterPoint(2, 2, 2, 2);
        data.ajouterPoint(3, 3, 3, 3);
        for (PointIris pt : data.getPoints()) assertNull(pt.getCategorie());
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        data.classifierPoints(new DistanceEuclidienne(), 5);
        for (PointIris pt : data.getPoints()) assertNotNull(pt.getCategorie());
    }
}
