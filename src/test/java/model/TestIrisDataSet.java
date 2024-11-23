package model;

import model.data.IrisDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestIrisDataSet {
    IrisDataSet data;

    @BeforeEach
    public void setup() throws IOException {
        data = new IrisDataSet();
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
        IrisPoint p1 = new IrisPoint(5, 5, 5, 5);
        assertNull(p1.getCategory());
        data.classifyPoint(p1, new EuclidianDistance(), 5);
        assertNotNull(p1.getCategory());
        assertTrue(Arrays.asList(Category.values()).contains(p1.getCategory()));
    }

    @Test
    public void testAjouterPoint() {
        assertTrue(data.getPoints().isEmpty());
        data.addPoint(1, 1, 1, 1);
        assertFalse(data.getPoints().isEmpty());
        assertEquals(1, data.getPoints().size());
    }

    @Test
    public void testClassifierPoints() {
        data.addPoint(1, 1, 1, 1);
        data.addPoint(2, 2, 2, 2);
        data.addPoint(3, 3, 3, 3);
        for (IrisPoint pt : data.getPoints()) assertNull(pt.getCategory());
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        data.classifyPoints(new EuclidianDistance(), 5);
        for (IrisPoint pt : data.getPoints()) assertNotNull(pt.getCategory());
    }
}
