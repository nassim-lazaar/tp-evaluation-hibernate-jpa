package ma.projet.classes.etatcivil;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "etatcivil_mariages")
public class Mariage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "nbr_enfant", nullable = false)
    private int nbrEnfant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;

    public Mariage() {
    }

    public Mariage(Homme h, Femme f, LocalDate debut, LocalDate fin, int enfants) {
        this.homme = h;
        this.femme = f;
        this.dateDebut = debut;
        this.dateFin = fin;
        this.nbrEnfant = enfants;
    }

    public int getId() {
        return id;
    }

    public void setId(int idMariage) {
        this.id = idMariage;
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

    public int getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(int enfants) {
        this.nbrEnfant = enfants;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme h) {
        this.homme = h;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme f) {
        this.femme = f;
    }

    @Override
    public String toString() {
        return "Mariage{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrEnfant=" + nbrEnfant +
                '}';
    }
}
