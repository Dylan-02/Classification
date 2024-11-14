package model;

import java.util.Objects;

public class PokemonPoint {
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
    private final boolean is_legendary;

    public PokemonPoint(String name, int attack, int base_egg_steps, double capture_rate, int defense, int experience_growth, int hp, int sp_attack, int sp_defense, String type1, String type2, double speed, boolean is_legendary) {
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonPoint that = (PokemonPoint) o;
        return attack == that.attack && base_egg_steps == that.base_egg_steps && Double.compare(capture_rate, that.capture_rate) == 0 && defense == that.defense && experience_growth == that.experience_growth && hp == that.hp && sp_attack == that.sp_attack && sp_defense == that.sp_defense && Double.compare(speed, that.speed) == 0 && is_legendary == that.is_legendary && Objects.equals(name, that.name) && Objects.equals(type1, that.type1) && Objects.equals(type2, that.type2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, is_legendary);
    }

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
}
