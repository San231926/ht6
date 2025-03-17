package org.example;

// Pokemon.java
public class Pokemon {
    private String name;
    private int pokedexNumber;
    private String type1;
    private String type2;
    private String classification;
    private double height;
    private double weight;
    private String abilities;
    private int generation;
    private boolean legendary;

    public Pokemon(String name, int pokedexNumber, String type1, String type2, String classification,
                   double height, double weight, String abilities, int generation, boolean legendary) {
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.type1 = type1;
        this.type2 = type2;
        this.classification = classification;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.generation = generation;
        this.legendary = legendary;
    }

    // Getters
    public String getName() { return name; }
    public int getPokedexNumber() { return pokedexNumber; }
    public String getType1() { return type1; }
    public String getType2() { return type2; }
    public String getClassification() { return classification; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getAbilities() { return abilities; }
    public int getGeneration() { return generation; }
    public boolean isLegendary() { return legendary; }

    @Override
    public String toString() {
        return "Pokemon: " + name +
                "\nPokedex Number: " + pokedexNumber +
                "\nTipo Primario: " + type1 +
                "\nTipo Secundario: " + (type2.isEmpty() ? "N/A" : type2) +
                "\nClasificación: " + classification +
                "\nAltura (m): " + height +
                "\nPeso (kg): " + weight +
                "\nHabilidades: " + abilities +
                "\nGeneración: " + generation +
                "\nLegendario: " + (legendary ? "Sí" : "No");
    }
}