package com.example.Mutuelle_mobile.repository;

import com.example.Mutuelle_mobile.entites.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation,Integer> {
    public Formation findbylibelle(String libelle);
}
