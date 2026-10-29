package ma.projet.classes.projet;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projet_employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK id = new EmployeTachePK();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Column(name = "date_debut_reelle", nullable = false)
    private LocalDate dateDebutReelle;

    @Column(name = "date_fin_reelle")
    private LocalDate dateFinReelle;

    public EmployeTache() {
    }

    public EmployeTache(Employe emp, Tache t, LocalDate debutReel, LocalDate finReelle) {
        this.employe = emp;
        this.tache = t;
        this.dateDebutReelle = debutReel;
        this.dateFinReelle = finReelle;
        if (emp != null && t != null) {
            this.id = new EmployeTachePK(emp.getId(), t.getId());
        }
    }

    public EmployeTachePK getId() {
        return id;
    }

    public void setId(EmployeTachePK idCle) {
        this.id = idCle;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe emp) {
        this.employe = emp;
        if (emp != null) {
            this.id.setEmployeId(emp.getId());
        }
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache t) {
        this.tache = t;
        if (t != null) {
            this.id.setTacheId(t.getId());
        }
    }

    public LocalDate getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(LocalDate debutReel) {
        this.dateDebutReelle = debutReel;
    }

    public LocalDate getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(LocalDate finReelle) {
        this.dateFinReelle = finReelle;
    }
}
