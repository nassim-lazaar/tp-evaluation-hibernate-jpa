package ma.projet.service.projet;

import ma.projet.classes.projet.EmployeTache;
import ma.projet.classes.projet.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tache managed = em.contains(o) ? o : em.merge(o);
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
    public Tache findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Tache.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tache> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tache t", Tache.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherTachesPrixSuperieur1000() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Tache> list = em.createNamedQuery("Tache.findByPrixSuperieur1000", Tache.class).getResultList();
            System.out.println("Taches dont le cout est superieur a 1000 DH (NamedQuery) :");
            for (Tache t : list) {
                System.out.println("  - [ID: " + t.getId() + "] " + t.getNom() + " : " + t.getPrix() + " DH");
            }
        } finally {
            em.close();
        }
    }

    public void afficherTachesRealiseesEntreDates(LocalDate d1, LocalDate d2) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            String jpql = "SELECT et FROM EmployeTache et JOIN FETCH et.tache WHERE et.dateDebutReelle >= :d1 AND (et.dateFinReelle <= :d2 OR et.dateFinReelle IS NULL)";
            List<EmployeTache> list = em.createQuery(jpql, EmployeTache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
            System.out.println("Taches realisees entre " + d1 + " et " + d2 + " (" + list.size() + ") :");
            for (EmployeTache et : list) {
                System.out.println("  - Tache: " + et.getTache().getNom() + " | Debut reel: " + et.getDateDebutReelle() + " | Fin reelle: " + et.getDateFinReelle());
            }
        } finally {
            em.close();
        }
    }
}
