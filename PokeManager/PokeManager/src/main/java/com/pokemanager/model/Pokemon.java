package com.pokemanager.model;

public class Pokemon {
    private int id;
    private String name;
    private String type1;
    private String type2;
    private double height;
    private double weight;
    private int baseExperience;
    
    public Pokemon() {}
    
    public Pokemon(int id, String name, String type1, String type2, 
                   double height, double weight, int baseExperience) {
        this.id = id;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType1() { return type1; }
    public void setType1(String type1) { this.type1 = type1; }
    
    public String getType2() { return type2; }
    public void setType2(String type2) { this.type2 = type2; }
    
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    
    public int getBaseExperience() { return baseExperience; }
    public void setBaseExperience(int baseExperience) { this.baseExperience = baseExperience; }
    
    @Override
    public String toString() {
        return String.format("Pokemon #%d: %s | Tipos: %s%s | Altura: %.1fm | Peso: %.1fkg | Exp: %d",
                id, name, type1, 
                type2 != null ? "/" + type2 : "", 
                height, weight, baseExperience);
    }
}
