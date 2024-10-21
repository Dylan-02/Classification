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

public class UserInterface extends Stage {
    DataSet ds = new DataSet();
    Label cheminFichier;
    XYChart.Series<Number, Number> seriesSetosa = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesVersicolor = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesVirginica = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesDefault = new XYChart.Series<>();
    ScatterChart<Number, Number> chart;

    //Que du visuel pour l'instant, commentaire à retirer
    public UserInterface(){

        File fichier = null;

        this.setMinWidth(610);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        Button boutonFichier = new Button("Choisir fichier");
        this.cheminFichier = new Label("aucun fichier selectionné");

        seriesSetosa.setName("Setosa");
        seriesVersicolor.setName("Versicolor");
        seriesVirginica.setName("Virginica");
        seriesDefault.setName("DONNEES UTILISATEUR");

        HBox boxFichier = new HBox(boutonFichier, cheminFichier);

        chart = new ScatterChart<>(xAxis, yAxis);
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
                    this.loadSeries();
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

    private void loadSeries(){
        XYChart.Data<Number,Number> invisiblePointDe = new XYChart.Data<>(0, 0);
        XYChart.Data<Number,Number> invisiblePointSe = new XYChart.Data<>(0, 0);
        XYChart.Data<Number,Number> invisiblePointVe = new XYChart.Data<>(0, 0);
        XYChart.Data<Number,Number> invisiblePointVi = new XYChart.Data<>(0, 0);
        seriesSetosa.getData().clear();
        seriesSetosa.getData().add(invisiblePointSe);
        seriesVersicolor.getData().clear();
        seriesVersicolor.getData().add(invisiblePointVe);
        seriesVirginica.getData().clear();
        seriesVirginica.getData().add(invisiblePointVi);
        seriesDefault.getData().clear();
        seriesDefault.getData().add(invisiblePointDe);
        ajouterPoints();
        seriesSetosa.getData().remove(invisiblePointSe);
        seriesVersicolor.getData().remove(invisiblePointVe);
        seriesVirginica.getData().remove(invisiblePointVi);
        seriesDefault.getData().remove(invisiblePointDe);
    }

    private void ajouterPoints() {
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
                default:
                    seriesDefault.getData().add(dataPoint);
                    break;
            }
        }

        chart.getData().addAll(seriesSetosa, seriesVersicolor, seriesVirginica, seriesDefault);
    }
}
// C:\Users\giout\OneDrive\Bureau\BUTINFO\BUT2\git\J5_SAE3.3\src\main\resources\model