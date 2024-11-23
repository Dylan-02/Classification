package utils;

import model.Category;
import model.IrisPoint;
import model.RawDataFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataLoadUtil {
    private List<RawDataFormat> rawData;
    private IrisPoint p1, p2, p3;

    @BeforeEach
    public void setup() throws IOException {
        rawData = DataLoadUtil.load(getClass().getResource("/model/iris.csv").getFile());
        p1 = new IrisPoint(Category.SETOSA, 5.1, 3.5, 1.4, 0.2);
        p2 = new IrisPoint(Category.VERSICOLOR, 5.9, 3.2, 4.8, 1.8);
        p3 = new IrisPoint(Category.VIRGINICA, 5.9, 3, 5.1, 1.8);
    }

    @Test
    public void should_throw_exception_when_loading_non_existing_file() throws IOException {
        rawData = new ArrayList<>();
        assertNotNull(rawData);
        assertTrue(rawData.isEmpty());
        assertThrows(IOException.class, () -> rawData = DataLoadUtil.load("nonExistingFile"));
        assertTrue(rawData.isEmpty());
    }

    @Test
    public void should_create_new_point_when_given_valid_raw_data() {
        assertEquals(p1.getPetalLength(), DataLoadUtil.createIrisPoint(rawData.get(0)).getPetalLength());
        assertEquals(p1, DataLoadUtil.createIrisPoint(rawData.get(0)));
        assertEquals(p2, DataLoadUtil.createIrisPoint(rawData.get(70)));
        assertEquals(p3, DataLoadUtil.createIrisPoint(rawData.get(rawData.size() - 1)));
    }

    @Test
    public void should_create_a_points_list_when_given_valid_raw_data() {
        List<IrisPoint> listPoints = new ArrayList<>();
        p2 = new IrisPoint(Category.SETOSA, 4.9, 3, 1.4, 0.2);
        p3 = new IrisPoint(Category.SETOSA, 4.7, 3.2, 1.3, 0.2);
        listPoints.add(p1);
        listPoints.add(p2);
        listPoints.add(p3);
        assertEquals(listPoints, DataLoadUtil.createIrisPointList(rawData.subList(0, 3)));
    }
}
