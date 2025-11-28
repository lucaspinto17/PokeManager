package com.pokemanager;

import com.pokemanager.controller.MenuController;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== POKEMANAGER INICIADO ===");
        System.out.println("Sistema de Gerenciamento de Pokemon - Nova Pokedex");
        System.out.println("Banco: SQLite (automático)");
        System.out.println("API: PokeAPI (Free)");
        System.out.println("Bem vindo ao PokeManager!");
        System.out.println("===================================");

        MenuController menu = new MenuController();
        menu.showMenu();
    }
}
