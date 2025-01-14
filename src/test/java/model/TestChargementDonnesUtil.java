package model;

import model.data.RawIrisDataFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataLoadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestChargementDonnesUtil {
    List<RawIrisDataFormat> donneesBrutes;
    IrisPoint p1, p2, p3;

    @BeforeEach
    public void setup() throws IOException {
        donneesBrutes = DataLoadUtil.loadIris("src/main/resources/model/iris.csv");
        p1 = new IrisPoint(Category.SETOSA, 5.1, 3.5, 1.4, 0.2);
        p2 = new IrisPoint(Category.VERSICOLOR, 5.9, 3.2, 4.8, 1.8);
        p3 = new IrisPoint(Category.VIRGINICA, 5.9, 3, 5.1, 1.8);
    }

    @Test
    public void should_have_loaded_data_during_setup() throws IOException {
        assertNotNull(donneesBrutes);
        assertFalse(donneesBrutes.isEmpty());
        assertEquals(150, donneesBrutes.size());
        donneesBrutes = DataLoadUtil.loadIris("src/main/resources/model/iris.csv");
        assertFalse(donneesBrutes.isEmpty());
    }

    @Test
    public void should_create_iris_points_when_given_raw_data() {
        assertEquals(p1.getPetalLength(), DataLoadUtil.createIrisPoint(donneesBrutes.get(0)).getPetalLength());
        assertEquals(p1, DataLoadUtil.createIrisPoint(donneesBrutes.get(0)));
        assertEquals(p2, DataLoadUtil.createIrisPoint(donneesBrutes.get(70)));
        assertEquals(p3, DataLoadUtil.createIrisPoint(donneesBrutes.get(donneesBrutes.size() - 1)));
    }

    @Test
    public void should_create_set_of_iris_points() {
        List<IrisPoint> listPoints = new ArrayList<>();
        p2 = new IrisPoint(Category.SETOSA, 4.9, 3, 1.4, 0.2);
        p3 = new IrisPoint(Category.SETOSA, 4.7, 3.2, 1.3, 0.2);
        listPoints.add(p1);
        listPoints.add(p2);
        listPoints.add(p3);
        assertEquals(listPoints, DataLoadUtil.createIrisPointList(donneesBrutes.subList(0, 3)));
    }
}
