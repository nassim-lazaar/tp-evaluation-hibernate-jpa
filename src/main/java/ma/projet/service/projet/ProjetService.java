package ma.projet.service.projet;

import ma.projet.classes.projet.EmployeTache;
import ma.projet.classes.projet.Projet;
import ma.projet.classes.projet.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
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
    public boolean update(Projet o) {
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
    public boolean delete(Projet o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Projet managed = em.contains(o) ? o : em.merge(o);
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
    public Projet findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Projet.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Projet> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Projet p", Projet.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherTachesPlanifiees(Projet p) {
        if (p == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Tache> list = em.createQuery("SELECT t FROM Tache t WHERE t.projet.id = :pId", Tache.class)
                    .setParameter("pId", p.getId())
                    .getResultList();
            System.out.println("Taches planifiees pour le projet [" + p.getNom() + "] :");
            for (Tache t : list) {
                System.out.println("  - " + t.getNom() + " (Debut: " + t.getDateDebut() + " -> Fin: " + t.getDateFin() + " | Cout: " + t.getPrix() + " DH)");
            }
        } finally {
            em.close();
        }
    }

    public void afficherTachesRealisees(Projet p) {
        if (p == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<EmployeTache> list = em.createQuery("SELECT et FROM EmployeTache et JOIN FETCH et.tache WHERE et.tache.projet.id = :pId", EmployeTache.class)
                    .setParameter("pId", p.getId())
                    .getResultList();

            DateTimeFormatter dtfFmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
            DateTimeFormatter stdFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String debutStr = p.getDateDebut() != null ? p.getDateDebut().format(dtfFmt) : "N/A";

            System.out.println("Projet : " + p.getId() + "\t Nom : " + p.getNom() + "\t Date début : " + debutStr);
            System.out.println("Liste des tâches:");
            System.out.printf("%-4s %-16s %-20s %-15s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            for (EmployeTache et : list) {
                String dDeb = et.getDateDebutReelle() != null ? et.getDateDebutReelle().format(stdFmt) : "N/A";
                String dFin = et.getDateFinReelle() != null ? et.getDateFinReelle().format(stdFmt) : "En cours";
                System.out.printf("%-4d %-16s %-20s %-15s%n", et.getTache().getId(), et.getTache().getNom(), dDeb, dFin);
            }
        } finally {
            em.close();
        }
    }
}
