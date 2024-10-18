package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        FileChooser fileChooser = new FileChooser(); //TODO relier au bouton

        boutonFichier.setOnAction(e -> {
            if(e.getTarget().equals(boutonFichier)){
                fileChooser.showOpenDialog(this);
            }
        });

        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.prefHeightProperty().bind(this.heightProperty().subtract(100));


        VBox chartBox = new VBox(boxFichier, chart);
        chartBox.prefWidthProperty().bind(this.widthProperty().subtract(100));

        Label axeDesAbscisses = new Label("Axe des abscisses");
        ComboBox<String> menuDeroulantAbscisses = new ComboBox<>(); //TODO à l'implémentation des données, CHANGER LE TYPE GéNéRIQUE DES COMBOBOX
        HBox espaceurSelecteursAxe = new HBox();
        espaceurSelecteursAxe.setPrefHeight(15);
        Label axeDesOrdonnees = new Label("Axe des ordonnées");
        ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();
        VBox conteneurMenusAxes = new VBox(axeDesAbscisses, menuDeroulantAbscisses, espaceurSelecteursAxe, axeDesOrdonnees, menuDeroulantOrdonnees);

        VBox conteneurStats = new VBox(); //TODO, AJOUTER LES STATS

        Button boutonAjouter = new Button("Ajouter");
        boutonAjouter.setMaxWidth(Double.MAX_VALUE);
        Button boutonSupprimer = new Button("Supprimer");
        boutonSupprimer.setMaxWidth(Double.MAX_VALUE);
        Button boutonClassifier = new Button("Classifier");
        boutonClassifier.setMaxWidth(Double.MAX_VALUE);
        Button boutonNouvelleFenetre = new Button("Nouvelle fenêtre");
        boutonNouvelleFenetre.setMaxWidth(Double.MAX_VALUE);

        VBox conteneurBoutonGestion = new VBox(boutonAjouter,boutonSupprimer,boutonClassifier,boutonNouvelleFenetre);
        conteneurBoutonGestion.setMinHeight(boutonAjouter.getHeight()*4);

        VBox sideBar = new VBox(conteneurMenusAxes, conteneurStats, conteneurBoutonGestion);
        axeDesAbscisses.prefWidthProperty().bind(sideBar.widthProperty());
        axeDesAbscisses.setAlignment(Pos.CENTER);
        axeDesOrdonnees.prefWidthProperty().bind(sideBar.widthProperty());
        axeDesOrdonnees.setAlignment(Pos.CENTER);
        menuDeroulantAbscisses.prefWidthProperty().bind(sideBar.widthProperty());
        menuDeroulantOrdonnees.prefWidthProperty().bind(sideBar.widthProperty());
        //conteneurStats.minHeightProperty().bind(sideBar.heightProperty().subtract(conteneurBoutonGestion.getHeight()));
        //VBox.setVgrow(conteneurStats, Priority.ALWAYS);

        sideBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        sideBar.setPadding(new Insets(5));
        sideBar.setAlignment(Pos.BASELINE_RIGHT);
        sideBar.prefWidthProperty().bind(this.widthProperty().multiply(0.3));

        HBox mainBox = new HBox(chartBox, sideBar);

        Scene scene = new Scene(mainBox);
        this.setScene(scene);
        this.setTitle("Visualisation données");
        this.show();
    }
}