package com.example.Mutuelle_mobile.repository;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.entites.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
 public List<Inscription> findByEtudiantAndFormation(Etudiant etudiant, Formation formation);
 public List<Inscription> findByEtudiant(Etudiant etudiant);
 public List<Inscription> findyFormation(Formation formation);
}
