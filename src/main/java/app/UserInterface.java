package app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface extends Stage {

    //Que du visuel pour l'instant, commentaire à retirer
    public UserInterface(){

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        TextField filePathTf = new TextField("Sélectionner fichier");
        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        Button boutonClassifier = new Button("Classifier");
        VBox chartBox = new VBox(filePathTf, chart, boutonClassifier);

        Text axeDesAbscisses = new Text("Axe des abscisses");
        ComboBox<String> menuDeroulantAbscisses = new ComboBox<>(); //TODO à l'implémentaiton des données, CHANGER LE TYPE GéNéRIQUE DES COMBOBOX
        Text axeDesOrdonnees = new Text("Axe des ordonnées");
        ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();
        VBox sideBar = new VBox(axeDesAbscisses, menuDeroulantAbscisses, axeDesOrdonnees, menuDeroulantOrdonnees);
        HBox mainBox = new HBox(chartBox, sideBar);
        Scene scene = new Scene(mainBox);
        this.setScene(scene);
        this.setTitle("Visualisation données");
        this.show();
    }
}