package ma.projet.test;

import ma.projet.classes.etatcivil.Femme;
import ma.projet.classes.etatcivil.Homme;
import ma.projet.classes.etatcivil.Mariage;
import ma.projet.classes.projet.Employe;
import ma.projet.classes.projet.EmployeTache;
import ma.projet.classes.projet.Projet;
import ma.projet.classes.projet.Tache;
import ma.projet.classes.stock.Categorie;
import ma.projet.classes.stock.Commande;
import ma.projet.classes.stock.LigneCommandeProduit;
import ma.projet.classes.stock.Produit;
import ma.projet.service.etatcivil.FemmeService;
import ma.projet.service.etatcivil.HommeService;
import ma.projet.service.etatcivil.MariageService;
import ma.projet.service.projet.EmployeService;
import ma.projet.service.projet.EmployeTacheService;
import ma.projet.service.projet.ProjetService;
import ma.projet.service.projet.TacheService;
import ma.projet.service.stock.CategorieService;
import ma.projet.service.stock.CommandeService;
import ma.projet.service.stock.LigneCommandeService;
import ma.projet.service.stock.ProduitService;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("   EVALUATION FINALE HIBERNATE & JPA - 3 EXERCICES DE SYNTHESE          ");
        System.out.println("========================================================================");

        try {
            // =================================================================
            // EXERCICE 1 : GESTION DE STOCK
            // =================================================================
            System.out.println("\n########################################################################");
            System.out.println("### EXERCICE 1 : GESTION DE STOCK (PRODUITS, COMMANDES & CATEGORIES)  ###");
            System.out.println("########################################################################");

            CategorieService catService = new CategorieService();
            ProduitService prodService = new ProduitService();
            CommandeService cmdService = new CommandeService();
            LigneCommandeService ligneService = new LigneCommandeService();

            Categorie catInfo = new Categorie("INFO", "Matériel Informatique");
            Categorie catAcc = new Categorie("ACC", "Accessoires");
            catService.create(catInfo);
            catService.create(catAcc);

            Produit p1 = new Produit("ES12", 120.0f, catInfo);
            Produit p2 = new Produit("ZR85", 100.0f, catInfo);
            Produit p3 = new Produit("EE85", 200.0f, catInfo);
            Produit p4 = new Produit("KB44", 80.0f, catAcc);
            prodService.create(p1);
            prodService.create(p2);
            prodService.create(p3);
            prodService.create(p4);

            Commande cmd4 = new Commande(LocalDate.of(2013, 3, 14));
            cmdService.create(cmd4);

            ligneService.create(new LigneCommandeProduit(cmd4, p1, 7));
            ligneService.create(new LigneCommandeProduit(cmd4, p2, 14));
            ligneService.create(new LigneCommandeProduit(cmd4, p3, 5));

            System.out.println("\n--- [1.1] Affichage des produits par categorie ---");
            prodService.afficherProduitsParCategorie(catInfo);

            System.out.println("\n--- [1.2] Produits commandes entre le 01/01/2013 et le 31/12/2013 ---");
            prodService.afficherProduitsCommandesEntreDates(LocalDate.of(2013, 1, 1), LocalDate.of(2013, 12, 31));

            System.out.println("\n--- [1.3] Affichage formaté de la commande 4 ---");
            prodService.afficherProduitsParCommande(cmd4);

            System.out.println("\n--- [1.4] NamedQuery : Produits avec prix > 100 DH ---");
            prodService.afficherProduitsPrixSuperieurs100();

            // =================================================================
            // EXERCICE 2 : GESTION DE PROJETS
            // =================================================================
            System.out.println("\n########################################################################");
            System.out.println("### EXERCICE 2 : GESTION DE PROJETS (TACHES, EMPLOYES & PLANNING)    ###");
            System.out.println("########################################################################");

            EmployeService empService = new EmployeService();
            ProjetService projService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService etService = new EmployeTacheService();

            Employe chef = new Employe("Nassim", "Lazaar", "0612345678");
            Employe dev = new Employe("Karim", "Mansouri", "0698765432");
            empService.create(chef);
            empService.create(dev);

            Projet projetStock = new Projet("Gestion de stock", LocalDate.of(2013, 1, 14), LocalDate.of(2013, 6, 30), chef);
            projService.create(projetStock);

            Tache t1 = new Tache("Analyse", LocalDate.of(2013, 2, 1), LocalDate.of(2013, 2, 28), 1200.0, projetStock);
            Tache t2 = new Tache("Conception", LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 31), 1500.0, projetStock);
            Tache t3 = new Tache("Développement", LocalDate.of(2013, 4, 1), LocalDate.of(2013, 5, 15), 3500.0, projetStock);
            Tache t4 = new Tache("Tests Unitaires", LocalDate.of(2013, 5, 16), LocalDate.of(2013, 5, 31), 800.0, projetStock);
            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);
            tacheService.create(t4);

            etService.create(new EmployeTache(dev, t1, LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20)));
            etService.create(new EmployeTache(dev, t2, LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15)));
            etService.create(new EmployeTache(dev, t3, LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25)));

            System.out.println("\n--- [2.1] Projets geres par l'employe ---");
            empService.afficherProjetsParEmploye(chef);

            System.out.println("\n--- [2.2] Taches realisees par l'employe ---");
            empService.afficherTachesParEmploye(dev);

            System.out.println("\n--- [2.3] Affichage formaté des tâches réalisées pour le projet ---");
            projService.afficherTachesRealisees(projetStock);

            System.out.println("\n--- [2.4] NamedQuery : Taches avec prix > 1000 DH ---");
            tacheService.afficherTachesPrixSuperieur1000();

            System.out.println("\n--- [2.5] Taches realisees entre deux dates ---");
            tacheService.afficherTachesRealiseesEntreDates(LocalDate.of(2013, 2, 1), LocalDate.of(2013, 4, 30));

            // =================================================================
            // EXERCICE 3 : GESTION DE L'ETAT CIVIL
            // =================================================================
            System.out.println("\n########################################################################");
            System.out.println("### EXERCICE 3 : GESTION DE L'ETAT CIVIL (HOMMES, FEMMES & MARIAGES) ###");
            System.out.println("########################################################################");

            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();

            // Point 1 : Creation de 10 femmes et 5 hommes
            Femme f1 = new Femme("Rami", "Salima", "0600000001", "Rabat", LocalDate.of(1968, 5, 12));
            Femme f2 = new Femme("Ali", "Amal", "0600000002", "Casablanca", LocalDate.of(1974, 9, 21));
            Femme f3 = new Femme("Alaoui", "Wafa", "0600000003", "Fes", LocalDate.of(1978, 11, 4));
            Femme f4 = new Femme("Alami", "Karima", "0600000004", "Tanger", LocalDate.of(1965, 3, 1));
            Femme f5 = new Femme("Bennani", "Fatima", "0600000005", "Marrakech", LocalDate.of(1982, 8, 15));
            Femme f6 = new Femme("Idrissi", "Imane", "0600000006", "Agadir", LocalDate.of(1985, 2, 19));
            Femme f7 = new Femme("Tahiri", "Nadia", "0600000007", "Oujda", LocalDate.of(1979, 6, 27));
            Femme f8 = new Femme("Chraibi", "Zineb", "0600000008", "Meknes", LocalDate.of(1988, 12, 10));
            Femme f9 = new Femme("Mansouri", "Sanaa", "0600000009", "Tetouan", LocalDate.of(1990, 4, 30));
            Femme f10 = new Femme("Daoudi", "Khadija", "0600000010", "Kenitra", LocalDate.of(1986, 7, 18));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);

            Homme h1 = new Homme("Safi", "Said", "0611111111", "Casablanca", LocalDate.of(1965, 1, 10));
            Homme h2 = new Homme("El Amrani", "Hassan", "0622222222", "Rabat", LocalDate.of(1970, 7, 22));
            Homme h3 = new Homme("Tazi", "Omar", "0633333333", "Fes", LocalDate.of(1975, 4, 18));
            Homme h4 = new Homme("Berrada", "Mehdi", "0644444444", "Tanger", LocalDate.of(1980, 10, 5));
            Homme h5 = new Homme("Fassi", "Youssef", "0655555555", "Marrakech", LocalDate.of(1983, 12, 1));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);

            // Mariages pour Said Safi (3 en cours, 1 echoue)
            mariageService.create(new Mariage(h1, f1, LocalDate.of(1990, 9, 3), null, 4));
            mariageService.create(new Mariage(h1, f2, LocalDate.of(1995, 9, 3), null, 2));
            mariageService.create(new Mariage(h1, f3, LocalDate.of(2000, 11, 4), null, 3));
            mariageService.create(new Mariage(h1, f4, LocalDate.of(1989, 9, 3), LocalDate.of(1990, 9, 3), 0));

            // Mariages pour créer des femmes mariées au moins 2 fois (Karima Alami se remarie avec Hassan)
            mariageService.create(new Mariage(h2, f4, LocalDate.of(1992, 1, 10), null, 2));
            mariageService.create(new Mariage(h2, f5, LocalDate.of(2005, 6, 15), null, 1));

            // Homme marié à 4 femmes (Hassan marié avec 4 femmes)
            mariageService.create(new Mariage(h2, f6, LocalDate.of(2008, 3, 20), null, 1));
            mariageService.create(new Mariage(h2, f7, LocalDate.of(2010, 9, 12), null, 2));

            // Point 2 : Afficher la liste des femmes
            System.out.println("\n--- [3.1] Liste de toutes les femmes enregistrees (" + femmeService.findAll().size() + ") ---");
            for (Femme f : femmeService.findAll()) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom() + " (Nee le: " + f.getDateNaissance() + ")");
            }

            // Point 3 : Afficher la femme la plus âgée
            System.out.println("\n--- [3.2] Femme la plus agee ---");
            Femme ainee = femmeService.trouverFemmePlusAgee();
            if (ainee != null) {
                System.out.println("  -> " + ainee.getNom() + " " + ainee.getPrenom() + " (Nee le: " + ainee.getDateNaissance() + ")");
            }

            // Point 4 : Afficher les épouses d'un homme donné
            System.out.println("\n--- [3.3] Epouses de SAFI SAID entre 1988 et 2026 ---");
            hommeService.afficherEpousesEntreDates(h1, LocalDate.of(1988, 1, 1), LocalDate.of(2026, 12, 31));

            // Point 5 : Nombre d'enfants d'une femme entre deux dates (Native Query)
            System.out.println("\n--- [3.4] Nombre d'enfants d'une femme entre deux dates (NamedNativeQuery) ---");
            femmeService.afficherNombreEnfantsEntreDates(f1, LocalDate.of(1989, 1, 1), LocalDate.of(2026, 12, 31));

            // Point 6 : Femmes mariées 2 fois ou plus (NamedQuery)
            System.out.println("\n--- [3.5] Femmes mariees 2 fois ou plus (NamedQuery) ---");
            femmeService.afficherFemmesMarieesAuMoinsDeuxFois();

            // Point 7 : Hommes mariés à 4 femmes entre deux dates (Criteria API)
            System.out.println("\n--- [3.6] Hommes maries a 4 femmes (Criteria API) ---");
            long nbHommes4 = hommeService.compterHommesMariesQuatreFemmes(LocalDate.of(1980, 1, 1), LocalDate.of(2026, 12, 31));
            System.out.println("Nombre d'hommes maries a 4 femmes : " + nbHommes4);

            // Point 8 : Afficher les mariages d'un homme avec tous les détails (En cours vs Echoues)
            System.out.println("\n--- [3.7] Details des mariages de SAFI SAID (En cours & Echoues) ---");
            hommeService.afficherMariagesHomme(h1);

            System.out.println("\n========================================================================");
            System.out.println("   EVALUATION REUSSIE AVEC SUCCES : LES 3 EXERCICES SONT VALIDES        ");
            System.out.println("========================================================================");

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
