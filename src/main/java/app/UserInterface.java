package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UserInterface extends Stage {

    //Que du visuel pour l'instant, commentaire à retirer
    public UserInterface(){

        File fichier = null;

        this.setMinWidth(610);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        Button boutonFichier = new Button("Choisir fichier");
        Label cheminFichier = new Label("aucun fichier selectionné");
        HBox boxFichier = new HBox(boutonFichier, cheminFichier);
        FileChooser filePathTf = new FileChooser(); //TODO relier au bouton




        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.prefHeightProperty().bind(this.heightProperty().subtract(100));
        Button boutonClassifier = new Button("Classifier");

        VBox chartBox = new VBox(boxFichier, chart, boutonClassifier);
        chartBox.prefWidthProperty().bind(this.widthProperty().subtract(100));

        Text axeDesAbscisses = new Text("Axe des abscisses");
        ComboBox<String> menuDeroulantAbscisses = new ComboBox<>(); //TODO à l'implémentation des données, CHANGER LE TYPE GéNéRIQUE DES COMBOBOX
        Text axeDesOrdonnees = new Text("Axe des ordonnées");
        ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();


        VBox sideBar = new VBox(axeDesAbscisses, menuDeroulantAbscisses, axeDesOrdonnees, menuDeroulantOrdonnees);
        menuDeroulantAbscisses.setPrefWidth(sideBar.getWidth());
        menuDeroulantOrdonnees.setPrefWidth(sideBar.getWidth());
        sideBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        sideBar.setPadding(new Insets(5));
        sideBar.setAlignment(Pos.BASELINE_RIGHT);
        sideBar.setMinWidth(400);


        HBox mainBox = new HBox(chartBox, sideBar);

        Scene scene = new Scene(mainBox);
        this.setScene(scene);
        this.setTitle("Visualisation données");
        this.show();
    }
}