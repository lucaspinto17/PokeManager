package com.pokemanager.model;

public class PokemonAbility {
    private boolean isHidden;
    private int slot;
    private Ability ability;
    
    public static class Ability {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    public boolean isHidden() { return isHidden; }
    public void setHidden(boolean hidden) { isHidden = hidden; }
    
    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
    
    public Ability getAbility() { return ability; }
    public void setAbility(Ability ability) { this.ability = ability; }
}
