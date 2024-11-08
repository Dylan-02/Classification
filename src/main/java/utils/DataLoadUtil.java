package utils;

import com.opencsv.bean.CsvToBeanBuilder;
import model.Category;
import model.IrisPoint;
import model.RawDataFormat;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoadUtil {
    public static List<RawDataFormat> load(String fileName) throws IOException {
        return new CsvToBeanBuilder<RawDataFormat>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(RawDataFormat.class)
                .build().parse();
    }

    public static IrisPoint createIrisPoint(RawDataFormat d) {
        Category category = d.getCategory();
        double petalLength = d.getPetalLength();
        double petalWidth = d.getPetalWidth();
        double sepalLength = d.getSepalLength();
        double sepalWidth = d.getSepalWidth();
        return new IrisPoint(category, sepalLength, sepalWidth, petalLength, petalWidth);
    }

    public static List<IrisPoint> createIrisPointList(List<RawDataFormat> data) {
        List<IrisPoint> result = new ArrayList<>();
        for (RawDataFormat d : data) {
            result.add(createIrisPoint(d));
        }
        return result;
    }

}
