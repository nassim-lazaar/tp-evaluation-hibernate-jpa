package ma.projet.classes.etatcivil;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "etatcivil_femmes")
@NamedQueries({
        @NamedQuery(name = "Femme.findMarriedAtLeastTwice", query = "SELECT f FROM Femme f JOIN f.mariages m GROUP BY f HAVING COUNT(m) >= 2")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Femme.countChildrenBetweenDates", query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM etatcivil_mariages m WHERE m.femme_id = :femmeId AND m.date_debut >= :d1 AND (m.date_fin <= :d2 OR m.date_fin IS NULL)")
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {
        super();
    }

    public Femme(String nomF, String prenomF, String telF, String adrF, LocalDate dateNais) {
        super(nomF, prenomF, telF, adrF, dateNais);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> listeMariages) {
        this.mariages = listeMariages;
    }
}
