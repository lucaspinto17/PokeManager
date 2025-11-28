package com.pokemanager.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//#region DATABASE 
public class DatabaseConfig {
    // SQLite: arquivo local na pasta do projeto
    private static final String URL = "jdbc:sqlite:pokedb.db";
    
    public static Connection getConnection() {
        try {
            // Conexão automática - cria arquivo se não existir
            Connection conn = DriverManager.getConnection(URL);
            
            // Configurar para melhor performance
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON"); // Ativar chaves estrangeiras
            }
            
            createTablesIfNotExist(conn);
            return conn;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com SQLite: " + e.getMessage());
        }
    }
    // Criar tabelas automaticamente
    private static void createTablesIfNotExist(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            
            // TABELA Pokemon
            String createPokemonTable = "CREATE TABLE IF NOT EXISTS pokemon ("
                    + "id INTEGER PRIMARY KEY,"
                    + "name TEXT NOT NULL,"
                    + "type1 TEXT NOT NULL,"
                    + "type2 TEXT,"
                    + "height REAL,"
                    + "weight REAL,"
                    + "base_experience INTEGER,"
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")";
            
            // TABELA TIMES
            String createTeamTable = "CREATE TABLE IF NOT EXISTS pokemon_team ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "team_name TEXT NOT NULL DEFAULT 'Meu Time',"
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")";
            
            // TABELA MEMBROS DO TIME
            String createTeamMembersTable = "CREATE TABLE IF NOT EXISTS team_members ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "team_id INTEGER,"
                    + "pokemon_id INTEGER,"
                    + "position INTEGER,"
                    + "added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (team_id) REFERENCES pokemon_team(id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (pokemon_id) REFERENCES pokemon(id) ON DELETE CASCADE,"
                    + "UNIQUE (team_id, position)"
                    + ")";
            
            // Executar criação das tabelas
            stmt.execute(createPokemonTable);
            stmt.execute(createTeamTable);
            stmt.execute(createTeamMembersTable);
            
            // Inserir time padrão
            String insertDefaultTeam = "INSERT OR IGNORE INTO pokemon_team (id, team_name) "
                    + "VALUES (1, 'Meu Time Favorito')";
            stmt.execute(insertDefaultTeam);
            
            System.out.println(" Banco SQLite configurado com sucesso!");
            
        } catch (SQLException e) {
            System.out.println(" Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
