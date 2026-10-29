package ma.projet.classes.stock;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitPK implements Serializable {

    @Column(name = "commande_id")
    private int commandeId;

    @Column(name = "produit_id")
    private int produitId;

    public LigneCommandeProduitPK() {
    }

    public LigneCommandeProduitPK(int cmdId, int prodId) {
        this.commandeId = cmdId;
        this.produitId = prodId;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int cmdId) {
        this.commandeId = cmdId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int prodId) {
        this.produitId = prodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigneCommandeProduitPK that = (LigneCommandeProduitPK) o;
        return commandeId == that.commandeId && produitId == that.produitId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandeId, produitId);
    }
}
