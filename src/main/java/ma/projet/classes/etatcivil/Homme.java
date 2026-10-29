package ma.projet.classes.etatcivil;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "etatcivil_hommes")
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();

    public Homme() {
        super();
    }

    public Homme(String nomH, String prenomH, String telH, String adrH, LocalDate dateNais) {
        super(nomH, prenomH, telH, adrH, dateNais);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> listeMariages) {
        this.mariages = listeMariages;
    }
}
