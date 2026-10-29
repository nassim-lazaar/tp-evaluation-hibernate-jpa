package ma.projet.classes.projet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projet_employes")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom_emp", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom_emp", nullable = false, length = 100)
    private String prenom;

    @Column(name = "tel_emp", length = 30)
    private String telephone;

    @OneToMany(mappedBy = "chefDeProjet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Projet> projets = new ArrayList<>();

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeTache> employeTaches = new ArrayList<>();

    public Employe() {
    }

    public Employe(String nomEmp, String prenomEmp, String telEmp) {
        this.nom = nomEmp;
        this.prenom = prenomEmp;
        this.telephone = telEmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int idEmp) {
        this.id = idEmp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomEmp) {
        this.nom = nomEmp;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenomEmp) {
        this.prenom = prenomEmp;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telEmp) {
        this.telephone = telEmp;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> listeProjets) {
        this.projets = listeProjets;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> listeAssignations) {
        this.employeTaches = listeAssignations;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
