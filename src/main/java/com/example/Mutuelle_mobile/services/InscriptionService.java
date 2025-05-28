package com.example.Mutuelle_mobile.services;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.entites.Inscription;
import com.example.Mutuelle_mobile.repository.EtudiantRepository;
import com.example.Mutuelle_mobile.repository.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            message="Erreur l'étudiant a deja ete inscrit pour cette formation";
        } else {
            if(!etudiantService.etudiantVerification(etudiant)){
                etudiantService.ajoutEtudiantDansBD(etudiant);
            }
            inscriptionRepository.save(inscription);
            message=" Inscription reussie";
        }
        return  message;
    }
    // Verification d'une inscription
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
    // Recherche de la liste des inscriptions
    public List<Inscription>ListeDesInscriptions(){
        return inscriptionRepository.findAll();
    }

    // Recherche des inscriptions fait par un etudiant
    public List<Inscription> rechercheInscriptionParEtudiant(Etudiant etudiant){
        return inscriptionRepository.findByEtudiant(etudiant);
    }
    //recherche de la liste des inscriptions d'une formation
    public List<Inscription>rechercheInscriptionParFormation(Formation formation){
        return inscriptionRepository.findyFormation(formation);
    }
    //Recherche des inscriptions pour une formation et un etudiant defini
    public List<Inscription>rechercheInscriptionParEtudiantFormation(Etudiant etudiant,Formation formation){
        return inscriptionRepository.findByEtudiantAndFormation(etudiant,formation);
    }
    //Recherhche des informations d'un etudiant  a partir d'une inscription
    public Etudiant rechercheEtudiantParInscription(Inscription inscription){
        return inscription.getEtudiant();
    }
    //recherche de la formation  a partir d'une inscription
    public Formation recherceFormationParInscription(Inscription inscription){
        return inscription.getFormation();
    }
    //Recherche de la liste des etudiants appartenant s'etant inscrit a une formation
    public List<Etudiant> rechercheEtudiantParFormation(Formation formation){
        List<Etudiant>etudiants = new ArrayList<>();
        List<Inscription> inscriptions = rechercheInscriptionParFormation(formation);
        for (Inscription inscription:inscriptions){
            Etudiant etudiant = rechercheEtudiantParInscription(inscription);
            etudiants.add(etudiant);
        }
        return etudiants;
    }

    // La recherche des formations d'un utilisateur
    public List<Formation> rechercheFormationParEtudiant(Etudiant etudiant){
        List<Formation>formations = new ArrayList<>();
        List<Inscription> inscriptions = rechercheInscriptionParEtudiant(etudiant);
        for (Inscription inscription:inscriptions){
            Formation formation = recherceFormationParInscription(inscription);
            formations.add(formation);
        }
        return formations;
    }
}
