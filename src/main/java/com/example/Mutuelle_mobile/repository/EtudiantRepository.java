package com.example.Mutuelle_mobile.repository;

import com.example.Mutuelle_mobile.entites.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
}
