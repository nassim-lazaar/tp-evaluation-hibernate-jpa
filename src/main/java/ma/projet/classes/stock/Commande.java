package ma.projet.classes.stock;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock_commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_cmd", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Commande() {
    }

    public Commande(LocalDate dateCommande) {
        this.date = dateCommande;
    }

    public int getId() {
        return id;
    }

    public void setId(int idCmd) {
        this.id = idCmd;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate dateCommande) {
        this.date = dateCommande;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeProduit> lignesCmd) {
        this.lignes = lignesCmd;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
