package app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface extends Stage {

    //Que du visuel pour l'instant, commentaire à retirer
    public UserInterface(){

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        VBox chartBox = new VBox(chart);
        VBox sideBar = new VBox();
        HBox mainBox = new HBox(chartBox, sideBar);
        Scene scene = new Scene(mainBox);
        this.setScene(scene);
        this.setTitle("Visualisation données");
        this.show();
    }
}