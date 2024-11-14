package utils;

import com.opencsv.bean.CsvToBeanBuilder;
import model.Category;
import model.IrisPoint;
import model.RawIrisDataFormat;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoadUtil {
    public static List<RawIrisDataFormat> load(String fileName) throws IOException {
        return new CsvToBeanBuilder<RawIrisDataFormat>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(RawIrisDataFormat.class)
                .build().parse();
    }

    public static IrisPoint createIrisPoint(RawIrisDataFormat d) {
        Category category = d.getCategory();
        double petalLength = d.getPetalLength();
        double petalWidth = d.getPetalWidth();
        double sepalLength = d.getSepalLength();
        double sepalWidth = d.getSepalWidth();
        return new IrisPoint(category, sepalLength, sepalWidth, petalLength, petalWidth);
    }

    public static List<IrisPoint> createIrisPointList(List<RawIrisDataFormat> data) {
        List<IrisPoint> result = new ArrayList<>();
        for (RawIrisDataFormat d : data) {
            result.add(createIrisPoint(d));
        }
        return result;
    }

}
