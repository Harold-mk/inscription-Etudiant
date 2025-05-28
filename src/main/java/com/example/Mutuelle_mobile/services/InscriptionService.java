package com.example.Mutuelle_mobile.services;

import com.example.Mutuelle_mobile.entites.Etudiant;
import com.example.Mutuelle_mobile.entites.Formation;
import com.example.Mutuelle_mobile.entites.Inscription;
import com.example.Mutuelle_mobile.repository.EtudiantRepository;
import com.example.Mutuelle_mobile.repository.InscriptionRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
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
        verification= inscriptionsDansLaBD != null;
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

    /**
     * Génère un PDF pour la fiche d'inscription d'un étudiant
     * @param inscription L'inscription pour laquelle générer le PDF
     * @return ResponseEntity contenant le PDF généré
     */
    public ResponseEntity<byte[]> genererPdfInscription(Inscription inscription) {
        try {
            // Récupérer l'étudiant et la formation
            Etudiant etudiant = inscription.getEtudiant();
            Formation formation = inscription.getFormation();

            // Vérifier si l'étudiant et la formation existent
            if (etudiant == null || formation == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Créer un document PDF
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            // Ouvrir le document
            document.open();

            // Ajouter le titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Fiche d'Inscription", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Espace

            // Ajouter les informations de l'étudiant
            document.add(new Paragraph("Informations de l'étudiant:"));
            document.add(new Paragraph("Nom: " + etudiant.getNom()));
            document.add(new Paragraph("Prénom: " + etudiant.getPrenom()));
            document.add(new Paragraph("Adresse: " + etudiant.getAdresse()));
            document.add(new Paragraph("Email: " + etudiant.getEmail()));
            document.add(new Paragraph("Téléphone: " + etudiant.getTelephone()));
            document.add(new Paragraph("Date de naissance: " + etudiant.getDateNaissance()));
            document.add(new Paragraph(" ")); // Espace

            // Ajouter les informations de la formation
            document.add(new Paragraph("Informations de la formation:"));
            document.add(new Paragraph("Libellé: " + formation.getLibelle()));
            document.add(new Paragraph("Description: " + formation.getDescription()));
            document.add(new Paragraph(" ")); // Espace

            // Ajouter la date d'inscription
            document.add(new Paragraph("Date d'inscription: " + new Date()));

            // Fermer le document
            document.close();

            // Préparer la réponse HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "inscription_" + etudiant.getNom() + "_" + etudiant.getPrenom() + ".pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (DocumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
