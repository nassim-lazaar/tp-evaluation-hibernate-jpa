package ma.projet.classes.projet;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projet_projets")
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom_projet", nullable = false, length = 150)
    private String nom;

    @Column(name = "date_debut_projet", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin_projet")
    private LocalDate dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe chefDeProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tache> taches = new ArrayList<>();

    public Projet() {
    }

    public Projet(String nomProjet, LocalDate debut, LocalDate fin, Employe chef) {
        this.nom = nomProjet;
        this.dateDebut = debut;
        this.dateFin = fin;
        this.chefDeProjet = chef;
    }

    public int getId() {
        return id;
    }

    public void setId(int idProjet) {
        this.id = idProjet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomProjet) {
        this.nom = nomProjet;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate debut) {
        this.dateDebut = debut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate fin) {
        this.dateFin = fin;
    }

    public Employe getChefDeProjet() {
        return chefDeProjet;
    }

    public void setChefDeProjet(Employe chef) {
        this.chefDeProjet = chef;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> listeTaches) {
        this.taches = listeTaches;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                '}';
    }
}
