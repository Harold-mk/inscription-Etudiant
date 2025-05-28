package com.example.Mutuelle_mobile.repository;

import com.example.Mutuelle_mobile.entites.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
    public Etudiant findByemail(String email);
    public List<Etudiant>findByNomContaining(String nom);
    public List<Etudiant>findByPrenomContaining(String prenom);
}
