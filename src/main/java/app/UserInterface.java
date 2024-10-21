package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataSet;
import model.PointIris;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;

public class UserInterface extends Stage {
    DataSet ds = new DataSet();
    Label cheminFichier;
    //Que du visuel pour l'instant, commentaire à retirer
    public UserInterface(){

        File fichier = null;

        this.setMinWidth(610);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        Button boutonFichier = new Button("Choisir fichier");
        this.cheminFichier = new Label("aucun fichier selectionné");

        HBox boxFichier = new HBox(boutonFichier, cheminFichier);

        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.prefHeightProperty().bind(this.heightProperty().subtract(100));
        Button boutonClassifier = new Button("Classifier");

        VBox chartBox = new VBox(boxFichier, chart, boutonClassifier);
        chartBox.prefWidthProperty().bind(this.widthProperty().subtract(100));

        Text axeDesAbscisses = new Text("Axe des abscisses");
        ComboBox<String> menuDeroulantAbscisses = new ComboBox<>(); //TODO à l'implémentation des données, CHANGER LE TYPE GéNéRIQUE DES COMBOBOX
        Text axeDesOrdonnees = new Text("Axe des ordonnées");
        ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();

        boutonFichier.setOnAction(e -> {
            if(e.getTarget().equals(boutonFichier)){
                try{
                    this.openFileChooser();
                    this.ajouterPoints(chart);
                }catch(FileNotFoundException fnfe){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erreur !");
                    alert.setContentText("Un problème est survenu lors de la sélection du fichier");
                    alert.showAndWait();
                    fnfe.getMessage();
                }

                //ObservableList<FileChooser.ExtensionFilter>
            }
        });

        VBox sideBar = new VBox(axeDesAbscisses, menuDeroulantAbscisses, axeDesOrdonnees, menuDeroulantOrdonnees);
        menuDeroulantAbscisses.setPrefWidth(sideBar.getWidth());
        menuDeroulantOrdonnees.setPrefWidth(sideBar.getWidth());
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

    private void ajouterPoints(ScatterChart<Number, Number> chart) {
        XYChart.Series<Number, Number> seriesSetosa = new XYChart.Series<>();
        seriesSetosa.setName("Setosa");

        XYChart.Series<Number, Number> seriesVersicolor = new XYChart.Series<>();
        seriesVersicolor.setName("Versicolor");

        XYChart.Series<Number, Number> seriesVirginica = new XYChart.Series<>();
        seriesVirginica.setName("Virginica");

        for (PointIris point : ds.getPoints()) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(point.getLargeurSepal(), point.getLongueurPetal());

            switch (point.getCategorie()) {
                case SETOSA:
                    seriesSetosa.getData().add(dataPoint);
                    break;
                case VERSICOLOR:
                    seriesVersicolor.getData().add(dataPoint);
                    break;
                case VIRGINICA:
                    seriesVirginica.getData().add(dataPoint);
                    break;
            }
        }

        chart.getData().addAll(seriesSetosa, seriesVersicolor, seriesVirginica);
    }
}
// C:\Users\giout\OneDrive\Bureau\BUTINFO\BUT2\git\J5_SAE3.3\src\main\resources\model