package model;

import com.opencsv.bean.CsvBindByName;

public class RawPokemonDataFormat implements RawDataFormat{
    @Override
    public DataType getPointType() {
        return DataType.POKEMON;
    }

    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "attack")
    private int attack;
    @CsvBindByName(column = "base_egg_steps")
    private int base_egg_steps;
    @CsvBindByName(column = "capture_rate")
    private double capture_rate;
    @CsvBindByName(column = "defense")
    private int defense;
    @CsvBindByName(column = "experience_growth")
    private int experience_growth;
    @CsvBindByName(column = "hp")
    private int hp;
    @CsvBindByName(column = "sp_attack")
    private int sp_attack;
    @CsvBindByName(column = "sp_defense")
    private int sp_defense;
    @CsvBindByName(column = "type1")
    private String type1;
    @CsvBindByName(column = "type2")
    private String type2;
    @CsvBindByName(column = "speed")
    private double speed;
    @CsvBindByName(column = "is_legendary")
    private boolean is_legendary;

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
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
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isIs_legendary() {
        return is_legendary;
    }
}
