package com.example.Mutuelle_mobile.services;

import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.repository.FormationRepository;
import org.springframework.stereotype.Service;

@Service
public class FormationService {
    //Injection des dependances
    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    //Services
    //Verification de l'existence de la formation dans la BD
     public boolean formationVerification(Formation formation){
        Formation formationDansLaBD= formationRepository.findbylibelle(formation.getLibelle());
        boolean verification = true;
         if(formationDansLaBD.getDescription().equals(formation.getDescription())){
             verification = true;
         }
         else {
             verification=false;
         }
         return verification;
     }

     //Ajout d'une formation dans la BD
    public void ajoutFormation(Formation formation){
        formationRepository.save(formation);
    }
}
