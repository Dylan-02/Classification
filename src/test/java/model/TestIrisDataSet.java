package model;

import model.data.IrisDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataLoadUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestIrisDataSet {
    public static final int IRIS_FILE_SIZE = 150; // numbers of lines in the iris.csv file
    private IrisDataSet data;

    @BeforeEach
    public void setup() throws IOException {
        data = new IrisDataSet();
    }

    @Test
    public void should_print_message_when_loading_non_existent_file() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        data.loadCSV("nonExistingFile");
        assertEquals("Fichier introuvable\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void should_load_data_when_given_a_valid_CSV_file() throws IOException {
        assertTrue(data.getPoints().isEmpty());
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        assertFalse(data.getPoints().isEmpty());
        assertEquals(IRIS_FILE_SIZE, data.getPoints().size());
    }

    @Test
    public void should_classify_point_when_its_category_is_null() {
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        IrisPoint p1 = new IrisPoint(5, 5, 5, 5);
        assertNull(p1.getCategory());
        data.classifyPoint(p1, new EuclidianDistance(), 5);
        assertNotNull(p1.getCategory());
        assertTrue(Arrays.asList(Category.values()).contains(p1.getCategory()));
    }

    @Test
    public void should_add_point_when_given_data() {
        assertTrue(data.getPoints().isEmpty());
        data.addPoint(1, 1, 1, 1);
        assertFalse(data.getPoints().isEmpty());
        assertEquals(1, data.getPoints().size());
    }

    @Test
    public void should_classify_all_points_having_a_null_category() {
        data.addPoint(1, 1, 1, 1);
        data.addPoint(2, 2, 2, 2);
        data.addPoint(3, 3, 3, 3);
        for (IrisPoint pt : data.getPoints()) assertNull(pt.getCategory());
        data.loadCSV(getClass().getResource("/model/iris.csv").getFile());
        data.classifyPoints(new EuclidianDistance(), 5);
        for (IrisPoint pt : data.getPoints()) assertNotNull(pt.getCategory());
    }
}
