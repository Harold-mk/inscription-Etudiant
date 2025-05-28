package com.example.Mutuelle_mobile.entites;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    private String description;
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscription> inscriptions = new ArrayList<>();

    // Les constructeurs

    public Formation() {
    }

    public Formation(int id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    //Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
