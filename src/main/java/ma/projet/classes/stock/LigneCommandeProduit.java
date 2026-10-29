package ma.projet.classes.stock;

import javax.persistence.*;

@Entity
@Table(name = "stock_ligne_commande_produit")
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitPK id = new LigneCommandeProduitPK();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(name = "qte", nullable = false)
    private int quantite;

    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(Commande cmd, Produit prod, int qte) {
        this.commande = cmd;
        this.produit = prod;
        this.quantite = qte;
        if (cmd != null && prod != null) {
            this.id = new LigneCommandeProduitPK(cmd.getId(), prod.getId());
        }
    }

    public LigneCommandeProduitPK getId() {
        return id;
    }

    public void setId(LigneCommandeProduitPK idLigne) {
        this.id = idLigne;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande cmd) {
        this.commande = cmd;
        if (cmd != null) {
            this.id.setCommandeId(cmd.getId());
        }
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit prod) {
        this.produit = prod;
        if (prod != null) {
            this.id.setProduitId(prod.getId());
        }
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int qte) {
        this.quantite = qte;
    }
}
