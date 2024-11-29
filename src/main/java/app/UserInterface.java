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
import model.*;
import model.data.IrisDataSet;
import model.data.PokemonDataSet;
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

    IrisDataSet ds = new IrisDataSet();
    PokemonDataSet dsPokemon = new PokemonDataSet();

    String fichier = "";


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
    final Label labelDistance = new Label("Distance :");
    final ComboBox<String> menuDeroulantDistances = new ComboBox<>();

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
    final XYChart.Series<Number, Number> seriesLegendary = new XYChart.Series<>();
    final XYChart.Series<Number, Number> seriesNotLegendary = new XYChart.Series<>();

    /**
     * Constructeur de l'interface utilisateur.
     * Initialise les composants graphiques, configure les événements et attache l'observateur à l'ensemble de données.
     */
    public UserInterface() {

        boutonNouvelleFenetre.setDisable(true);

        ds.attach(this);
        dsPokemon.attach(this);

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

        boutonAjouter.setOnAction(event -> {
            if (this.fichier.equals("iris.csv")) {
                addNewPoint();
            }
            if (this.fichier.equals("pokemon_train.csv")) {
                addNewPointPokemon();
            }
        });


        this.setMinWidth(610);

        seriesSetosa.setName("Setosa");
        seriesVersicolor.setName("Versicolor");
        seriesVirginica.setName("Virginica");
        seriesLegendary.setName("Legendary");
        seriesNotLegendary.setName("Not Legendary");
        seriesDefault.setName("Données Utilisateur");

        Set<Node> nodes = chart.lookupAll(".chart-legend");
        for (Node n : nodes) {
            n.setStyle("-fx-padding: 5;");
        }

        conteneurStats.getChildren().addAll(labelDistance, menuDeroulantDistances);

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
                    if (this.chart.getData().isEmpty()) this.loadSeries(this.fichier);
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
        menuDeroulantDistances.prefWidthProperty().bind(sideBar.widthProperty());
        conteneurStats.maxHeightProperty().bind(sideBar.heightProperty().subtract(195));

        sideBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        sideBar.setPadding(new Insets(5));
        sideBar.setAlignment(Pos.BASELINE_RIGHT);
        sideBar.prefWidthProperty().bind(this.widthProperty().multiply(0.3));
        setupComboBoxesMenuDeroulantDistances();
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
        if (fichier.getName().equals("iris.csv")) {
            setupComboBoxesIris();
            ds.loadCSV(path);
        }
        if (fichier.getName().equals("pokemon_train.csv")) {
            setupComboBoxesPokemon();
            dsPokemon.loadCSV(path);
        }

        this.fichier = fichier.getName();
        System.out.println(this.fichier);

        boutonNouvelleFenetre.setDisable(false);


        try {
            this.cheminFichier.setText(fichier.getCanonicalPath());
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    private void setupComboBoxesMenuDeroulantDistances() {
        menuDeroulantDistances.getItems().addAll("Manhattan", "Euclidienne", "Manhattan Normalisée", "Euclidienne Normalisée");
        menuDeroulantDistances.getSelectionModel().select("Euclidienne");
    }

    private void setupComboBoxesIris() {
        menuDeroulantAbscisses.getItems().clear();
        menuDeroulantAbscisses.getItems().addAll("Longueur Sepal", "Largeur Sepal", "Longueur Petal", "Largeur Petal");
        menuDeroulantOrdonnees.getItems().clear();
        menuDeroulantOrdonnees.getItems().addAll("Longueur Sepal", "Largeur Sepal", "Longueur Petal", "Largeur Petal");
        menuDeroulantAbscisses.setValue("Longueur Sepal");
        menuDeroulantOrdonnees.setValue("Longueur Petal");
        xAxis.setLabel(menuDeroulantAbscisses.getValue());
        yAxis.setLabel(menuDeroulantOrdonnees.getValue());
    }

    private void setupComboBoxesPokemon() {
        menuDeroulantAbscisses.getItems().clear();
        menuDeroulantAbscisses.getItems().addAll("attack", "base_egg_steps", "capture_rate","defense", "experience_growth", "hp", "sp_attack", "sp_defense", "speed");
        menuDeroulantOrdonnees.getItems().clear();
        menuDeroulantOrdonnees.getItems().addAll("attack", "base_egg_steps", "capture_rate","defense", "experience_growth", "hp", "sp_attack", "sp_defense", "speed");
        menuDeroulantAbscisses.setValue("attack");
        menuDeroulantOrdonnees.setValue("hp");
        xAxis.setLabel(menuDeroulantAbscisses.getValue());
        yAxis.setLabel(menuDeroulantOrdonnees.getValue());
    }

    private Distance getSelectedDistance() {
        return switch (menuDeroulantDistances.getSelectionModel().getSelectedItem()) {
            case "Manhattan" -> new ManhattanDistance();
            case "Manhattan Normalisée" -> new NormalizedManhattanDistance(this.ds.getSepalLengthAmplitude(), this.ds.getSepalWidthAmplitude(), this.ds.getPetalLengthAmplitude(), this.ds.getPetalWidthAmplitude());
            case "Euclidienne Normalisée" -> new NormalizedEuclidianDistance(this.ds.getSepalLengthAmplitude(), this.ds.getSepalWidthAmplitude(), this.ds.getPetalLengthAmplitude(), this.ds.getPetalWidthAmplitude(), 0, 0, 0, 0, 0, 0, 0, 0, 0);
            default -> new EuclidianDistance();
        };
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
     * Ouvre une fenêtre pour ajouter manuellement un point de données au DataSet.
     * Demande à l'utilisateur de saisir les caractéristiques d'un pokemon.
     */
    private void addNewPointPokemon() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Ajouter un point");
        popupStage.setResizable(false);

        UnaryOperator<TextFormatter.Change> filterDouble = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        };

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        };

        Label label1 = new Label("name:");
        TextField textField1 = new TextField();
        Label label2 = new Label("attack:");
        TextField textField2 = new TextField();
        Label label3 = new Label("base_egg_steps:");
        TextField textField3 = new TextField();
        Label label4 = new Label("capture_rate:");
        TextField textField4 = new TextField();
        Label label5 = new Label("defense:");
        TextField textField5 = new TextField();
        Label label6 = new Label("experience_growth:");
        TextField textField6 = new TextField();
        Label label7 = new Label("hp:");
        TextField textField7 = new TextField();
        Label label8 = new Label("sp_attack:");
        TextField textField8 = new TextField();
        Label label9 = new Label("sp_defense:");
        TextField textField9 = new TextField();
        Label label10 = new Label("type1:");
        TextField textField10 = new TextField();
        Label label11 = new Label("type2:");
        TextField textField11 = new TextField();
        Label label12 = new Label("speed:");
        TextField textField12 = new TextField();


        textField2.setTextFormatter(new TextFormatter<>(integerFilter));
        textField3.setTextFormatter(new TextFormatter<>(integerFilter));
        textField4.setTextFormatter(new TextFormatter<>(filterDouble));
        textField5.setTextFormatter(new TextFormatter<>(integerFilter));
        textField6.setTextFormatter(new TextFormatter<>(integerFilter));
        textField7.setTextFormatter(new TextFormatter<>(integerFilter));
        textField8.setTextFormatter(new TextFormatter<>(integerFilter));
        textField9.setTextFormatter(new TextFormatter<>(integerFilter));
        textField12.setTextFormatter(new TextFormatter<>(filterDouble));

        Button submitButton = new Button("Ajouter");
        submitButton.setTooltip(new Tooltip("Click Me !"));
        submitButton.setOnAction(event -> {
            try {
                String name = (textField1.getText());
                int attack = Integer.parseInt(textField2.getText());
                int base_egg_steps = Integer.parseInt(textField3.getText());
                double capture_rate = Double.parseDouble(textField4.getText());
                int defense = Integer.parseInt(textField5.getText());
                int experience_growth = Integer.parseInt(textField6.getText());
                int hp = Integer.parseInt(textField7.getText());
                int sp_attack = Integer.parseInt(textField8.getText());
                int sp_defense = Integer.parseInt(textField9.getText());
                String type1 = textField10.getText();
                String type2 = textField11.getText();
                double speed = Double.parseDouble(textField12.getText());
                dsPokemon.addPoint(name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed);
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
        VBox vbox = new VBox(2, label1, textField1, label2, textField2, label3, textField3, label4, textField4,label5, textField5, label6, textField6, label7, textField7, label8, textField8, label9, textField9, label10, textField10, label11, textField11, label12, textField12, submitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new javafx.geometry.Insets(10));
        Scene popupScene = new Scene(vbox, 500, 600);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    /**
     * Permet de charger les series de points sur le graphique uniquement au premier lancement
     */
    private void loadSeries(String fichier) {
        XYChart.Data<Number, Number> invisiblePointDe = new XYChart.Data<>(0, 0);
        seriesDefault.getData().add(invisiblePointDe);
        addAllPoints();
        if (fichier.equals("iris.csv")) chartAddIrisCategories();
        if (fichier.equals("pokemon_train.csv")) chartAddPokemonCategories();
        seriesDefault.getData().remove(invisiblePointDe);
        installTooltips();
    }

    private void chartAddIrisCategories(){
        chart.getData().addAll(seriesSetosa, seriesVersicolor, seriesVirginica, seriesDefault);
    }

    private void chartAddPokemonCategories(){
       chart.getData().addAll(seriesLegendary, seriesNotLegendary, seriesDefault);
    }

    /**
     * Recharge les séries de points sur le graphique en fonction des axes sélectionnés.
     */
    private void reloadSeries() {
        if (this.fichier.equals("iris.csv")) clearIrisData();
        if (this.fichier.equals("pokemon_train.csv")) clearPokemonData();
        seriesDefault.getData().clear();
        addAllPoints();
        installTooltips();
    }

    private void clearIrisData(){
        seriesSetosa.getData().clear();
        seriesVersicolor.getData().clear();
        seriesVirginica.getData().clear();
    }

    private void clearPokemonData(){
        seriesLegendary.getData().clear();
        seriesNotLegendary.getData().clear();
    }

    /**
     * Permet d'ajouter des info-bulles sur chaque point affiché dans le graphique. Ces info-bulles affichent les coordonnées du point en fonction des axes choisis dans les menus déroulants.
     */
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
            case "Longueur Sepal" -> p.getSepalLength();
            case "Largeur Sepal" -> p.getSepalWidth();
            case "Longueur Petal" -> p.getPetalLength();
            case "Largeur Petal" -> p.getPetalWidth();
            default -> throw new IllegalArgumentException("Valeur inattendue: " + data);
        };
    }

    private double getDataforXYPokemon(PokemonPoint p, String data) {
        return switch (data) {
            case "attack" -> p.getAttack();
            case "base_egg_steps" -> p.getBase_egg_steps();
            case "capture_rate" -> p.getCapture_rate();
            case "defense" -> p.getDefense();
            case "experience_growth" -> p.getExperience_growth();
            case "hp" -> p.getHp();
            case "sp_attack" -> p.getSp_attack();
            case "sp_defense" -> p.getSp_defense();
            case "speed" -> p.getSpeed();

                default -> throw new IllegalArgumentException("Valeur inattendue: " + data);
        };
    }

    /**
     * Permet d'ajouter tous les points dans les series
     */
    private void addAllPoints() {
        if (fichier.equals("iris.csv")) {
            for (IrisPoint point : ds.getPoints()) {
                this.addNewPoint(point);
            }
        }
        if (fichier.equals("pokemon_train.csv")) {
            for (PokemonPoint point : dsPokemon.getPoints()) {
                this.addNewPoint(point);
            }
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

    public void addNewPoint(PokemonPoint point) {

        Number y = getDataforXYPokemon(point, menuDeroulantOrdonnees.getValue());
        Number x = getDataforXYPokemon(point, menuDeroulantAbscisses.getValue());
        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);

        if (point.getCategory() ==null) seriesDefault.getData().add(dataPoint);
        else {
            switch (point.getCategory()) {
                case LEGENDARY:
                    seriesLegendary.getData().add(dataPoint);
                    break;
                case NOT_LEGENDARY:
                    seriesNotLegendary.getData().add(dataPoint);
                    break;

            }
        }
    }

    @Override
    public void update(Observable observable) {
        if ((observable instanceof IrisDataSet)) {
            this.addAllPoints();
        }
        if ((observable instanceof PokemonDataSet)) {
            this.addAllPoints();
        }

    }

    @Override
    public void update(Observable observable, Object data) {
        if ((observable instanceof IrisDataSet) && (data instanceof IrisPoint)) {
            this.addNewPoint((IrisPoint) data);
        }
        if ((observable instanceof PokemonDataSet) && (data instanceof PokemonPoint)) {
            this.addNewPoint((PokemonPoint) data);
        }
    }


    /**
     * Permet de classer tous les points utilisateurs.
     */
    public void classify() {
        Distance distance = getSelectedDistance();
        if (this.fichier.equals("iris.csv")) {
            ds.classifyPoints(distance, ds.getBestKValue(distance));
        }
        if (this.fichier.equals("pokemon_train.csv")) {
            dsPokemon.classifyPoints(distance, dsPokemon.getBestKValue(distance));
        }

        seriesDefault.getData().clear();
    }


    /**
     * Permet de définir le DataSet de la vue et d'attacher celle-ci au nouveau DataSet
     *
     * @param ds Répresente le nouveau DataSet
     */
    private void setDs(IrisDataSet ds) {
        this.ds = ds;
        this.ds.attach(this);
    }

    private void setDsPokemon(PokemonDataSet dsPokemon) {
        this.dsPokemon = dsPokemon;
        this.dsPokemon.attach(this);
    }


    /**
     * Permet de créer une nouvelle vue, partageant le même DataSet
     */
    private void newVue() {
        UserInterface newVue = new UserInterface();
        newVue.fichier = this.fichier;
        if (this.fichier.equals("iris.csv")) {
            newVue.setupComboBoxesIris();
        }
        if (this.fichier.equals("pokemon_train.csv")){
            newVue.setupComboBoxesPokemon();
        }

        newVue.setDsPokemon(this.dsPokemon);
        newVue.setDs(this.ds);

        if (this.chart.getData().isEmpty()){
            this.loadSeries(this.fichier);
        }
        newVue.loadSeries(this.fichier);
        newVue.boutonNouvelleFenetre.setDisable(false);
        System.out.println(newVue.getFichier());
    }

    public String getFichier(){
        return this.fichier;
    }
}

