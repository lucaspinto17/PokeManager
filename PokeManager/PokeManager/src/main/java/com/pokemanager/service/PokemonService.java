package com.pokemanager.service;

import com.pokemanager.model.Pokemon;
import com.pokemanager.api.PokeAPIClient;
import com.pokemanager.dao.PokemonDAO;
import java.util.List;

//#region POKEMON SERVICE

public class PokemonService {
    private PokeAPIClient apiClient;
    private PokemonDAO pokemonDAO;
    
    public PokemonService() {
        this.apiClient = new PokeAPIClient();
        this.pokemonDAO = new PokemonDAO();
    }
    
    public Pokemon searchAndSavePokemon(int id) {
        Pokemon pokemon = apiClient.getPokemonById(id);
        if (pokemon != null && pokemonDAO.insertPokemon(pokemon)) {
            System.out.println("Pokemon salvo com sucesso!");
        }
        return pokemon;
    }
    
    public Pokemon searchAndSavePokemon(String name) {
        Pokemon pokemon = apiClient.getPokemonByName(name);
        if (pokemon != null && pokemonDAO.insertPokemon(pokemon)) {
            System.out.println("Pokemon salvo com sucesso!");
        }
        return pokemon;
    }
    
    public List<Pokemon> getAllPokemonFromDB() {
        return pokemonDAO.getAllPokemon();
    }
    
    public Pokemon getPokemonFromDB(int id) {
        return pokemonDAO.getPokemonById(id);
    }
    
    public boolean updatePokemonInDB(Pokemon pokemon) {
        return pokemonDAO.updatePokemon(pokemon);
    }
    
    public boolean deletePokemonFromDB(int id) {
        return pokemonDAO.deletePokemon(id);
    }
}
