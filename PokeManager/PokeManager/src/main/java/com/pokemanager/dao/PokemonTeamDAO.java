package com.pokemanager.dao;

import com.pokemanager.config.DatabaseConfig;
import com.pokemanager.model.Pokemon;
import com.pokemanager.model.PokemonTeam;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//#region CONFIG POKEMON TEAM DAO

public class PokemonTeamDAO {
    private Connection connection;
    private PokemonDAO pokemonDAO;
    
    public PokemonTeamDAO() {
        this.connection = DatabaseConfig.getConnection();
        this.pokemonDAO = new PokemonDAO();
    }
    
    // CRIAR NOVO TIME
    public int createTeam(String teamName) {
        String sql = "INSERT INTO pokemon_team (team_name) VALUES (?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, teamName);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar seu time: " + e.getMessage());
        }
        return -1;
    }
    
    // ADICIONAR Pokemon AO TIME
    public boolean addPokemonToTeam(int teamId, int pokemonId, int position) {
        String sql = "INSERT OR REPLACE INTO team_members (team_id, pokemon_id, position) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, pokemonId);
            stmt.setInt(3, position);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar Pokemon ao time: " + e.getMessage());
            return false;
        }
    }
    
    // REMOVER Pokemon DO TIME
    public boolean removePokemonFromTeam(int teamId, int position) {
        String sql = "DELETE FROM team_members WHERE team_id = ? AND position = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, position);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover Pokemon do time: " + e.getMessage());
            return false;
        }
    }
    
    // BUSCAR TIME POR ID
    public PokemonTeam getTeamById(int teamId) {
        String teamSql = "SELECT * FROM pokemon_team WHERE id = ?";
        String membersSql = "SELECT tm.position, p.* FROM team_members tm " +
                           "JOIN pokemon p ON tm.pokemon_id = p.id " +
                           "WHERE tm.team_id = ? ORDER BY tm.position";
        
        try (PreparedStatement teamStmt = connection.prepareStatement(teamSql)) {
            teamStmt.setInt(1, teamId);
            ResultSet teamRs = teamStmt.executeQuery();
            
            if (teamRs.next()) {
                PokemonTeam team = new PokemonTeam();
                team.setId(teamRs.getInt("id"));
                team.setTeamName(teamRs.getString("team_name"));
                
                // Buscar membros do time
                try (PreparedStatement membersStmt = connection.prepareStatement(membersSql)) {
                    membersStmt.setInt(1, teamId);
                    ResultSet membersRs = membersStmt.executeQuery();
                    
                    List<Pokemon> members = new ArrayList<>();
                    while (membersRs.next()) {
                        Pokemon pokemon = new Pokemon(
                            membersRs.getInt("id"),
                            membersRs.getString("name"),
                            membersRs.getString("type1"),
                            membersRs.getString("type2"),
                            membersRs.getDouble("height"),
                            membersRs.getDouble("weight"),
                            membersRs.getInt("base_experience")
                        );
                        members.add(pokemon);
                    }
                    team.setMembers(members);
                }
                return team;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar time: " + e.getMessage());
        }
        return null;
    }
    
    // VERIFICAR SE POSIÇÃO ESTÁ LIVRE
    public boolean isPositionAvailable(int teamId, int position) {
        String sql = "SELECT COUNT(*) FROM team_members WHERE team_id = ? AND position = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, position);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar posição: " + e.getMessage());
        }
        return false;
    }
    
    // CONTAR QUANTOS Pokemon TEM NO TIME
    public int getTeamMemberCount(int teamId) {
        String sql = "SELECT COUNT(*) FROM team_members WHERE team_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar membros do time: " + e.getMessage());
        }
        return 0;
    }
    
    // LISTAR TODOS OS TIMES
    public List<PokemonTeam> getAllTeams() {
        List<PokemonTeam> teams = new ArrayList<>();
        String sql = "SELECT id FROM pokemon_team ORDER BY created_at";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PokemonTeam team = getTeamById(rs.getInt("id"));
                if (team != null) {
                    teams.add(team);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar times: " + e.getMessage());
        }
        return teams;
    }
    
    // DELETAR TIME
    public boolean deleteTeam(int teamId) {
        String sql = "DELETE FROM pokemon_team WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar time: " + e.getMessage());
            return false;
        }
    }
}
