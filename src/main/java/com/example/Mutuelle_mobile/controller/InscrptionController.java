package com.example.Mutuelle_mobile.controller;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.entites.Inscription;
import com.example.Mutuelle_mobile.services.EtudiantService;
import com.example.Mutuelle_mobile.services.FormationService;
import com.example.Mutuelle_mobile.services.InscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscriptions")

public class InscrptionController {

    //Injection des dependances
    private final InscriptionService inscriptionService;
    private final FormationService formationService;
    private final EtudiantService etudiantService;
    public InscrptionController(InscriptionService inscriptionService, FormationService formationService, EtudiantService etudiantService) {
        this.inscriptionService = inscriptionService;
        this.formationService = formationService;
        this.etudiantService = etudiantService;
    }

    //Methode pour inscrire un etudiant et sauvegarder sa formation
    @PostMapping("/inscrire")

    public String saveInscription(@RequestBody Inscription inscription) {
        // Récupérer l'étudiant et la formation depuis l'inscription
        Etudiant etudiant = inscription.getEtudiant();
        Formation formation = inscription.getFormation();

        // Vérifier si l'étudiant et la formation ne sont pas null
        if (etudiant == null || formation == null) {
            return "Erreur: L'étudiant et la formation sont requis";
        }

        // Appeler le service pour sauvegarder l'inscription
        return inscriptionService.AjoutInscription(inscription, etudiant, formation);
    }

    //Methode pour imprimer la fiche d'inscription d'un étudiant et l'exporter
    @PostMapping("/imprimer")
    public ResponseEntity<byte[]> imprimerFicheInscription(@RequestBody Inscription inscription) {
        // Vérifier si l'inscription existe
        if (inscription == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Appeler le service pour générer le PDF
        return inscriptionService.genererPdfInscription(inscription);
    }
}
