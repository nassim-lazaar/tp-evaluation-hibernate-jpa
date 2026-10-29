package ma.projet;

import ma.projet.classes.etatcivil.Femme;
import ma.projet.classes.etatcivil.Homme;
import ma.projet.classes.etatcivil.Mariage;
import ma.projet.classes.projet.Employe;
import ma.projet.classes.projet.Projet;
import ma.projet.classes.projet.Tache;
import ma.projet.classes.stock.Categorie;
import ma.projet.classes.stock.Produit;
import ma.projet.service.etatcivil.FemmeService;
import ma.projet.service.etatcivil.HommeService;
import ma.projet.service.etatcivil.MariageService;
import ma.projet.service.projet.EmployeService;
import ma.projet.service.projet.ProjetService;
import ma.projet.service.projet.TacheService;
import ma.projet.service.stock.CategorieService;
import ma.projet.service.stock.ProduitService;
import ma.projet.util.HibernateUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class EvaluationTest {

    private CategorieService catService;
    private ProduitService prodService;
    private EmployeService empService;
    private ProjetService projService;
    private TacheService tacheService;
    private HommeService hommeService;
    private FemmeService femmeService;
    private MariageService mariageService;

    @Before
    public void setUp() {
        catService = new CategorieService();
        prodService = new ProduitService();
        empService = new EmployeService();
        projService = new ProjetService();
        tacheService = new TacheService();
        hommeService = new HommeService();
        femmeService = new FemmeService();
        mariageService = new MariageService();
    }

    @AfterClass
    public static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    public void testExercice1Stock() {
        Categorie cat = new Categorie("TEST_CAT", "Test Categorie");
        assertTrue(catService.create(cat));

        Produit p = new Produit("REF_TEST_01", 150.0f, cat);
        assertTrue(prodService.create(p));

        Produit retrieved = prodService.findById(p.getId());
        assertNotNull(retrieved);
        assertEquals("REF_TEST_01", retrieved.getReference());
        assertEquals(150.0f, retrieved.getPrix(), 0.01);
    }

    @Test
    public void testExercice2Projets() {
        Employe emp = new Employe("TestNom", "TestPrenom", "0600000000");
        assertTrue(empService.create(emp));

        Projet proj = new Projet("Test Projet", LocalDate.now(), LocalDate.now().plusMonths(3), emp);
        assertTrue(projService.create(proj));

        Tache t = new Tache("Tache Test", LocalDate.now(), LocalDate.now().plusWeeks(2), 2000.0, proj);
        assertTrue(tacheService.create(t));

        Tache found = tacheService.findById(t.getId());
        assertNotNull(found);
        assertTrue(found.getPrix() > 1000);
    }

    @Test
    public void testExercice3EtatCivil() {
        Homme h = new Homme("TestH", "Said", "0611111111", "Casablanca", LocalDate.of(1970, 1, 1));
        Femme f = new Femme("TestF", "Salima", "0622222222", "Rabat", LocalDate.of(1975, 5, 5));
        assertTrue(hommeService.create(h));
        assertTrue(femmeService.create(f));

        Mariage m = new Mariage(h, f, LocalDate.of(1995, 9, 3), null, 3);
        assertTrue(mariageService.create(m));

        Femme ainee = femmeService.trouverFemmePlusAgee();
        assertNotNull(ainee);
    }
}
