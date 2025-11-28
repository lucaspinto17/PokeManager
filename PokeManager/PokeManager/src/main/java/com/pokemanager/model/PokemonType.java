package com.pokemanager.model;

public class PokemonType {
    private int slot;
    private Type type;
    
    public static class Type {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
    
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
