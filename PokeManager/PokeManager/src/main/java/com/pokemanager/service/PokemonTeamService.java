package com.pokemanager.service;

import com.pokemanager.model.Pokemon;
import com.pokemanager.model.PokemonTeam;
import com.pokemanager.dao.PokemonTeamDAO;
import com.pokemanager.dao.PokemonDAO;
import java.util.List;

//#region POKEMON SERVICE


public class PokemonTeamService {
    private PokemonTeamDAO teamDAO;
    private PokemonDAO pokemonDAO;
    private int currentTeamId = 1; // Time padrão
    
    public PokemonTeamService() {
        this.teamDAO = new PokemonTeamDAO();
        this.pokemonDAO = new PokemonDAO();
    }
    
    // Add Pokemon ao time atual
    public boolean addPokemonToTeam(int pokemonId) {
        Pokemon pokemon = pokemonDAO.getPokemonById(pokemonId);
        if (pokemon == null) {
            System.out.println("Pokemon não encontrado no banco!");
            return false;
        }
        
        int memberCount = teamDAO.getTeamMemberCount(currentTeamId);
        if (memberCount >= 6) {
            System.out.println("Time cheio! Remova um Pokemon antes de adicionar outro.");
            return false;
        }
        
        // Próxima posição disponível
        int position = findNextAvailablePosition();
        if (position == -1) {
            System.out.println("Erro: Não foi possível encontrar posição disponível.");
            return false;
        }
        
        if (teamDAO.addPokemonToTeam(currentTeamId, pokemonId, position)) {
            System.out.println(pokemon.getName() + " adicionado ao time na posição " + position + "!");
            return true;
        }
        return false;
    }
    
    // Add por Nome
    public boolean addPokemonToTeam(String pokemonName) {
        // Verifica se já existe no DB
        List<Pokemon> allPokemon = pokemonDAO.getAllPokemon();
        for (Pokemon p : allPokemon) {
            if (p.getName().equalsIgnoreCase(pokemonName)) {
                return addPokemonToTeam(p.getId());
            }
        }
        System.out.println("Pokemon '" + pokemonName + "' não encontrado no banco.");
        return false;
    }
    
    // Remove Pokemon do Time
    public boolean removePokemonFromTeam(int position) {
        if (position < 1 || position > 6) {
            System.out.println("Posição inválida! Use 1-6.");
            return false;
        }
        
        PokemonTeam team = getCurrentTeam();
        if (team == null || position > team.getMemberCount()) {
            System.out.println("Não há Pokemon nessa posição!");
            return false;
        }
        
        if (teamDAO.removePokemonFromTeam(currentTeamId, position)) {
            System.out.println("Pokemon removido da posição " + position + "!");
            return true;
        }
        return false;
    }
    
    // VER TIME ATUAL
    public PokemonTeam getCurrentTeam() {
        return teamDAO.getTeamById(currentTeamId);
    }
    
    // Listar todos os Times
    public List<PokemonTeam> getAllTeams() {
        return teamDAO.getAllTeams();
    }
    
    // Proxima posição disponível no time
    private int findNextAvailablePosition() {
        for (int i = 1; i <= 6; i++) {
            if (teamDAO.isPositionAvailable(currentTeamId, i)) {
                return i;
            }
        }
        return -1;
    }
    
    // Verifica se o time tá cheio
    public boolean isTeamFull() {
        PokemonTeam team = getCurrentTeam();
        return team != null && team.isFull();
    }
    
    // Limpa
    public boolean clearTeam() {
        PokemonTeam team = getCurrentTeam();
        if (team == null) return false;
        
        for (int i = 1; i <= 6; i++) {
            teamDAO.removePokemonFromTeam(currentTeamId, i);
        }
        System.out.println("Time limpo!");
        return true;
    }
}
