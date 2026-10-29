package ma.projet.service.projet;

import ma.projet.classes.projet.Employe;
import ma.projet.classes.projet.EmployeTache;
import ma.projet.classes.projet.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employe managed = em.contains(o) ? o : em.merge(o);
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
    public Employe findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Employe.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Employe> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherTachesParEmploye(Employe e) {
        if (e == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<EmployeTache> assignations = em.createQuery("SELECT et FROM EmployeTache et JOIN FETCH et.tache WHERE et.employe.id = :empId", EmployeTache.class)
                    .setParameter("empId", e.getId())
                    .getResultList();
            System.out.println("Taches assignees a l'employe [" + e.getNom() + " " + e.getPrenom() + "] (" + assignations.size() + ") :");
            for (EmployeTache et : assignations) {
                System.out.println("  - " + et.getTache().getNom() + " | Debut reel: " + et.getDateDebutReelle() + " | Fin reelle: " + et.getDateFinReelle());
            }
        } finally {
            em.close();
        }
    }

    public void afficherProjetsParEmploye(Employe e) {
        if (e == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Projet> list = em.createQuery("SELECT p FROM Projet p WHERE p.chefDeProjet.id = :empId", Projet.class)
                    .setParameter("empId", e.getId())
                    .getResultList();
            System.out.println("Projets supervises par [" + e.getNom() + " " + e.getPrenom() + "] (" + list.size() + ") :");
            for (Projet p : list) {
                System.out.println("  - " + p.getNom() + " (Debut: " + p.getDateDebut() + " -> Fin: " + p.getDateFin() + ")");
            }
        } finally {
            em.close();
        }
    }
}
