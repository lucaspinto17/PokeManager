package com.pokemanager.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pokemanager.model.Pokemon;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//#region POKEAPI CLIENT
public class PokeAPIClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
    private final Gson gson = new Gson();

    public Pokemon getPokemonById(int id) {
        try {
            URL url = java.net.URI.create(BASE_URL + id).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro: Pokemon não encontrado!");
                return null;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return parsePokemonFromJson(response.toString());
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar Pokemon: " + e.getMessage());
            return null;
        }
    }

    public Pokemon getPokemonByName(String name) {
        try {
            URL url = java.net.URI.create(BASE_URL + name.toLowerCase()).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro: Pokemon não encontrado!");
                return null;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return parsePokemonFromJson(response.toString());
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar Pokemon: " + e.getMessage());
            return null;
        }
    }

    private Pokemon parsePokemonFromJson(String json) {
        try {
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            int id = jsonObject.get("id").getAsInt();
            String name = jsonObject.get("name").getAsString();
            double height = jsonObject.get("height").getAsDouble() / 10.0;
            double weight = jsonObject.get("weight").getAsDouble() / 10.0;
            int baseExperience = jsonObject.get("base_experience").getAsInt();

            JsonArray typesArray = jsonObject.getAsJsonArray("types");
            String type1 = null;
            String type2 = null;

            for (int i = 0; i < typesArray.size(); i++) {
                String type = typesArray.get(i).getAsJsonObject()
                    .get("type").getAsJsonObject()
                    .get("name").getAsString();

                if (i == 0) type1 = type;
                else if (i == 1) type2 = type;
            }

            return new Pokemon(id, name, type1, type2, height, weight, baseExperience);

        } catch (Exception e) {
            System.out.println("Erro ao processar dados do Pokemon: " + e.getMessage());
            return null;
        }
    }
}
