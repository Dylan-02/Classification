package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DataSet;
import model.PointIris;
import utils.Observable;
import utils.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * La classe UserInterface représente l'interface graphique pour visualiser un ensemble de données Iris.
 */
public class UserInterface extends Stage implements Observer {

    DataSet ds = new DataSet();


    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);

    VBox espaceurChartFichier = new VBox();

    Button boutonFichier = new Button("Choisir fichier");
    Label cheminFichier = new Label("aucun fichier selectionné");
    HBox boxFichier = new HBox(boutonFichier, cheminFichier);
    VBox chartBox = new VBox(chart, espaceurChartFichier, boxFichier);
    Label axeDesAbscisses = new Label("Axe des abscisses");
    ComboBox<String> menuDeroulantAbscisses = new ComboBox<>();
    HBox espaceurSelecteursAxe = new HBox();
    Label axeDesOrdonnees = new Label("Axe des ordonnées");
    ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();

    VBox conteneurStats = new VBox();

    Button boutonAjouter = new Button("Ajouter");
    Button boutonClassifier = new Button("Classer");
    Button boutonNouvelleFenetre = new Button("Nouvelle fenêtre");
    VBox conteneurBoutons = new VBox(boutonAjouter, boutonClassifier, boutonNouvelleFenetre);

    XYChart.Series<Number, Number> seriesSetosa = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesVersicolor = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesVirginica = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesDefault = new XYChart.Series<>();

    VBox sideBar = new VBox(axeDesAbscisses, menuDeroulantAbscisses, espaceurSelecteursAxe, axeDesOrdonnees, menuDeroulantOrdonnees, conteneurStats, conteneurBoutons);

    HBox mainBox = new HBox(chartBox, sideBar);

    /**
     * Constructeur de l'interface utilisateur.
     * Initialise les composants graphiques, configure les événements et attache l'observateur à l'ensemble de données.
     */
    public UserInterface() {

        ds.attach(this);

        menuDeroulantAbscisses.getItems().addAll("longueurSepal", "largeurSepal", "longueurPetal", "largeurPetal");


        menuDeroulantOrdonnees.getItems().addAll("longueurSepal", "largeurSepal", "longueurPetal", "largeurPetal");

        menuDeroulantAbscisses.setValue("longueurSepal");
        menuDeroulantOrdonnees.setValue("largeurSepal");

        menuDeroulantAbscisses.setOnAction(f -> {
            String selectedItem = menuDeroulantAbscisses.getSelectionModel().getSelectedItem();
            menuDeroulantAbscisses.setPromptText(selectedItem);
            xAxis.setLabel(selectedItem);
            reloadSeries();
        });

        menuDeroulantOrdonnees.setOnAction(g -> {
            String selectedItem = menuDeroulantOrdonnees.getSelectionModel().getSelectedItem();
            menuDeroulantOrdonnees.setPromptText(selectedItem);
            yAxis.setLabel(selectedItem);
            reloadSeries();

        });

        this.setMinWidth(610);

        seriesSetosa.setName("Setosa");
        seriesVersicolor.setName("Versicolor");
        seriesVirginica.setName("Virginica");
        seriesDefault.setName("Données Utilisateur");

        Set<Node> nodes = chart.lookupAll(".chart-legend");
        for (Node n : nodes) {
            n.setStyle("-fx-padding: 5;");
        }

        VBox.setMargin(boxFichier, new Insets(5));
        VBox.setVgrow(espaceurChartFichier, Priority.ALWAYS);

        boutonFichier.setMinWidth(110);
        cheminFichier.setPadding(new Insets(5));
        boxFichier.setAlignment(Pos.CENTER_LEFT);

        chart.prefHeightProperty().bind(this.heightProperty().subtract(100));

        chartBox.prefWidthProperty().bind(this.widthProperty().subtract(100));

        espaceurSelecteursAxe.setPrefHeight(15);

        conteneurStats.prefHeightProperty().bind(sideBar.heightProperty().subtract(265));

        boutonAjouter.setMaxWidth(Double.MAX_VALUE);
        boutonAjouter.setStyle("-fx-background-color: Green; -fx-text-fill: White");
        boutonAjouter.setOnMouseEntered(e -> boutonAjouter.setStyle("-fx-background-color: DarkGreen; -fx-text-fill: White"));
        boutonAjouter.setOnMouseExited(e -> boutonAjouter.setStyle("-fx-background-color: Green; -fx-text-fill: White"));
        boutonAjouter.setOnAction(e -> ajouterPoint());

        boutonClassifier.setOnMouseEntered(e -> boutonClassifier.setStyle("-fx-background-color: DarkOrange;"));
        boutonClassifier.setOnMouseExited(e -> boutonClassifier.setStyle("-fx-background-color: Orange;"));
        boutonClassifier.setMaxWidth(Double.MAX_VALUE);
        boutonClassifier.setStyle("-fx-background-color: Orange");

        boutonNouvelleFenetre.setMaxWidth(Double.MAX_VALUE);
        boutonNouvelleFenetre.setOnAction(e -> newVue());

        conteneurBoutons.setSpacing(5);

        boutonFichier.setOnAction(e -> {
            if (e.getTarget().equals(boutonFichier)) {
                try {
                    this.openFileChooser();
                    if (this.chart.getData().isEmpty()) this.loadSeries();
                } catch (FileNotFoundException fileNotFound) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Quelque chose cloche !");
                    alert.setContentText("Le fichier n'a pas été sélectionné !");
                    alert.show();
                }

            }
        });

        boutonClassifier.setOnAction(event -> {

            classifier();

        });

        axeDesAbscisses.prefWidthProperty().bind(sideBar.widthProperty());
        axeDesAbscisses.setAlignment(Pos.CENTER);
        axeDesOrdonnees.prefWidthProperty().bind(sideBar.widthProperty());
        axeDesOrdonnees.setAlignment(Pos.CENTER);
        menuDeroulantAbscisses.prefWidthProperty().bind(sideBar.widthProperty());
        menuDeroulantOrdonnees.prefWidthProperty().bind(sideBar.widthProperty());

        conteneurStats.maxHeightProperty().bind(sideBar.heightProperty().subtract(195));

        sideBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        sideBar.setPadding(new Insets(5));
        sideBar.setAlignment(Pos.BASELINE_RIGHT);
        sideBar.prefWidthProperty().bind(this.widthProperty().multiply(0.3));
        Scene scene = new Scene(mainBox);
        this.setScene(scene);
        this.setTitle("Visualisation données");
        this.setMinHeight(350);
        this.show();
    }

    /**
     * Ouvre une boîte de dialogue pour sélectionner un fichier CSV et charge les données dans le DataSet.
     *
     * @throws FileNotFoundException si aucun fichier n'est sélectionné.
     */
    public void openFileChooser() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter("Ne prend que les fichiers csv", "*.csv");
        fileChooser.getExtensionFilters().add(extensions);
        File fichier = fileChooser.showOpenDialog(this);
        if (fichier == null)
            throw new FileNotFoundException(); // Le file ne peut pas être érroné car il est protégé par des extensions filter. Mais quand on ferme ça met null

        String path = fichier.getAbsolutePath();
        ds.loadCSV(path);
        try {
            this.cheminFichier.setText(fichier.getCanonicalPath());
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    /**
     * Ouvre une fenêtre pour ajouter manuellement un point de données au DataSet.
     * Demande à l'utilisateur de saisir les caractéristiques d'un iris.
     */
    private void ajouterPoint() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Ajouter un point");

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        Label label1 = new Label("Longueur sépal:");
        TextField textField1 = new TextField();
        Label label2 = new Label("Largeur sépal:");
        TextField textField2 = new TextField();
        Label label3 = new Label("Longueur pétal:");
        TextField textField3 = new TextField();
        Label label4 = new Label("Largeur pétal:");
        TextField textField4 = new TextField();

        textField1.setTextFormatter(new TextFormatter<>(filter));
        textField2.setTextFormatter(new TextFormatter<>(filter));
        textField3.setTextFormatter(new TextFormatter<>(filter));
        textField4.setTextFormatter(new TextFormatter<>(filter));

        Button submitButton = new Button("Ajouter");
        submitButton.setOnAction(event -> {
            try {
                int longueurSepal = Integer.parseInt(textField1.getText());
                int largeurSepal = Integer.parseInt(textField2.getText());
                int longueurPetal = Integer.parseInt(textField3.getText());
                int largeurPetal = Integer.parseInt(textField4.getText());
                System.out.println(ds.getPoints().size());
                ds.ajouterPoint(longueurSepal, largeurSepal, longueurPetal, largeurPetal);
                System.out.println(ds.getPoints().size());
                for (XYChart.Data<Number, Number> data : seriesDefault.getData()){
                    System.out.println(ds.getPoints().size());
                    System.out.println(data.toString());
                    System.out.println(ds.getPoints().size());
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Saisie invalide");
                alert.setHeaderText("Erreur");
                alert.setContentText("Les données saisies sont invalides, veuillez saisir des données numériques.");
                alert.showAndWait();
                ajouterPoint();
            }
            popupStage.close();
        });
        VBox vbox = new VBox(10, label1, textField1, label2, textField2, label3, textField3, label4, textField4, submitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new javafx.geometry.Insets(10));
        Scene popupScene = new Scene(vbox, 300, 300);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    /**
     * Charge les series de points sur le graphique uniquement au premier lancement
     */
    private void loadSeries() {
        XYChart.Data<Number, Number> invisiblePointDe = new XYChart.Data<>(0, 0);
        XYChart.Data<Number, Number> invisiblePointSe = new XYChart.Data<>(0, 0);
        XYChart.Data<Number, Number> invisiblePointVe = new XYChart.Data<>(0, 0);
        XYChart.Data<Number, Number> invisiblePointVi = new XYChart.Data<>(0, 0);
        seriesSetosa.getData().add(invisiblePointSe);
        seriesVersicolor.getData().add(invisiblePointVe);
        seriesVirginica.getData().add(invisiblePointVi);
        seriesDefault.getData().add(invisiblePointDe);
        ajouterPoints();
        chart.getData().addAll(seriesSetosa, seriesVersicolor, seriesVirginica, seriesDefault);
        seriesSetosa.getData().remove(invisiblePointSe);
        seriesVersicolor.getData().remove(invisiblePointVe);
        seriesVirginica.getData().remove(invisiblePointVi);
        seriesDefault.getData().remove(invisiblePointDe);
    }

    /**
     * Recharge les séries de points sur le graphique en fonction des axes sélectionnés.
     */
    private void reloadSeries(){
        seriesSetosa.getData().clear();
        seriesVersicolor.getData().clear();
        seriesVirginica.getData().clear();
        seriesDefault.getData().clear();
        ajouterPoints();
    }

    /**
     * Permet de récuperer la longueur ou la largeur d'un point donné par rapport à la data
     * @param p
     * @param data
     * @return La longueur ou largeur du point en fonction de la data
     */
    private double getDataforXY(PointIris p, String data){
        switch (data) {
            case "longueurSepal":
                return p.getLongueurSepal();
            case "largeurSepal":
                return p.getLargeurSepal();
            case "longueurPetal":
                return p.getLongueurPetal();
            case "largeurPetal":
                return p.getLargeurPetal();
            default:
                throw new IllegalArgumentException("Valeur inattendue: " + data);
        }
    }

    /**
     * Permet d'ajouter tous les points dans les series
     */
    private void ajouterPoints() {

        for (PointIris point : ds.getPoints()) {
            this.ajouterPoint(point);
        }

    }

    /**
     * Permet d'ajouter un point dans la serie en fonction de sa catégorie
     * @param point
     */
    public void ajouterPoint(PointIris point){

            Number y = getDataforXY(point, menuDeroulantOrdonnees.getValue());
            Number x = getDataforXY(point, menuDeroulantAbscisses.getValue());
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);

            if (point.getCategorie() == null)
                seriesDefault.getData().add(dataPoint);
            else {
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

    }

    @Override
    public void update(Observable observable) {
        if ((observable instanceof DataSet)){
            this.ajouterPoints();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if ((observable instanceof DataSet) && (data instanceof PointIris)){
            this.ajouterPoint((PointIris) data);
        }
    }
    public void classifier(){

        ds.classifierPoints();
        seriesDefault.getData().clear();

    }

    private void setDs(DataSet ds) {
        this.ds = ds;
        this.ds.attach(this);
    }

    private void newVue() {
        UserInterface newVue = new UserInterface();
        newVue.setDs(this.ds);
        if (this.chart.getData().isEmpty()) this.loadSeries();
        newVue.loadSeries();
    }
}