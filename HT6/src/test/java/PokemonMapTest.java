import org.example.MapFactory;
import org.example.Pokemon;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PokemonMapTest {
    private Map<String, Pokemon> pokemonMap;
    private Set<String> userCollection;

    @BeforeEach
    void setUp() {
        MapFactory factory = new MapFactory();
        pokemonMap = factory.createMap(1); // Usar HashMap para las pruebas
        userCollection = new HashSet<>();

        // Agregar algunos Pokémon de prueba
        pokemonMap.put("Pikachu", new Pokemon("Pikachu", 25, "Electric", "", "Mouse Pokemon",
                0.4, 6.0, "Static,Lightning Rod", 1, false));
        pokemonMap.put("Bulbasaur", new Pokemon("Bulbasaur", 1, "Grass", "Poison", "Seed Pokemon",
                0.7, 6.9, "Overgrow,Chlorophyll", 1, false));
        pokemonMap.put("Charmander", new Pokemon("Charmander", 4, "Fire", "", "Lizard Pokemon",
                0.6, 8.5, "Blaze,Solar Power", 1, false));
    }

    @Test
    void testAddPokemonToCollection() {
        // Caso 1: Agregar un Pokémon que existe
        assertTrue(pokemonMap.containsKey("Pikachu"));
        assertFalse(userCollection.contains("Pikachu"));

        userCollection.add("Pikachu");
        assertTrue(userCollection.contains("Pikachu"));

        // Caso 2: Intentar agregar un Pokémon que no existe
        assertFalse(pokemonMap.containsKey("Perro"));
        assertFalse(userCollection.contains("Perro"));
    }

    @Test
    void testShowPokemonsByType() {
        // Agregar Pokémon a la colección del usuario
        userCollection.add("Pikachu");
        userCollection.add("Bulbasaur");
        userCollection.add("Charmander");

        // Crear lista ordenada por tipo para verificar
        List<Pokemon> expectedOrder = new ArrayList<>();
        expectedOrder.add(pokemonMap.get("Pikachu"));      // Electric
        expectedOrder.add(pokemonMap.get("Charmander"));   // Fire
        expectedOrder.add(pokemonMap.get("Bulbasaur"));    // Grass

        // Ordenar por tipo
        expectedOrder.sort(Comparator.comparing(Pokemon::getType1));

        // Verificar el orden
        assertEquals("Pikachu", expectedOrder.get(0).getName()); //  Electric viene primero alfabéticamente
        assertEquals("Charmander", expectedOrder.get(1).getName());    // Fire viene segundo
        assertEquals("Bulbasaur", expectedOrder.get(2).getName());  // Grass viene tercero
    }
}