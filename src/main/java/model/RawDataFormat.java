package model;

import com.opencsv.bean.CsvBindByName;

public class RawDataFormat {
    @CsvBindByName(column = "variety")
    private Category category;
    @CsvBindByName(column = "sepal.length")
    private double sepalLength;
    @CsvBindByName(column = "sepal.width")
    private double sepalWidth;
    @CsvBindByName(column = "petal.length")
    private double petalLength;
    @CsvBindByName(column = "petal.width")
    private double petalWidth;

    public Category getCategory() {
        return category;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }
}
