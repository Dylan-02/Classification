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
import java.util.List;
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

    final Button boutonDarkMode = new Button("☾");
    final Button boutonFichier = new Button("Choisir fichier");
    final Label cheminFichier = new Label("aucun fichier selectionné");
    final HBox boxBas = new HBox(boutonDarkMode, boutonFichier, cheminFichier);
    final VBox chartBox = new VBox(chart, espaceurChartFichier, boxBas);
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

        VBox.setMargin(boxBas, new Insets(5));
        VBox.setVgrow(espaceurChartFichier, Priority.ALWAYS);

        boutonFichier.setMinWidth(110);
        cheminFichier.setPadding(new Insets(5));
        boxBas.setAlignment(Pos.CENTER_LEFT);

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

        boutonDarkMode.setMinWidth(30);
        boutonDarkMode.setOnAction(e -> {
            if(e.getTarget().equals(boutonDarkMode)) {
                if(boutonDarkMode.getText().equals("☾")) {
                    boutonDarkMode.setText("☀");
                    mainBox.setStyle("-fx-background-color: #363636");
                    sideBar.setStyle("-fx-background-color: #797979");
                    cheminFichier.setStyle("-fx-text-fill: White");
                    chart.lookup(".chart-plot-background").setStyle("-fx-background-color: LightGray");
                    chart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: White");
                    chart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: White");
                    chart.getXAxis().lookup(".label").setStyle("-fx-text-fill: White");
                    chart.getYAxis().lookup(".label").setStyle("-fx-text-fill: White");
                }
                else {
                    boutonDarkMode.setText("☾");
                    mainBox.setStyle("-fx-background-color: White");
                    sideBar.setStyle("-fx-background-color: LightGray");
                    cheminFichier.setStyle("-fx-text-fill: Black");
                    chart.lookup(".chart-plot-background").setStyle("-fx-background-color: White");
                    chart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: Gray");
                    chart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: Gray");
                    chart.getXAxis().lookup(".label").setStyle("-fx-text-fill: Black");
                    chart.getYAxis().lookup(".label").setStyle("-fx-text-fill: Black");
                }
            }
        });

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
            throw new FileNotFoundException();

        String path = fichier.getAbsolutePath();
        if (fichier.getName().equals("iris.csv")) {
            reset();
            setupComboBoxesIris();
            ds.loadCSV(path);
        }
        if (fichier.getName().equals("pokemon_train.csv")) {
            reset();
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

    /**
     * Permet de définir les attributs du menu déroulant pour les distances
     */
    private void setupComboBoxesMenuDeroulantDistances() {
        menuDeroulantDistances.getItems().addAll("Manhattan", "Euclidienne", "Manhattan Normalisée", "Euclidienne Normalisée");
        menuDeroulantDistances.getSelectionModel().select("Euclidienne");
    }

    /**
     * Permet de définir les attributs X et Y pour les Iris lors de la création d'une vue
     */
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

    /**
     * Permet de définir les attributs X et Y pour les pokemons lors de la création d'une vue
     */
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

    /**
     * Permet de récuperer la méthode de distance à utiliser en fonction du texte choisi 
     * @return renvoie une Distance qui est une interface des différentes méthode de calcul
     */
    private Distance getSelectedDistance() {
        return switch (menuDeroulantDistances.getSelectionModel().getSelectedItem()) {
            case "Manhattan" -> new ManhattanDistance();
            case "Manhattan Normalisée" -> new NormalizedManhattanDistance(this.ds.getSepalLengthAmplitude(), this.ds.getSepalWidthAmplitude(), this.ds.getPetalLengthAmplitude(), this.ds.getPetalWidthAmplitude(), this.dsPokemon.getAttackAmplitude(), this.dsPokemon.getBaseEggsStepsAmplitude(), this.dsPokemon.getCaptureRateAmplitude(), this.dsPokemon.getDefenseAmplitude(), this.dsPokemon.getExperienceGrowthAmplitude(), this.dsPokemon.getHPAmplitude(), this.dsPokemon.getSpAttackAmplitude(), this.dsPokemon.getSpDefenseAmplitude(), this.dsPokemon.getSpeedAmplitude());
            case "Euclidienne Normalisée" -> new NormalizedEuclidianDistance(this.ds.getSepalLengthAmplitude(), this.ds.getSepalWidthAmplitude(), this.ds.getPetalLengthAmplitude(), this.ds.getPetalWidthAmplitude(), this.dsPokemon.getAttackAmplitude(), this.dsPokemon.getBaseEggsStepsAmplitude(), this.dsPokemon.getCaptureRateAmplitude(), this.dsPokemon.getDefenseAmplitude(), this.dsPokemon.getExperienceGrowthAmplitude(), this.dsPokemon.getHPAmplitude(), this.dsPokemon.getSpAttackAmplitude(), this.dsPokemon.getSpDefenseAmplitude(), this.dsPokemon.getSpeedAmplitude());
            default -> new EuclidianDistance();
        };
    }

    /**
     * Ouvre une fenêtre pour ajouter manuellement un point de données au DataSet.
     * Demande à l'utilisateur de saisir les caractéristiques d'un iris.
     */
    private Stage createPopupStage(String title) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);
        popupStage.setResizable(false);
        return popupStage;
    }

    /**
 * Crée un layout vertical (VBox) contenant une liste de labels et leurs champs de texte associés.
 *
 * @param labels une liste de labels décrivant chaque champ de texte.
 * @param textFields une liste de champs de texte correspondant aux labels.
 * @return un VBox contenant les labels et les champs de texte, aligné au centre avec un espacement entre les éléments.
 */
    private VBox createFormLayout(List<Label> labels, List<TextField> textFields) {
        VBox vbox = new VBox(10);
        for (int i = 0; i < labels.size(); i++) {
            vbox.getChildren().addAll(labels.get(i), textFields.get(i));
        }
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new javafx.geometry.Insets(10));
        return vbox;
    }

    /**
 * Crée un filtre de texte pour valider l'entrée utilisateur dans un champ de texte en fonction d'une expression régulière.
 *
 * @param regex une chaîne représentant une expression régulière pour valider l'entrée utilisateur.
 * @return un UnaryOperator de type TextFormatter.Change permettant de filtrer les modifications de texte.
 */
    private UnaryOperator<TextFormatter.Change> createTextFilter(String regex) {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regex)) {
                return change;
            }
            return null;
        };
    }

    /**
 * Affiche une fenêtre pop-up avec un formulaire contenant des labels et des champs de texte.
 * Les champs de texte sont formatés selon des TextFormatters et une action est exécutée lors de la soumission.
 *
 * @param labels une liste de labels décrivant les champs du formulaire.
 * @param textFields une liste de champs de texte correspondant aux labels.
 * @param formatters une liste de TextFormatters pour valider les entrées dans les champs de texte.
 * @param onSubmitAction une action à exécuter lorsque l'utilisateur clique sur le bouton "Ajouter".
 * @param width la largeur de la fenêtre pop-up.
 * @param height la hauteur de la fenêtre pop-up.
 */

    private void displayPopup(List<Label> labels, List<TextField> textFields, List<TextFormatter> formatters, Runnable onSubmitAction, int width, int height) {
        Stage popupStage = createPopupStage("Ajouter un point");

        for (int i = 0; i < textFields.size(); i++) {
            textFields.get(i).setTextFormatter(formatters.get(i));
        }

        Button submitButton = getSubmitButton(onSubmitAction, popupStage);

        VBox vbox = createFormLayout(labels, textFields);
        vbox.getChildren().add(submitButton);

        Scene popupScene = new Scene(vbox, width, height);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    /**
 * Crée un bouton de soumission associé à une action à exécuter lorsqu'il est cliqué.
 * Si une exception de type NumberFormatException est levée, une alerte d'erreur est affichée.
 *
 * @param onSubmitAction une action à exécuter lorsque l'utilisateur clique sur le bouton.
 * @param popupStage la fenêtre pop-up qui doit être fermée après la soumission.
 * @return un bouton configuré pour exécuter l'action et fermer la fenêtre.
 */

    private static Button getSubmitButton(Runnable onSubmitAction, Stage popupStage) {
        Button submitButton = new Button("Ajouter");
        submitButton.setTooltip(new Tooltip("Click Me !"));

        submitButton.setOnAction(event -> {
            try {
                onSubmitAction.run();
                popupStage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Saisie invalide");
                alert.setHeaderText("Erreur");
                alert.setContentText("Les données saisies sont invalides, veuillez saisir des données numériques.");
                alert.showAndWait();
            }
        });
        return submitButton;
    }

    /**
 * Ouvre une fenêtre pop-up permettant à l'utilisateur d'ajouter un nouveau point dans le DataSet Iris.
 * Le formulaire contient des champs pour les caractéristiques d'une fleur (longueur et largeur des sépales et pétales).
 * Une fois soumis, le point est ajouté au DataSet associé.
 */

    private void addNewPoint() {
        List<Label> labels = List.of(
                new Label("Longueur sépal:"),
                new Label("Largeur sépal:"),
                new Label("Longueur pétal:"),
                new Label("Largeur pétal:")
        );
        List<TextField> textFields = List.of(new TextField(), new TextField(), new TextField(), new TextField());
        List<TextFormatter> formatters = List.of(
                new TextFormatter<>(createTextFilter("\\d*(\\.\\d*)?")),
                new TextFormatter<>(createTextFilter("\\d*(\\.\\d*)?")),
                new TextFormatter<>(createTextFilter("\\d*(\\.\\d*)?")),
                new TextFormatter<>(createTextFilter("\\d*(\\.\\d*)?"))
        );

        Runnable onSubmitAction = () -> {
            double longueurSepal = Double.parseDouble(textFields.get(0).getText());
            double largeurSepal = Double.parseDouble(textFields.get(1).getText());
            double longueurPetal = Double.parseDouble(textFields.get(2).getText());
            double largeurPetal = Double.parseDouble(textFields.get(3).getText());
            ds.addPoint(longueurSepal, largeurSepal, longueurPetal, largeurPetal);
        };

        displayPopup(labels, textFields, formatters, onSubmitAction, 300, 300);
    }

    /**
 * Ouvre une fenêtre pop-up permettant à l'utilisateur d'ajouter un nouveau point dans le DataSet Pokémon.
 * Le formulaire contient des champs pour les caractéristiques d'un Pokémon (nom, statistiques, types, etc.).
 * Une fois soumis, le point est ajouté au DataSet associé.
 */

    private void addNewPointPokemon() {
        List<Label> labels = List.of(
                new Label("name:"), new Label("attack:"), new Label("base_egg_steps:"), new Label("capture_rate:"),
                new Label("defense:"), new Label("experience_growth:"), new Label("hp:"), new Label("sp_attack:"),
                new Label("sp_defense:"), new Label("type1:"), new Label("type2:"), new Label("speed:")
        );
        List<TextField> textFields = List.of(
                new TextField(), new TextField(), new TextField(), new TextField(),
                new TextField(), new TextField(), new TextField(), new TextField(),
                new TextField(), new TextField(), new TextField(), new TextField()
        );
        List<TextFormatter> formatters = List.of(
                new TextFormatter<>(createTextFilter("\\D*")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\d*(\\.\\d*)?")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\d*")),
                new TextFormatter<>(createTextFilter("\\D*")),
                new TextFormatter<>(createTextFilter("\\D*")),
                new TextFormatter<>(createTextFilter("\\d*(\\.\\d*)?"))
        );

        Runnable onSubmitAction = () -> {
            String name = textFields.get(0).getText();
            int attack = Integer.parseInt(textFields.get(1).getText());
            int baseEggSteps = Integer.parseInt(textFields.get(2).getText());
            double captureRate = Double.parseDouble(textFields.get(3).getText());
            int defense = Integer.parseInt(textFields.get(4).getText());
            int experienceGrowth = Integer.parseInt(textFields.get(5).getText());
            int hp = Integer.parseInt(textFields.get(6).getText());
            int spAttack = Integer.parseInt(textFields.get(7).getText());
            int spDefense = Integer.parseInt(textFields.get(8).getText());
            String type1 = textFields.get(9).getText();
            String type2 = textFields.get(10).getText();
            double speed = Double.parseDouble(textFields.get(11).getText());
            dsPokemon.addPoint(name, attack, baseEggSteps, captureRate, defense, experienceGrowth, hp, spAttack, spDefense, type1, type2, speed);
        };

        displayPopup(labels, textFields, formatters, onSubmitAction, 500, 800);
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

    /**
     * Permet d'ajouter les catégories des Iris dans la légende
     */
    private void chartAddIrisCategories(){
        chart.getData().addAll(seriesSetosa, seriesVersicolor, seriesVirginica, seriesDefault);
    }

    /**
     * Permet d'ajouter les categories des pokemon dans la légende
     */
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

    /**
     * Remet à zero les series de categorie d'iris
     */
    private void clearIrisData(){
        seriesSetosa.getData().clear();
        seriesVersicolor.getData().clear();
        seriesVirginica.getData().clear();
    }

    /**
     * Remet à zero les series de pokemon légendaire ou non
     */
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

    /**
     * Permet de récuperer les data d'un pokemon en fonction du texte selectionné
     * @param p Point de pokemon à récuperer
     * @param data Texte de la data à trouver
     * @return la data correspondante
     */
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
     * Permet d'ajouter un point Iris dans la serie en fonction de sa catégorie
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

    /**
     * Permet d'ajouter un point Pokemon dans la serie en fonction de sa catégorie
     *
     * @param point Représente le point à ajouter.
     */
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
     * Permet de définir le DataSet des Iris de la vue et d'attacher celle-ci au nouveau DataSet
     *
     * @param ds Répresente le nouveau DataSet
     */
    private void setDs(IrisDataSet ds) {
        this.ds = ds;
        this.ds.attach(this);
    }

    /**
     * Permet de définir le DataSet des Pokemons de la vue et d'attacher celle-ci au nouveau DataSet
     *
     * @param dsPokemon Répresente le nouveau DataSet
     */
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

    /**
     * Permet de récuperer le nom du fichier utilisé pour l'userInterface
     * @return nom du fichier
     */
    public String getFichier(){
        return this.fichier;
    }

    /**
     * Permet de remettre à 0 toutes les données de l'UserInterface
     */
    private void reset() {
        this.ds.getPoints().clear();
        this.dsPokemon.getPoints().clear();
        menuDeroulantOrdonnees.getItems().clear();
        menuDeroulantAbscisses.getItems().clear();
        this.chart.getData().clear();
    }
}

