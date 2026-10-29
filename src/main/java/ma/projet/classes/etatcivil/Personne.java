package ma.projet.classes.etatcivil;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "etatcivil_personnes")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom_pers", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom_pers", nullable = false, length = 100)
    private String prenom;

    @Column(name = "tel_pers", length = 30)
    private String telephone;

    @Column(name = "adresse_pers", length = 200)
    private String adresse;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    public Personne() {
    }

    public Personne(String nomPers, String prenomPers, String telPers, String adrPers, LocalDate dateNais) {
        this.nom = nomPers;
        this.prenom = prenomPers;
        this.telephone = telPers;
        this.adresse = adrPers;
        this.dateNaissance = dateNais;
    }

    public int getId() {
        return id;
    }

    public void setId(int idPers) {
        this.id = idPers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomPers) {
        this.nom = nomPers;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenomPers) {
        this.prenom = prenomPers;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telPers) {
        this.telephone = telPers;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adrPers) {
        this.adresse = adrPers;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNais) {
        this.dateNaissance = dateNais;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
