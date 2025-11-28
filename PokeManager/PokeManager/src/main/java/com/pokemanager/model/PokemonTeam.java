package com.pokemanager.model;

import java.util.ArrayList;
import java.util.List;

public class PokemonTeam {
    private int id;
    private String teamName;
    private List<Pokemon> members;
    private int memberCount;
    
    public PokemonTeam() {
        this.members = new ArrayList<>();
        this.teamName = "Meu Time";
        this.memberCount = 0;
    }
    
    public PokemonTeam(int id, String teamName) {
        this.id = id;
        this.teamName = teamName;
        this.members = new ArrayList<>();
        this.memberCount = 0;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    
    public List<Pokemon> getMembers() { return members; }
    public void setMembers(List<Pokemon> members) { 
        this.members = members;
        this.memberCount = members.size();
    }
    
    public int getMemberCount() { return memberCount; }
    
    // MÉTODOS PARA GERENCIAR O TIME
    public boolean addMember(Pokemon pokemon) {
        if (memberCount >= 6) {
            System.out.println("Time cheio! Máximo de 6 Pokemon.");
            return false;
        }
        members.add(pokemon);
        memberCount++;
        return true;
    }
    
    public boolean removeMember(int position) {
        if (position >= 0 && position < members.size()) {
            members.remove(position);
            memberCount--;
            return true;
        }
        return false;
    }
    
    public Pokemon getMember(int position) {
        if (position >= 0 && position < members.size()) {
            return members.get(position);
        }
        return null;
    }
    
    public boolean isFull() {
        return memberCount >= 6;
    }
    
    public boolean isEmpty() {
        return memberCount == 0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(teamName).append(" ===\n");
        sb.append("Membros: ").append(memberCount).append("/6\n");
        
        if (members.isEmpty()) {
            sb.append("Time vazio - adicione Pokemon!\n");
        } else {
            for (int i = 0; i < members.size(); i++) {
                sb.append(i + 1).append(". ").append(members.get(i)).append("\n");
            }
        }
        return sb.toString();
    }
    
    public String toShortString() {
        return teamName + " (" + memberCount + "/6 Pokemon)";
    }
}
