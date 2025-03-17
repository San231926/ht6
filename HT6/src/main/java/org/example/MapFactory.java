package org.example;

import java.util.*;

public class MapFactory {
    public Map<String, Pokemon> createMap(int option) {
        switch (option) {
            case 1:
                System.out.println("Usando HashMap");
                return new HashMap<>();
            case 2:
                System.out.println("Usando TreeMap");
                return new TreeMap<>();
            case 3:
                System.out.println("Usando LinkedHashMap");
                return new LinkedHashMap<>();
            default:
                System.out.println("Opción no válida, usando HashMap por defecto");
                return new HashMap<>();
        }
    }
}

