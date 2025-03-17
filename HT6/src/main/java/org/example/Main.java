package org.example;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MapFactory factory = new MapFactory();

        System.out.println("Seleccione la implementación de Map a utilizar:");
        System.out.println("1) HashMap");
        System.out.println("2) TreeMap");
        System.out.println("3) LinkedHashMap");

        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Map<String, Pokemon> pokemonMap = factory.createMap(option);

        // Cargar datos desde el archivo CSV
        try {
            loadPokemonData(pokemonMap);
            System.out.println("Datos de Pokemon cargados exitosamente. Total: " + pokemonMap.size());
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            return;
        }

        // Colección personalizada del usuario (HashSet para evitar duplicados)
        Set<String> userCollection = new HashSet<>();

        // Menú principal
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menú de Operaciones ---");
            System.out.println("1. Agregar un Pokemon a tu colección");
            System.out.println("2. Mostrar datos de un Pokemon");
            System.out.println("3. Mostrar tu colección ordenada por tipo primario");
            System.out.println("4. Mostrar todos los Pokemon ordenados por tipo primario");
            System.out.println("5. Buscar Pokemon por habilidad");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            int menuOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (menuOption) {
                case 1:
                    addPokemonToCollection(scanner, pokemonMap, userCollection);
                    break;
                case 2:
                    showPokemonDetails(scanner, pokemonMap);
                    break;
                case 3:
                    showUserCollectionByType(pokemonMap, userCollection);
                    break;
                case 4:
                    showAllPokemonByType(pokemonMap, getMapImplementationName(option));
                    break;
                case 5:
                    findPokemonByAbility(scanner, pokemonMap);
                    break;
                case 0:
                    running = false;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        scanner.close();

    }

    private static void loadPokemonData(Map<String, Pokemon> pokemonMap) throws IOException {
        String fileName = "src/main/java/org/example/pokemon.csv"; // Asegúrate de tener este archivo en el directorio de ejecución

        List<String> lines = Files.readAllLines(Paths.get(fileName));
        boolean firstLine = true;

        for (String line : lines) {
            if (firstLine) {
                firstLine = false;
                continue; // Saltar la línea de encabezados
            }

            // Usar regex para dividir correctamente campos entre comillas
            List<String> values = parseCSVLine(line);

            // Asegurar que haya suficientes campos
            if (values.size() >= 10) {
                try {
                    String name = values.get(0);
                    int pokedexNumber = Integer.parseInt(values.get(1));
                    String type1 = values.get(2);
                    String type2 = values.get(3);
                    String classification = values.get(4);
                    double height = Double.parseDouble(values.get(5));
                    double weight = Double.parseDouble(values.get(6));
                    String abilities = values.get(7);
                    int generation = Integer.parseInt(values.get(8));
                    boolean legendary = Boolean.parseBoolean(values.get(9));

                    Pokemon pokemon = new Pokemon(name, pokedexNumber, type1, type2, classification,
                            height, weight, abilities, generation, legendary);
                    pokemonMap.put(name, pokemon);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Error al procesar línea: " + line);
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    // Método para parsear correctamente CSV con valores entre comillas
    private static List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        // Patron para buscar campos delimitados por comas, manejando comillas
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?=,|$)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String value = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            result.add(value.trim());
        }

        return result;
    }

    private static void addPokemonToCollection(Scanner scanner, Map<String, Pokemon> pokemonMap, Set<String> userCollection) {
        System.out.print("Ingrese el nombre del Pokemon que desea agregar: ");
        String pokemonName = scanner.nextLine();

        if (pokemonMap.containsKey(pokemonName)) {
            if (userCollection.contains(pokemonName)) {
                System.out.println("¡" + pokemonName + " ya está en tu colección!");
            } else {
                userCollection.add(pokemonName);
                System.out.println(pokemonName + " ha sido agregado a tu colección.");
            }
        } else {
            System.out.println("Error: " + pokemonName + " no existe en la base de datos.");
        }
    }

    private static void showPokemonDetails(Scanner scanner, Map<String, Pokemon> pokemonMap) {
        System.out.print("Ingrese el nombre del Pokemon: ");
        String pokemonName = scanner.nextLine();

        Pokemon pokemon = pokemonMap.get(pokemonName);
        if (pokemon != null) {
            System.out.println(pokemon);
        } else {
            System.out.println("No se encontró un Pokemon con el nombre: " + pokemonName);
        }
    }

    private static void showUserCollectionByType(Map<String, Pokemon> pokemonMap, Set<String> userCollection) {
        if (userCollection.isEmpty()) {
            System.out.println("Tu colección está vacía.");
            return;
        }

        // Crear una lista de Pokémon del usuario
        List<Pokemon> userPokemons = new ArrayList<>();
        for (String name : userCollection) {
            Pokemon pokemon = pokemonMap.get(name);
            if (pokemon != null) {
                userPokemons.add(pokemon);
            }
        }

        // Ordenar por tipo primario
        userPokemons.sort(Comparator.comparing(Pokemon::getType1));

        System.out.println("Tu colección ordenada por tipo primario:");
        for (Pokemon pokemon : userPokemons) {
            System.out.println(pokemon.getName() + " - Tipo: " + pokemon.getType1());
        }
    }

    private static void showAllPokemonByType1(Map<String, Pokemon> pokemonMap) {
        List<Pokemon> allPokemons = new ArrayList<>(pokemonMap.values());

        // Ordenar por tipo primario
        allPokemons.sort(Comparator.comparing(Pokemon::getType1));

        System.out.println("Todos los Pokemon ordenados por tipo primario:");
        for (Pokemon pokemon : allPokemons) {
            System.out.println(pokemon.getName() + " - Tipo: " + pokemon.getType1());
        }
    }


    private static void showAllPokemonByType(Map<String, Pokemon> pokemonMap, String implementation) {
        long startTime = System.nanoTime();

        List<Pokemon> allPokemons = new ArrayList<>(pokemonMap.values());
        allPokemons.sort(Comparator.comparing(Pokemon::getType1));

        System.out.println("Todos los Pokemon ordenados por tipo primario:");
        for (Pokemon pokemon : allPokemons) {
            System.out.println(pokemon.getName() + " - Tipo: " + pokemon.getType1());
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Tiempo de ejecución (nanosegundos): " + duration);

        saveResultsToCSV(implementation, duration);
    }

    private static void findPokemonByAbility(Scanner scanner, Map<String, Pokemon> pokemonMap) {
        System.out.print("Ingrese la habilidad que desea buscar: ");
        String ability = scanner.nextLine().toLowerCase();

        System.out.println("Pokemon con la habilidad " + ability + ":");
        boolean found = false;

        for (Pokemon pokemon : pokemonMap.values()) {
            if (pokemon.getAbilities().toLowerCase().contains(ability)) {
                System.out.println(pokemon.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No se encontraron Pokemon con esa habilidad.");
        }
    }

    private static void saveResultsToCSV(String implementation, long duration) {
        String fileName = "results.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(implementation).append(",").append(String.valueOf(duration)).append("\n");
        } catch (IOException e) {
            System.out.println("Error al guardar los resultados: " + e.getMessage());
        }
    }

    private static String getMapImplementationName(int option) {
        switch (option) {
            case 1: return "HashMap";
            case 2: return "TreeMap";
            case 3: return "LinkedHashMap";
            default: return "Unknown";
        }
    }

}
