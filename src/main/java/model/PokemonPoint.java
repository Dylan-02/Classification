package model;

import java.util.Objects;

/**
 * Classe représentant un Pokémon avec ses différentes caractéristiques.
 *
 * Cette classe encapsule les données associées à un Pokémon, comme son nom,
 * ses statistiques, ses types, son statut légendaire, et d'autres attributs spécifiques.
 */
public class PokemonPoint {
    private CategoryPokemon category;
    private final String name;
    private final int attack;
    private final int base_egg_steps;
    private final double capture_rate;
    private final int defense;
    private final int experience_growth;
    private final int hp;
    private final int sp_attack;
    private final int sp_defense;
    private final String type1;
    private final String type2;
    private final double speed;
    private final Boolean is_legendary;

    /**
     * Constructeur pour initialiser les caractéristiques d'un Pokémon.
     *
     * @param name le nom du Pokémon.
     * @param attack la valeur de l'attaque.
     * @param base_egg_steps le nombre de pas nécessaires pour faire éclore un œuf.
     * @param capture_rate le taux de capture (valeur entre 0 et 255).
     * @param defense la valeur de la défense.
     * @param experience_growth la croissance de l'expérience (points nécessaires pour augmenter de niveau).
     * @param hp les points de vie (HP) du Pokémon.
     * @param sp_attack la valeur de l'attaque spéciale.
     * @param sp_defense la valeur de la défense spéciale.
     * @param type1 le premier type du Pokémon (exemple : "Eau").
     * @param type2 le second type du Pokémon (peut être null ou vide si monotype).
     * @param speed la vitesse du Pokémon.
     * @param is_legendary indique si le Pokémon est légendaire (true/false).
     */
    public PokemonPoint(String name, int attack, int base_egg_steps, double capture_rate, int defense, int experience_growth, int hp, int sp_attack, int sp_defense, String type1, String type2, double speed, Boolean is_legendary) {
        this.name = name;
        this.attack = attack;
        this.base_egg_steps = base_egg_steps;
        this.capture_rate = capture_rate;
        this.defense = defense;
        this.experience_growth = experience_growth;
        this.hp = hp;
        this.sp_attack = sp_attack;
        this.sp_defense = sp_defense;
        this.type1 = type1;
        this.type2 = type2;
        this.speed = speed;
        this.is_legendary = is_legendary;
        if (this.is_legendary != null) {
            if (this.is_legendary == true) this.category = CategoryPokemon.LEGENDARY;
            else this.category = CategoryPokemon.NOT_LEGENDARY;
        }
    }

    public PokemonPoint(String name, int attack, int base_egg_steps, double capture_rate, int defense, int experience_growth, int hp, int sp_attack, int sp_defense, String type1, String type2, double speed) {
        this(name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, null);
    }


    /**
     * Vérifie si deux objets Pokémon sont égaux en comparant toutes leurs caractéristiques.
     *
     * @param o l'objet à comparer avec l'instance actuelle.
     * @return true si les deux objets sont identiques, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonPoint that = (PokemonPoint) o;
        return attack == that.attack && base_egg_steps == that.base_egg_steps && Double.compare(capture_rate, that.capture_rate) == 0 && defense == that.defense && experience_growth == that.experience_growth && hp == that.hp && sp_attack == that.sp_attack && sp_defense == that.sp_defense && Double.compare(speed, that.speed) == 0 && is_legendary == that.is_legendary && Objects.equals(name, that.name) && Objects.equals(type1, that.type1) && Objects.equals(type2, that.type2);
    }

    public CategoryPokemon getCategory() {
        return category;
    }


    /**
     * Calcule le code de hachage (hash) pour l'objet Pokémon.
     *
     * @return un entier représentant le code de hachage de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, is_legendary);
    }
    /**
     * Retourne une représentation textuelle de l'objet Pokémon.
     *
     * @return une chaîne de caractères contenant les détails du Pokémon.
     */
    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", base_egg_steps=" + base_egg_steps +
                ", capture_rate=" + capture_rate +
                ", defense=" + defense +
                ", experience_growth=" + experience_growth +
                ", hp=" + hp +
                ", sp_attack=" + sp_attack +
                ", sp_defense=" + sp_defense +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", speed=" + speed +
                ", is_legendary=" + is_legendary +
                '}';
    }


    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }

    public int getBase_egg_steps() {
        return base_egg_steps;
    }

    public double getCapture_rate() {
        return capture_rate;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperience_growth() {
        return experience_growth;
    }

    public int getHp() {
        return hp;
    }

    public int getSp_attack() {
        return sp_attack;
    }

    public int getSp_defense() {
        return sp_defense;
    }

    public String getType1() {
        return  type1;
    }

    public String getType2() {
        return type2;
    }

    public double getSpeed() {
        return speed;
    }

    public Boolean isIs_legendary() {
        return is_legendary;
    }

    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getAttackDifference(PokemonPoint p) {
        return Math.abs(this.getAttack() - p.getAttack());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getBase_egg_stepsDifference(PokemonPoint p) {
        return Math.abs(this.getBase_egg_steps() - p.getBase_egg_steps());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getCapture_rateDifference(PokemonPoint p) {
        return Math.abs(this.getCapture_rate() - p.getCapture_rate());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getDefenseDifference(PokemonPoint p) {
        return Math.abs(this.getDefense() - p.getDefense());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getExperience_growthDifference(PokemonPoint p) {
        return Math.abs(this.getExperience_growth() - p.getExperience_growth());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getHpDifference(PokemonPoint p) {
        return Math.abs(this.getHp() - p.getHp());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getSp_attackDifference(PokemonPoint p) {
        return Math.abs(this.getSp_attack() - p.getSp_attack());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getSp_defenseDifference(PokemonPoint p) {
        return Math.abs(this.getSp_defense() - p.getSp_defense());
    }
    /**
     * Permet de calculer la différence entre les attaques des deux IrisPoint
     * @param p le point avec le quel comparer l'attaque.
     * @return difference entre les deux Pokemon
     */
    public double getSpeedDifference(PokemonPoint p) {
        return Math.abs(this.getSpeed() - p.getSpeed());
    }
}
