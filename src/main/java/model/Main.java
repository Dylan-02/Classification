package model;

public class Main {
    public static void main(String[] args) {
        DataSet ds = new DataSet();
        ds.loadCSV("src/main/resources/model/iris.csv");
        for (PointIris pi : ds.getPoints()) {
            System.out.println(pi);
        }
    }
}
