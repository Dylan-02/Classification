package model.data;

import model.Distance;
import model.IrisPoint;
import model.KNNClassifier;
import model.PokemonPoint;
import utils.DataLoadUtil;
import utils.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PokemonDataSet extends Observable {
    final public DataType type = DataType.POKEMON;
    final protected List<PokemonPoint> points = new ArrayList<>();
    final protected KNNClassifier knn = new KNNClassifier();

    public List<PokemonPoint> getPoints() {
        return points;
    }

    /**
     * Ajoute un nouveau point de données au DataSet en utilisant les valeurs des caractéristiques de l'iris.
     * Notifie les observateurs après l'ajout du point.
     *
     * @param name Le nom du pokémon
     * @param attack La statistique attack du pokemon
     * @param base_egg_steps La statistique base_egg_steps du pokémon
     * @param capture_rate La statistique capture_rate du pokémon
     * @param defense La statistique defense du pokémon
     * @param experience_growth La statistique experience_growth du pokémon
     * @param hp La statistique hp du pokémon
     * @param sp_attack La statistique sp_attack du pokémon
     * @param sp_defense La statistique sp_defense du pokémon
     * @param type1 Le type principal du pokémon
     * @param type2 Le type secondaire du pokémon (peut être null)
     * @param speed La statistique speed du pokémon
     * @param is_legendary Si le pokémon est légendaire ou non
     */
    public void addPoint(String name, int attack, int base_egg_steps, double capture_rate, int defense, int experience_growth, int hp, int sp_attack, int sp_defense, String type1, String type2, double speed, boolean is_legendary) {
        PokemonPoint pt = new PokemonPoint(name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, is_legendary);
        this.points.add(pt);
        this.notifyObservers(pt);
    }

    /**
     * Ajoute une collection de points de données au DataSet et notifie les observateurs après l'ajout.
     *
     * @param pointList collection de points de type IrisPoint à ajouter.
     */
    public void addPoints(Collection<PokemonPoint> pointList) {
        this.points.addAll(pointList);
        this.notifyObservers();
    }

    /**
     * Charge les données à partir d'un fichier CSV et les ajoute au DataSet.
     * Notifie les observateurs après le chargement des données.
     *
     * @param file le chemin du fichier CSV à charger.
     */
    public void loadCSV(String file) {
        try {
            List<RawPokemonDataFormat> data = DataLoadUtil.loadPokemon(file);
            List<PokemonPoint> pointList = DataLoadUtil.createPokemonPointList(data);
            this.addPoints(pointList);
            this.notifyObservers();
        } catch (IOException e) {
            System.out.println("Fichier introuvable");
        }
    }
}
