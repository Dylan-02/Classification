package main;

import model.Categorie;
import model.DataSet;
import model.PointIris;
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
    public void testAjouterPoints() throws IOException {
        assertTrue(data.getPoints().isEmpty());
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        assertFalse(data.getPoints().isEmpty());
        assertEquals(150, data.getPoints().size());
    }

    @Test
    public void testClassifierPoint() {
        PointIris p1 = new PointIris(5, 5, 5, 5);
        assertNull(p1.getCategorie());
        data.classifierPoint(p1);
        assertNotNull(p1.getCategorie());
        assertTrue(Arrays.asList(Categorie.values()).contains(p1.getCategorie()));
    }
}
