package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;

public class UserInterface extends Stage {

    DataSet ds = new DataSet();

    Button boutonFichier = new Button("Choisir fichier");
    Label cheminFichier = new Label("aucun fichier selectionné");

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
    HBox boxFichier = new HBox(boutonFichier, cheminFichier);
    FileChooser fileChooser = new FileChooser(); //TODO relier au bouton
    VBox chartBox = new VBox(chart, boxFichier);
    Label axeDesAbscisses = new Label("Axe des abscisses");
    ComboBox<String> menuDeroulantAbscisses = new ComboBox<>(); //TODO à l'implémentation des données, CHANGER LE TYPE GéNéRIQUE DES COMBOBOX
    HBox espaceurSelecteursAxe = new HBox();
    Label axeDesOrdonnees = new Label("Axe des ordonnées");
    ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();
    VBox conteneurStats = new VBox(); //TODO, AJOUTER LES STATS
    Button boutonAjouter = new Button("Ajouter");
    Button boutonSupprimer = new Button("Supprimer");
    Button boutonClassifier = new Button("Classifier");
    Button boutonNouvelleFenetre = new Button("Nouvelle fenêtre");

    VBox sideBar = new VBox(axeDesAbscisses, menuDeroulantAbscisses, espaceurSelecteursAxe, axeDesOrdonnees, menuDeroulantOrdonnees, conteneurStats, boutonAjouter, boutonSupprimer, boutonClassifier, boutonNouvelleFenetre);

    HBox mainBox = new HBox(chartBox, sideBar);


    //Que du visuel pour l'instant, commentaire à retirer
    public UserInterface(){

        File fichier = null;

        this.setMinWidth(610);


        cheminFichier.setTextAlignment(TextAlignment.CENTER);

        boutonFichier.setOnAction(e -> {
            if(e.getTarget().equals(boutonFichier)){
                try {
                    this.openFileChooser();
                }catch (FileNotFoundException fileNotFound){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Quelque chose cloche !");
                    alert.setContentText("Le fichier n'a pas été sélectionné !");
                    alert.show();
                }

            }
        });

        chart.prefHeightProperty().bind(this.heightProperty().subtract(100));


        chartBox.prefWidthProperty().bind(this.widthProperty().subtract(100));

        espaceurSelecteursAxe.setPrefHeight(15);

        VBox.setVgrow(conteneurStats, Priority.ALWAYS);

        boutonAjouter.setMaxWidth(Double.MAX_VALUE);
        boutonAjouter.setStyle("-fx-background-color: #00d41d");

        boutonSupprimer.setMaxWidth(Double.MAX_VALUE);
        boutonSupprimer.setStyle("-fx-background-color: RED");

        boutonClassifier.setMaxWidth(Double.MAX_VALUE);
        boutonClassifier.setStyle("-fx-background-color: ORANGE");

        boutonNouvelleFenetre.setMaxWidth(Double.MAX_VALUE);


        axeDesAbscisses.prefWidthProperty().bind(sideBar.widthProperty());
        axeDesAbscisses.setAlignment(Pos.CENTER);
        axeDesOrdonnees.prefWidthProperty().bind(sideBar.widthProperty());
        axeDesOrdonnees.setAlignment(Pos.CENTER);
        menuDeroulantAbscisses.prefWidthProperty().bind(sideBar.widthProperty());
        menuDeroulantOrdonnees.prefWidthProperty().bind(sideBar.widthProperty());
        conteneurStats.maxHeightProperty().bind(sideBar.heightProperty().subtract(220));

        sideBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        sideBar.setPadding(new Insets(5));
        sideBar.setAlignment(Pos.BASELINE_RIGHT);
        sideBar.prefWidthProperty().bind(this.widthProperty().multiply(0.3));



        Scene scene = new Scene(mainBox);
        this.setScene(scene);
        this.setTitle("Visualisation données");
        this.setMinHeight(300);
        this.show();


    }

    public void openFileChooser() throws FileNotFoundException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter("Ne prend que les fichiers csv", "*.csv");
        fileChooser.getExtensionFilters().add(extensions);
        File fichier = fileChooser.showOpenDialog(this);
        if (fichier  == null)
            throw new FileNotFoundException(); // Le file ne peut pas être érroné car il est protégé par des extensions filter. Mais quand on ferme ça met null

        String path = fichier.getAbsolutePath();
        ds.loadCSV(path);
        this.cheminFichier.setVisible(false);
        //TODO
        //System.out.println(ds.getPoints().get(0)); débogue en attente de tests

    }
}