package com.example.Mutuelle_mobile.services;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.repository.FormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    //suppression d'une formation dans la BD
    public String supprimerFormation(Formation formation){
        formationRepository.delete(formation);
        return "Formation supprimee";
    }

    // modification d'une formation dans la BD
    public  String modifierFormation(Formation formation){
        Formation formationDansLaBd= formationRepository.findbylibelle(formation.getLibelle());
        formation.setId(formationDansLaBd.getId());
        formationRepository.delete(formationDansLaBd);
        formationRepository.save(formation);
        return "etudiant modifie";
    }

    // Recherche des formations

    //Liste totale des formations
    public List<Formation> RechercheToutesLesformations(){
        return formationRepository.findAll();
    }
    //formation par libelle.
    public Formation rechercheParLibelle(String libelle){
        return formationRepository.findbylibelle(libelle);
    }
}
