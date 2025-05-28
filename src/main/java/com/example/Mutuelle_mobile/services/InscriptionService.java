package com.example.Mutuelle_mobile.services;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.entites.Inscription;
import com.example.Mutuelle_mobile.repository.EtudiantRepository;
import com.example.Mutuelle_mobile.repository.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscriptionService {

    //injection des dependances
    private final InscriptionRepository inscriptionRepository;
    private final EtudiantService etudiantService;
    private final FormationService formationService;
    public InscriptionService(InscriptionRepository inscriptionRepository, EtudiantService etudiantService, FormationService formationService) {
        this.inscriptionRepository = inscriptionRepository;
        this.etudiantService=etudiantService;
        this.formationService=formationService;
    }

    // Les services

    // Ajout d'une inscription
    public String AjoutInscription(Inscription inscription, Etudiant etudiant, Formation formation){
        inscription.setEtudiant(etudiant);
        inscription.setFormation(formation);
        String message;
        boolean verification =verificationInscription(inscription);
        if(!formationService.formationVerification(formation)){
            message=" La formation n'existe pas";
        }
        else if(verification){
            message="Erreur l'etudiant a deja ete inscrit pour cette formation";
        } else {
            if(!etudiantService.etudiantVerification(etudiant)){
                etudiantService.ajoutEtudiantDansBD(etudiant);
            }
            inscriptionRepository.save(inscription);
            message=" Inscription reussie";
        }
        return  message;
    }
    public boolean verificationInscription(Inscription inscription){
        boolean verification ;
        List<Inscription> inscriptionsDansLaBD = inscriptionRepository.findByEtudiantAndFormation(inscription.getEtudiant(),inscription.getFormation());
        if(inscriptionsDansLaBD==null){
            verification=false;
        }
        else{
            verification=true;
        }
        return verification;
    }
}
