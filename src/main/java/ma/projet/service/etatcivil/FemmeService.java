package ma.projet.service.etatcivil;

import ma.projet.classes.etatcivil.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Femme managed = em.contains(o) ? o : em.merge(o);
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
    public Femme findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Femme.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT f FROM Femme f", Femme.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Femme trouverFemmePlusAgee() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Femme> list = em.createQuery("SELECT f FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    public void afficherFemmesMarieesAuMoinsDeuxFois() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Femme> list = em.createNamedQuery("Femme.findMarriedAtLeastTwice", Femme.class).getResultList();
            System.out.println("Femmes mariees au moins deux fois (" + list.size() + ") :");
            for (Femme f : list) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom() + " (Nombre de mariages: " + f.getMariages().size() + ")");
            }
        } finally {
            em.close();
        }
    }

    public int afficherNombreEnfantsEntreDates(Femme f, LocalDate d1, LocalDate d2) {
        if (f == null) return 0;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Object res = em.createNamedQuery("Femme.countChildrenBetweenDates")
                    .setParameter("femmeId", f.getId())
                    .setParameter("d1", java.sql.Date.valueOf(d1))
                    .setParameter("d2", java.sql.Date.valueOf(d2))
                    .getSingleResult();

            int totalEnfants = 0;
            if (res instanceof Number) {
                totalEnfants = ((Number) res).intValue();
            }
            System.out.println("Nombre d'enfants pour [" + f.getNom() + " " + f.getPrenom() + "] entre " + d1 + " et " + d2 + " : " + totalEnfants);
            return totalEnfants;
        } finally {
            em.close();
        }
    }
}
