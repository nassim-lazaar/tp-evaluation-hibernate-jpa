package ma.projet.classes.stock;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock_produits")
@NamedQueries({
        @NamedQuery(name = "Produit.findByPrixSuperieur100", query = "SELECT p FROM Produit p WHERE p.prix > 100")
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_produit", unique = true, nullable = false, length = 50)
    private String reference;

    @Column(name = "prix_produit", nullable = false)
    private float prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Produit() {
    }

    public Produit(String refProduit, float prixArticle, Categorie cat) {
        this.reference = refProduit;
        this.prix = prixArticle;
        this.categorie = cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int idProd) {
        this.id = idProd;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String refProduit) {
        this.reference = refProduit;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prixArticle) {
        this.prix = prixArticle;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie cat) {
        this.categorie = cat;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeProduit> listeLignes) {
        this.lignes = listeLignes;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", prix=" + prix + " DH" +
                '}';
    }
}
