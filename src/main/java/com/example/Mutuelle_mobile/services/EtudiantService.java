package com.example.Mutuelle_mobile.services;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService {
    //injection des dependances
    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    //Verification de l'existence d'un etudiant en BD
    public boolean etudiantVerification(Etudiant etudiant){
        boolean verification= true;
        Etudiant etudiantDansLaBD = etudiantRepository.findByemail(etudiant.getEmail());
        if (etudiantDansLaBD == null){
            verification = false;
        }
        else{
            verification= true;
        }
        return verification;
    }
    public void ajoutEtudiantDansBD(Etudiant etudiant){
        etudiantRepository.save(etudiant);
    }
    // Fonction de recherches


    // recherche de l'etudiant par nom
    public List<Etudiant> RechercheEtudiantParNom(String nom){
        List<Etudiant> etudiants = etudiantRepository.findByNomContaining(nom);
        return etudiants;
    }

    // recherche de l'etudiant par prenom
    public List<Etudiant> RechercheEtudiantParPrenom(String prenom){
        List<Etudiant> etudiants = etudiantRepository.findByPrenomContaining(prenom);
        return etudiants;
    }

    // recherhce de l'etudiant par mail
    public Etudiant rechercheEtudiantParMail( String mail){
        return etudiantRepository.findByemail(mail);
    }

    // Recuperer la liste des etudiants inscrits
    public List<Etudiant> ListeTotalDesEtudiants (){
        return etudiantRepository.findAll();
    }

    // modification des informations de l'etudiant en Base de donnees
    public String modifierEtudiant (Etudiant etudiant){
        Etudiant etudiantDansLaBd= etudiantRepository.findByemail(etudiant.getEmail());
        etudiant.setId(etudiantDansLaBd.getId());
        etudiantRepository.delete(etudiantDansLaBd);
        etudiantRepository.save(etudiant);
        return "etudiant modifie";
    }
}
