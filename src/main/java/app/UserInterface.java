package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DataSet;
import model.EuclidianDistance;
import model.IrisPoint;
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


    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    final ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);

    final VBox espaceurChartFichier = new VBox();

    final Button boutonFichier = new Button("Choisir fichier");
    final Label cheminFichier = new Label("aucun fichier selectionné");
    final HBox boxFichier = new HBox(boutonFichier, cheminFichier);
    final VBox chartBox = new VBox(chart, espaceurChartFichier, boxFichier);
    final Label axeDesAbscisses = new Label("Axe des abscisses");
    final ComboBox<String> menuDeroulantAbscisses = new ComboBox<>();
    final HBox espaceurSelecteursAxe = new HBox();
    final Label axeDesOrdonnees = new Label("Axe des ordonnées");
    final ComboBox<String> menuDeroulantOrdonnees = new ComboBox<>();

    final VBox conteneurStats = new VBox();

    final Button boutonAjouter = new Button("Ajouter");
    final Button boutonClassifier = new Button("Classer");
    final Button boutonNouvelleFenetre = new Button("Nouvelle fenêtre");
    final VBox conteneurBoutons = new VBox(boutonAjouter, boutonClassifier, boutonNouvelleFenetre);
    final VBox sideBar = new VBox(axeDesAbscisses, menuDeroulantAbscisses, espaceurSelecteursAxe, axeDesOrdonnees, menuDeroulantOrdonnees, conteneurStats, conteneurBoutons);
    final HBox mainBox = new HBox(chartBox, sideBar);
    final XYChart.Series<Number, Number> seriesSetosa = new XYChart.Series<>();
    final XYChart.Series<Number, Number> seriesVersicolor = new XYChart.Series<>();
    final XYChart.Series<Number, Number> seriesVirginica = new XYChart.Series<>();
    final XYChart.Series<Number, Number> seriesDefault = new XYChart.Series<>();

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

        espaceurSelecteursAxe.setPrefHeight(21);

        conteneurStats.prefHeightProperty().bind(sideBar.heightProperty().subtract(265));

        boutonAjouter.setMaxWidth(Double.MAX_VALUE);
        boutonAjouter.setStyle("-fx-background-color: Green; -fx-text-fill: White");
        boutonAjouter.setOnMouseEntered(e -> boutonAjouter.setStyle("-fx-background-color: DarkGreen; -fx-text-fill: White"));
        boutonAjouter.setOnMouseExited(e -> boutonAjouter.setStyle("-fx-background-color: Green; -fx-text-fill: White"));
        boutonAjouter.setOnAction(e -> addNewPoint());

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

        boutonClassifier.setOnAction(event -> classify());

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
            throw new FileNotFoundException(); // Le fichier ne peut pas être erroné, car il est protégé par des extensions filter. Mais quand on ferme ça met null

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
    private void addNewPoint() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Ajouter un point");
        popupStage.setResizable(false);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) {
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
        submitButton.setTooltip(new Tooltip("Click Me !"));
        submitButton.setOnAction(event -> {
            try {
                double longueurSepal = Double.parseDouble(textField1.getText());
                double largeurSepal = Double.parseDouble(textField2.getText());
                double longueurPetal = Double.parseDouble(textField3.getText());
                double largeurPetal = Double.parseDouble(textField4.getText());
                ds.addPoint(longueurSepal, largeurSepal, longueurPetal, largeurPetal);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Saisie invalide");
                alert.setHeaderText("Erreur");
                alert.setContentText("Les données saisies sont invalides, veuillez saisir des données numériques.");
                alert.showAndWait();
                addNewPoint();
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
     * Permet de charger les series de points sur le graphique uniquement au premier lancement
     */
    private void loadSeries() {
        XYChart.Data<Number, Number> invisiblePointDe = new XYChart.Data<>(0, 0);
        seriesDefault.getData().add(invisiblePointDe);
        addAllPoints();
        chart.getData().addAll(seriesSetosa, seriesVersicolor, seriesVirginica, seriesDefault);
        seriesDefault.getData().remove(invisiblePointDe);
        installTooltips();
    }

    /**
     * Recharge les séries de points sur le graphique en fonction des axes sélectionnés.
     */
    private void reloadSeries() {
        seriesSetosa.getData().clear();
        seriesVersicolor.getData().clear();
        seriesVirginica.getData().clear();
        seriesDefault.getData().clear();
        addAllPoints();
        installTooltips();
    }

    private void installTooltips() {
        for (XYChart.Series<Number, Number> serie : this.chart.getData()) {
            for (XYChart.Data<Number, Number> data : serie.getData()) {
                Tooltip tooltip = new Tooltip(menuDeroulantAbscisses.getValue() + ": "+data.getXValue() +
                        "\n" + menuDeroulantOrdonnees.getValue() + ": " +data.getYValue());
                Tooltip.install(data.getNode(), tooltip);
            }
        }
    }

    /**
     * Permet de récuperer la longueur ou la largeur d'un point donné par rapport à la data
     *
     * @param p    Représente le point dont on souhaite obtenir les données
     * @param data Représente la donnée que l'on souhaite obtenir
     * @return La longueur ou largeur du point en fonction de la data
     */
    private double getDataforXY(IrisPoint p, String data) {
        return switch (data) {
            case "longueurSepal" -> p.getSepalLength();
            case "largeurSepal" -> p.getSepalWidth();
            case "longueurPetal" -> p.getPetalLength();
            case "largeurPetal" -> p.getPetalWidth();
            default -> throw new IllegalArgumentException("Valeur inattendue: " + data);
        };
    }

    /**
     * Permet d'ajouter tous les points dans les series
     */
    private void addAllPoints() {

        for (IrisPoint point : ds.getPoints()) {
            this.addNewPoint(point);
        }
        installTooltips();
    }

    /**
     * Permet d'ajouter un point dans la serie en fonction de sa catégorie
     *
     * @param point Représente le point à ajouter.
     */
    public void addNewPoint(IrisPoint point) {

        Number y = getDataforXY(point, menuDeroulantOrdonnees.getValue());
        Number x = getDataforXY(point, menuDeroulantAbscisses.getValue());
        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);

        if (point.getCategory() == null)
            seriesDefault.getData().add(dataPoint);
        else {
            switch (point.getCategory()) {
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
        if ((observable instanceof DataSet)) {
            this.addAllPoints();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if ((observable instanceof DataSet) && (data instanceof IrisPoint)) {
            this.addNewPoint((IrisPoint) data);
        }
    }


    /**
     * Permet de classer tous les points utilisateurs.
     */
    public void classify() {
        ds.classifyPoints(new EuclidianDistance(), 5); //TODO Ajouter une comboBox pour modifier la distanche choisie et récuperer la valeur ici (peut être aussi pour le k)
        seriesDefault.getData().clear();
    }


    /**
     * Permet de définir le DataSet de la vue et d'attacher celle-ci au nouveau DataSet
     *
     * @param ds Répresente le nouveau DataSet
     */
    private void setDs(DataSet ds) {
        this.ds = ds;
        this.ds.attach(this);
    }


    /**
     * Permet de créer une nouvelle vue, partageant le même DataSet
     */
    private void newVue() {
        UserInterface newVue = new UserInterface();
        newVue.setDs(this.ds);
        if (this.chart.getData().isEmpty()) this.loadSeries();
        newVue.loadSeries();
    }
}