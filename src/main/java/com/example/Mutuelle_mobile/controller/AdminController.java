package com.example.Mutuelle_mobile.controller;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.entites.Inscription;
import com.example.Mutuelle_mobile.services.EtudiantService;
import com.example.Mutuelle_mobile.services.FormationService;
import com.example.Mutuelle_mobile.services.InscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final FormationService formationService;
    private final EtudiantService etudiantService;
    private final InscriptionService inscriptionService;

    public AdminController(FormationService formationService, EtudiantService etudiantService, InscriptionService inscriptionService) {
        this.formationService = formationService;
        this.etudiantService = etudiantService;
        this.inscriptionService = inscriptionService;
    }

    // =============== GESTION DES FORMATIONS ===============

    // Récupérer toutes les formations
    @GetMapping("/formations")
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.RechercheToutesLesformations();
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    // Récupérer une formation par son libellé
    @GetMapping("/formations/search")
    public ResponseEntity<Formation> getFormationByLibelle(@RequestParam String libelle) {
        Formation formation = formationService.rechercheParLibelle(libelle);
        if (formation != null) {
            return new ResponseEntity<>(formation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Ajouter une nouvelle formation
    @PostMapping("/formations")
    public ResponseEntity<String> addFormation(@RequestBody Formation formation) {
        formationService.ajoutFormation(formation);
        return new ResponseEntity<>("Formation ajoutée avec succès", HttpStatus.CREATED);
    }

    // Modifier une formation existante
    @PutMapping("/formations")
    public ResponseEntity<String> updateFormation(@RequestBody Formation formation) {
        String result = formationService.modifierFormation(formation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Supprimer une formation
    @DeleteMapping("/formations")
    public ResponseEntity<String> deleteFormation(@RequestBody Formation formation) {
        String result = formationService.supprimerFormation(formation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // =============== GESTION DES ÉTUDIANTS ===============

    // Récupérer tous les étudiants
    @GetMapping("/etudiants")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.ListeTotalDesEtudiants();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    // Récupérer un étudiant par son email
    @GetMapping("/etudiants/search")
    public ResponseEntity<Etudiant> getEtudiantByEmail(@RequestParam String email) {
        Etudiant etudiant = etudiantService.rechercheEtudiantParMail(email);
        if (etudiant != null) {
            return new ResponseEntity<>(etudiant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer des étudiants par leur nom
    @GetMapping("/etudiants/search-by-nom")
    public ResponseEntity<List<Etudiant>> getEtudiantsByNom(@RequestParam String nom) {
        List<Etudiant> etudiants = etudiantService.RechercheEtudiantParNom(nom);
        if (!etudiants.isEmpty()) {
            return new ResponseEntity<>(etudiants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer des étudiants par leur prénom
    @GetMapping("/etudiants/search-by-prenom")
    public ResponseEntity<List<Etudiant>> getEtudiantsByPrenom(@RequestParam String prenom) {
        List<Etudiant> etudiants = etudiantService.RechercheEtudiantParPrenom(prenom);
        if (!etudiants.isEmpty()) {
            return new ResponseEntity<>(etudiants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Modifier un étudiant existant
    @PutMapping("/etudiants")
    public ResponseEntity<String> updateEtudiant(@RequestBody Etudiant etudiant) {
        String result = etudiantService.modifierEtudiant(etudiant);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Récupérer les étudiants par date d'inscription (triés)
    @GetMapping("/etudiants/by-date")
    public ResponseEntity<List<Etudiant>> getEtudiantsByDate() {
        List<Inscription> inscriptions = inscriptionService.ListeDesInscriptions();

        // Trier les inscriptions par date
        inscriptions.sort(Comparator.comparing(Inscription::getDateinscription));

        // Extraire les étudiants uniques
        List<Etudiant> etudiants = inscriptions.stream()
                .map(Inscription::getEtudiant)
                .distinct()
                .collect(Collectors.toList());

        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    // Récupérer les étudiants par formation
    @GetMapping("/etudiants/by-formation/{formationLibelle}")
    public ResponseEntity<List<Etudiant>> getEtudiantsByFormation(@PathVariable String formationLibelle) {
        Formation formation = formationService.rechercheParLibelle(formationLibelle);
        if (formation != null) {
            List<Etudiant> etudiants = inscriptionService.rechercheEtudiantParFormation(formation);
            return new ResponseEntity<>(etudiants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // =============== GESTION DES INSCRIPTIONS ===============

    // Récupérer toutes les inscriptions
    @GetMapping("/inscriptions")
    public ResponseEntity<List<Inscription>> getAllInscriptions() {
        List<Inscription> inscriptions = inscriptionService.ListeDesInscriptions();
        return new ResponseEntity<>(inscriptions, HttpStatus.OK);
    }

    // Récupérer les inscriptions par formation
    @GetMapping("/inscriptions/by-formation/{formationLibelle}")
    public ResponseEntity<List<Inscription>> getInscriptionsByFormation(@PathVariable String formationLibelle) {
        Formation formation = formationService.rechercheParLibelle(formationLibelle);
        if (formation != null) {
            List<Inscription> inscriptions = inscriptionService.rechercheInscriptionParFormation(formation);
            return new ResponseEntity<>(inscriptions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer les inscriptions par étudiant
    @GetMapping("/inscriptions/by-etudiant/{email}")
    public ResponseEntity<List<Inscription>> getInscriptionsByEtudiant(@PathVariable String email) {
        Etudiant etudiant = etudiantService.rechercheEtudiantParMail(email);
        if (etudiant != null) {
            List<Inscription> inscriptions = inscriptionService.rechercheInscriptionParEtudiant(etudiant);
            return new ResponseEntity<>(inscriptions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
