package ma.projet.service.etatcivil;

import ma.projet.classes.etatcivil.Femme;
import ma.projet.classes.etatcivil.Homme;
import ma.projet.classes.etatcivil.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Homme managed = em.contains(o) ? o : em.merge(o);
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
    public Homme findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Homme.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Homme> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT h FROM Homme h", Homme.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherEpousesEntreDates(Homme h, LocalDate d1, LocalDate d2) {
        if (h == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            String jpql = "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hId AND m.dateDebut >= :d1 AND (m.dateFin <= :d2 OR m.dateFin IS NULL)";
            List<Femme> epouses = em.createQuery(jpql, Femme.class)
                    .setParameter("hId", h.getId())
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
            System.out.println("Epouses de [" + h.getNom() + " " + h.getPrenom() + "] entre " + d1 + " et " + d2 + " (" + epouses.size() + ") :");
            for (Femme f : epouses) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }
        } finally {
            em.close();
        }
    }

    public void afficherMariagesHomme(Homme h) {
        if (h == null) return;
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            List<Mariage> list = em.createQuery("SELECT m FROM Mariage m JOIN FETCH m.femme WHERE m.homme.id = :hId ORDER BY m.dateDebut", Mariage.class)
                    .setParameter("hId", h.getId())
                    .getResultList();

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.println("Nom : " + h.getNom().toUpperCase() + " " + h.getPrenom().toUpperCase());

            List<Mariage> enCours = new ArrayList<>();
            List<Mariage> echoues = new ArrayList<>();

            for (Mariage m : list) {
                if (m.getDateFin() == null) {
                    enCours.add(m);
                } else {
                    echoues.add(m);
                }
            }

            System.out.println("Mariages En Cours :");
            int idx = 1;
            for (Mariage m : enCours) {
                System.out.println(idx + ". Femme : " + m.getFemme().getNom().toUpperCase() + " " + m.getFemme().getPrenom().toUpperCase() +
                        "\t Date Début : " + m.getDateDebut().format(fmt) +
                        "\t Nbr Enfants : " + m.getNbrEnfant());
                idx++;
            }

            System.out.println("\nMariages échoués :");
            idx = 1;
            for (Mariage m : echoues) {
                System.out.println(idx + ". Femme : " + m.getFemme().getNom().toUpperCase() + " " + m.getFemme().getPrenom().toUpperCase() +
                        "\t Date Début : " + m.getDateDebut().format(fmt));
                System.out.println("Date Fin : " + m.getDateFin().format(fmt) + "\t Nbr Enfants : " + m.getNbrEnfant());
                idx++;
            }
        } finally {
            em.close();
        }
    }

    public long compterHommesMariesQuatreFemmes(LocalDate d1, LocalDate d2) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> root = cq.from(Mariage.class);

            List<Predicate> predicates = new ArrayList<>();
            if (d1 != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateDebut"), d1));
            }
            if (d2 != null) {
                predicates.add(cb.or(
                        cb.lessThanOrEqualTo(root.get("dateFin"), d2),
                        cb.isNull(root.get("dateFin"))
                ));
            }
            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            cq.groupBy(root.get("homme"));
            cq.having(cb.ge(cb.countDistinct(root.get("femme")), 4L));
            cq.select(root.get("homme").get("id"));

            List<Long> resultList = em.createQuery(cq).getResultList();
            return resultList != null ? resultList.size() : 0;
        } finally {
            em.close();
        }
    }
}
