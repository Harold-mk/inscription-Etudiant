package com.example.Mutuelle_mobile.entites;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateinscription;
    private String statutInscription;
    private String anneAcademique;

    @ManyToOne
    @JoinColumn(name = "ID_ETUDIANT")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "ID_FORMATION")
    private Formation formation;

    // Les constructeurs

    public Inscription() {
    }

    public Inscription(int id, Date dateinscription, String statutInscription, String anneAcademique, Etudiant etudiant, Formation formation) {
        this.id = id;
        this.dateinscription = dateinscription;
        this.statutInscription = statutInscription;
        this.anneAcademique = anneAcademique;
        this.etudiant = etudiant;
        this.formation = formation;
    }

    // Les Getters et les setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateinscription() {
        return dateinscription;
    }

    public void setDateinscription(Date dateinscription) {
        this.dateinscription = dateinscription;
    }

    public String getStatutInscription() {
        return statutInscription;
    }

    public void setStatutInscription(String statutInscription) {
        this.statutInscription = statutInscription;
    }

    public String getAnneAcademique() {
        return anneAcademique;
    }

    public void setAnneAcademique(String anneAcademique) {
        this.anneAcademique = anneAcademique;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
