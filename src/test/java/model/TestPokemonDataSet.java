package model;

import model.data.PokemonDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPokemonDataSet {
    protected final int POKEMON_FILE_SIZE = 507;
    protected PokemonDataSet data;

    @BeforeEach
    public void setup() {
        data = new PokemonDataSet();
    }

    @Test
    public void should_load_data_when_given_a_valid_CSV_file() throws IOException {
        assertTrue(data.getPoints().isEmpty());
        data.loadCSV("src/main/resources/model/pokemon_train.csv");
        assertFalse(data.getPoints().isEmpty());
        assertEquals(POKEMON_FILE_SIZE, data.getPoints().size());
    }

    @Test
    public void should_add_new_pokemon_when_given_data() {
        PokemonPoint pokemon = new PokemonPoint("Test", 1, 1, 1, 1, 1, 1, 1, 1, "Feu", "Psy", 1, null);
        assertTrue(data.getPoints().isEmpty());
        data.addPoint("Test", 1, 1, 1, 1, 1, 1, 1, 1, "Feu", "Psy", 1);
        assertFalse(data.getPoints().isEmpty());
        assertEquals(1, data.getPoints().size());
        assertEquals(pokemon, data.getPoints().get(0));
    }
}
