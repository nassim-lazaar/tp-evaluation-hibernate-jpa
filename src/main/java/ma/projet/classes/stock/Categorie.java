package ma.projet.classes.stock;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock_categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code_cat", nullable = false, length = 50)
    private String code;

    @Column(name = "libelle_cat", nullable = false, length = 150)
    private String libelle;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {
    }

    public Categorie(String codeCat, String libelleCat) {
        this.code = codeCat;
        this.libelle = libelleCat;
    }

    public int getId() {
        return id;
    }

    public void setId(int idCat) {
        this.id = idCat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String codeCat) {
        this.code = codeCat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelleCat) {
        this.libelle = libelleCat;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> listeProduits) {
        this.produits = listeProduits;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
