package ma.projet.service.stock;

import ma.projet.classes.stock.Categorie;
import ma.projet.classes.stock.Commande;
import ma.projet.classes.stock.LigneCommandeProduit;
import ma.projet.classes.stock.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(Produit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Produit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Produit managed = em.contains(o) ? o : em.merge(o);
            em.remove(managed);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Produit findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produit> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherProduitsParCategorie(Categorie c) {
        if (c == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Produit> list = em.createQuery("SELECT p FROM Produit p WHERE p.categorie.id = :catId", Produit.class)
                    .setParameter("catId", c.getId())
                    .getResultList();
            System.out.println("Produits de la categorie [" + c.getLibelle() + "] :");
            for (Produit p : list) {
                System.out.println("  - Ref: " + p.getReference() + " | Prix: " + p.getPrix() + " DH");
            }
        } finally {
            em.close();
        }
    }

    public void afficherProduitsCommandesEntreDates(LocalDate d1, LocalDate d2) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT l.produit FROM LigneCommandeProduit l WHERE l.commande.date BETWEEN :d1 AND :d2";
            List<Produit> list = em.createQuery(jpql, Produit.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
            System.out.println("Produits commandes entre " + d1 + " et " + d2 + " (" + list.size() + " references) :");
            for (Produit p : list) {
                System.out.println("  - " + p.getReference() + " (Prix: " + p.getPrix() + " DH)");
            }
        } finally {
            em.close();
        }
    }

    public void afficherProduitsParCommande(Commande c) {
        if (c == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<LigneCommandeProduit> lignes = em.createQuery("SELECT l FROM LigneCommandeProduit l JOIN FETCH l.produit WHERE l.commande.id = :cmdId", LigneCommandeProduit.class)
                    .setParameter("cmdId", c.getId())
                    .getResultList();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
            String dateStr = c.getDate() != null ? c.getDate().format(formatter) : "N/A";

            System.out.println("Commande : " + c.getId() + "\t Date : " + dateStr);
            System.out.println("Liste des produits :");
            System.out.printf("%-12s %-10s %-10s%n", "Référence", "Prix", "Quantité");
            for (LigneCommandeProduit l : lignes) {
                System.out.printf("%-12s %-10s %-10d%n", l.getProduit().getReference(), (int) l.getProduit().getPrix() + " DH", l.getQuantite());
            }
        } finally {
            em.close();
        }
    }

    public void afficherProduitsPrixSuperieurs100() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Produit> list = em.createNamedQuery("Produit.findByPrixSuperieur100", Produit.class).getResultList();
            System.out.println("Produits avec un prix > 100 DH (NamedQuery) :");
            for (Produit p : list) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
            }
        } finally {
            em.close();
        }
    }
}
