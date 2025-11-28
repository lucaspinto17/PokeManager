package com.pokemanager.dao;

import com.pokemanager.config.DatabaseConfig;
import com.pokemanager.model.Pokemon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//#region CONFIG POKEMON DAO

public class PokemonDAO {
    private Connection connection;
    
    public PokemonDAO() {
        this.connection = DatabaseConfig.getConnection();
    }
    
    public boolean insertPokemon(Pokemon pokemon) {
        String sql = "INSERT OR IGNORE INTO pokemon (id, name, type1, type2, height, weight, base_experience) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pokemon.getId());
            stmt.setString(2, pokemon.getName());
            stmt.setString(3, pokemon.getType1());
            stmt.setString(4, pokemon.getType2());
            stmt.setDouble(5, pokemon.getHeight());
            stmt.setDouble(6, pokemon.getWeight());
            stmt.setInt(7, pokemon.getBaseExperience());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir Pokemon: " + e.getMessage());
            return false;
        }
    }
    
    public Pokemon getPokemonById(int id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Pokemon(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type1"),
                    rs.getString("type2"),
                    rs.getDouble("height"),
                    rs.getDouble("weight"),
                    rs.getInt("base_experience")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pokemon: " + e.getMessage());
        }
        return null;
    }
    
    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM pokemon ORDER BY id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pokemons.add(new Pokemon(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type1"),
                    rs.getString("type2"),
                    rs.getDouble("height"),
                    rs.getDouble("weight"),
                    rs.getInt("base_experience")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar Pokemon: " + e.getMessage());
        }
        return pokemons;
    }
    
    public boolean updatePokemon(Pokemon pokemon) {
        String sql = "UPDATE pokemon SET name = ?, type1 = ?, type2 = ?, height = ?, weight = ?, base_experience = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pokemon.getName());
            stmt.setString(2, pokemon.getType1());
            stmt.setString(3, pokemon.getType2());
            stmt.setDouble(4, pokemon.getHeight());
            stmt.setDouble(5, pokemon.getWeight());
            stmt.setInt(6, pokemon.getBaseExperience());
            stmt.setInt(7, pokemon.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Pokemon: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletePokemon(int id) {
        String sql = "DELETE FROM pokemon WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar Pokemon: " + e.getMessage());
            return false;
        }
    }
}
