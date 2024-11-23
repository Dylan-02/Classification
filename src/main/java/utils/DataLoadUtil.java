package utils;

import com.opencsv.bean.CsvToBeanBuilder;
import model.Category;
import model.IrisPoint;
import model.PokemonPoint;
import model.data.PokemonDataSet;
import model.data.RawIrisDataFormat;
import model.data.RawPokemonDataFormat;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoadUtil {
    public static List<RawIrisDataFormat> loadIris(String fileName) throws IOException {
        return new CsvToBeanBuilder<RawIrisDataFormat>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(RawIrisDataFormat.class)
                .build().parse();
    }

    public static IrisPoint createIrisPoint(RawIrisDataFormat d) {
        Category category = d.getCategory();
        double petalLength = d.getPetalLength();
        double petalWidth = d.getPetalWidth();
        double sepalLength = d.getSepalLength();
        double sepalWidth = d.getSepalWidth();
        return new IrisPoint(category, sepalLength, sepalWidth, petalLength, petalWidth);
    }

    public static List<IrisPoint> createIrisPointList(List<RawIrisDataFormat> data) {
        List<IrisPoint> result = new ArrayList<>();
        for (RawIrisDataFormat d : data) {
            result.add(createIrisPoint(d));
        }
        return result;
    }

    public static List<RawPokemonDataFormat> loadPokemon(String fileName) throws IOException {
        return new CsvToBeanBuilder<RawPokemonDataFormat>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(RawPokemonDataFormat.class)
                .build().parse();
    }

    public static PokemonPoint createPokemonPoint(RawPokemonDataFormat d) {
        String name = d.getName();
        int attack = d.getAttack();
        int base_egg_steps = d.getBase_egg_steps();
        double capture_rate = d.getCapture_rate();
        int defense = d.getDefense();
        int experience_growth = d.getExperience_growth();
        int hp = d.getHp();
        int sp_attack = d.getSp_attack();
        int sp_defense = d.getSp_defense();
        String type1 = d.getType1();
        String type2 = d.getType2();
        double speed = d.getSpeed();
        boolean is_legendary = d.getIs_legendary();
        return new PokemonPoint(name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, is_legendary);
    }

    public static List<PokemonPoint> createPokemonPointList(List<RawPokemonDataFormat> data) {
        List<PokemonPoint> result = new ArrayList<>();
        for (RawPokemonDataFormat d : data) {
            result.add(createPokemonPoint(d));
        }
        return result;
    }

}
