package com.pokemanager.controller;

import com.pokemanager.model.Pokemon;
import com.pokemanager.model.PokemonTeam;
import com.pokemanager.service.PokemonService;
import com.pokemanager.service.PokemonTeamService;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private PokemonService pokemonService;
    private PokemonTeamService teamService;
    private Scanner scanner;
    
    public MenuController() {
        this.pokemonService = new PokemonService();
        this.teamService = new PokemonTeamService();
        this.scanner = new Scanner(System.in);
    }
    //#region MENU PRINCIPAL
    public void showMenu() {
        while (true) {
            System.out.println("\n=== POKEMANAGER - SISTEMA DE GERENCIAMENTO ===");
            System.out.println("1. Buscar Pokemon da API e salvar");
            System.out.println("2. Listar todos Pokemon do banco");
            System.out.println("3. Buscar Pokemon no banco por ID");
            System.out.println("4. Atualizar Pokemon");
            System.out.println("5. Deletar Pokemon");
            System.out.println("6. GERENCIAR TIME FAVORITO");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String optLine = scanner.nextLine();
            int option;
            try {
                option = Integer.parseInt(optLine.trim());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (option) {
                case 1: searchAndSavePokemon(); break;
                case 2: listAllPokemon(); break;
                case 3: searchPokemonInDB(); break;
                case 4: updatePokemon(); break;
                case 5: deletePokemon(); break;
                case 6: manageTeam(); break;
                case 0: 
                    System.out.println("Saindo do PokeManager... Até mais!");
                    return;
                default: 
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    //  NOVO MÉTODO: GERENCIAR TIME
    private void manageTeam() {
        while (true) {
            System.out.println("\n--- GERENCIADOR DE TIME FAVORITO ---");
            PokemonTeam currentTeam = teamService.getCurrentTeam();
            
            if (currentTeam != null) {
                System.out.println(currentTeam);
            }
            
            System.out.println("1. Adicionar Pokemon ao time");
            System.out.println("2. Remover Pokemon do time");
            System.out.println("3. Ver time completo");
            System.out.println("4. Limpar todo o time");
            System.out.println("5. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            String optLine = scanner.nextLine();
            int option;
            try {
                option = Integer.parseInt(optLine.trim());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (option) {
                case 1: addPokemonToTeam(); break;
                case 2: removePokemonFromTeam(); break;
                case 3: showTeam(); break;
                case 4: clearTeam(); break;
                case 5: return;
                default: System.out.println("Opção inválida!");
            }
        }
    }
    
    //  ADICIONAR Pokemon AO TIME
    private void addPokemonToTeam() {
        if (teamService.isTeamFull()) {
            System.out.println("Time cheio! Remova um Pokemon antes de adicionar outro.");
            return;
        }
        
        System.out.println("\n--- ADICIONAR Pokemon AO TIME ---");
        System.out.print("Digite o ID ou Nome do Pokemon: ");
        String input = scanner.nextLine();
        
        boolean success;
        try {
            int pokemonId = Integer.parseInt(input);
            success = teamService.addPokemonToTeam(pokemonId);
        } catch (NumberFormatException e) {
            success = teamService.addPokemonToTeam(input);
        }
        
        if (!success) {
            System.out.println("Não foi possível adicionar o Pokemon ao time.");
        }
    }
    
    //  REMOVER Pokemon DO TIME
    private void removePokemonFromTeam() {
        PokemonTeam team = teamService.getCurrentTeam();
        if (team == null || team.isEmpty()) {
            System.out.println("Time vazio! Não há Pokemon para remover.");
            return;
        }
        
        System.out.println("\n--- REMOVER Pokemon DO TIME ---");
        System.out.println(team);
        System.out.print("Digite a posição do Pokemon a remover (1-6): ");
        String line = scanner.nextLine();
        int position;
        try {
            position = Integer.parseInt(line.trim());
        } catch (Exception e) {
            System.out.println("Posição inválida!");
            return;
        }

        if (teamService.removePokemonFromTeam(position)) {
            System.out.println("Pokemon removido com sucesso!");
        } else {
            System.out.println("Erro ao remover Pokemon.");
        }
    }
    
    //  VER TIME COMPLETO
    private void showTeam() {
        PokemonTeam team = teamService.getCurrentTeam();
        if (team != null) {
            System.out.println("\n" + team);
        } else {
            System.out.println("Time não encontrado!");
        }
    }
    
    //  LIMPAR TIME
    private void clearTeam() {
        System.out.print("Tem certeza que quer limpar todo o time? (s/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("s")) {
            if (teamService.clearTeam()) {
                System.out.println("Time limpo com sucesso!");
            } else {
                System.out.println("Erro ao limpar time.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    // MÉTODOS ORIGINAIS
    private void searchAndSavePokemon() {
        System.out.println("\n--- BUSCAR Pokemon DA API ---");
        System.out.print("Digite o ID ou Nome do Pokemon: ");
        String input = scanner.nextLine();
        
        Pokemon pokemon;
        try {
            int id = Integer.parseInt(input);
            pokemon = pokemonService.searchAndSavePokemon(id);
        } catch (NumberFormatException e) {
            pokemon = pokemonService.searchAndSavePokemon(input);
        }
        
        if (pokemon != null) {
            System.out.println("Pokemon encontrado: " + pokemon);
        }
    }
    
    private void listAllPokemon() {
        System.out.println("\n--- TODOS OS Pokemon NO BANCO ---");
        List<Pokemon> pokemons = pokemonService.getAllPokemonFromDB();
        
        if (pokemons.isEmpty()) {
            System.out.println("Nenhum Pokemon encontrado no banco.");
        } else {
            pokemons.forEach(System.out::println);
        }
    }
    
    private void searchPokemonInDB() {
        System.out.println("\n--- BUSCAR Pokemon NO BANCO ---");
        System.out.print("Digite o ID do Pokemon: ");
        String idLine = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(idLine.trim());
        } catch (Exception e) {
            System.out.println("ID inválido!");
            return;
        }

        Pokemon pokemon = pokemonService.getPokemonFromDB(id);
        if (pokemon != null) {
            System.out.println("Pokemon encontrado: " + pokemon);
        } else {
            System.out.println("Pokemon não encontrado no banco.");
        }
    }
    
    private void updatePokemon() {
        System.out.println("\n--- ATUALIZAR Pokemon ---");
        System.out.print("Digite o ID do Pokemon a ser atualizado: ");
        String idLine = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(idLine.trim());
        } catch (Exception e) {
            System.out.println("ID inválido!");
            return;
        }
        
        Pokemon existing = pokemonService.getPokemonFromDB(id);
        if (existing == null) {
            System.out.println("Pokemon não encontrado!");
            return;
        }
        
        System.out.println("Pokemon atual: " + existing);
        System.out.print("Novo Nome: ");
        String newName = scanner.nextLine();
        
        if (!newName.isEmpty()) {
            existing.setName(newName);
            if (pokemonService.updatePokemonInDB(existing)) {
                System.out.println("Pokemon atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar Pokemon.");
            }
        }
    }
    
    private void deletePokemon() {
        System.out.println("\n--- DELETAR Pokemon ---");
        System.out.print("Digite o ID do Pokemon a ser deletado: ");
        String idLine = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(idLine.trim());
        } catch (Exception e) {
            System.out.println("ID inválido!");
            return;
        }

        System.out.print("Tem certeza? (s/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("s") && pokemonService.deletePokemonFromDB(id)) {
            System.out.println("Pokemon deletado com sucesso!");
        } else {
            System.out.println("Operação cancelada ou erro ao deletar.");
        }
    }
}
