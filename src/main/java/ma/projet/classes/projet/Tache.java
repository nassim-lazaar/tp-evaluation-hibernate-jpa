package ma.projet.classes.projet;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projet_taches")
@NamedQueries({
        @NamedQuery(name = "Tache.findByPrixSuperieur1000", query = "SELECT t FROM Tache t WHERE t.prix > 1000")
})
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom_tache", nullable = false, length = 150)
    private String nom;

    @Column(name = "date_debut_planifiee", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin_planifiee", nullable = false)
    private LocalDate dateFin;

    @Column(name = "cout_prix", nullable = false)
    private double prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeTache> employeTaches = new ArrayList<>();

    public Tache() {
    }

    public Tache(String nomTache, LocalDate debut, LocalDate fin, double cout, Projet p) {
        this.nom = nomTache;
        this.dateDebut = debut;
        this.dateFin = fin;
        this.prix = cout;
        this.projet = p;
    }

    public int getId() {
        return id;
    }

    public void setId(int idTache) {
        this.id = idTache;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomTache) {
        this.nom = nomTache;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double cout) {
        this.prix = cout;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet p) {
        this.projet = p;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> listeAssignations) {
        this.employeTaches = listeAssignations;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
