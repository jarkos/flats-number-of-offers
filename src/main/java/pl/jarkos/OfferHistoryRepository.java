package pl.jarkos;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Repository
class OfferHistoryRepository extends AbstractRepository {

    List<?> getAll() {
        String hql = "FROM OfferHistory AS oh";
        if (getSessionFactory().getCurrentSession().getTransaction() != null) {
            getSessionFactory().getCurrentSession().beginTransaction();
        }
        Query<?> query = getSessionFactory().getCurrentSession().createQuery(hql);
        return query.list();
    }

    OfferHistory get(String city, LocalDate date) {
        String hql = "FROM OfferHistory AS oh WHERE oh.city = :city AND oh.date = :date";
        if (getSessionFactory().getCurrentSession().getTransaction() != null) {
            getSessionFactory().getCurrentSession().beginTransaction();
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("city", city);
        query.setParameter("date", date);
        return (OfferHistory) query.uniqueResult();
    }

    List<OfferHistory> getByCity(String city) {
        String hql = "FROM OfferHistory AS oh WHERE oh.city = :city ORDER BY oh.date";
        SessionFactory sf = getSessionFactory();
        Session currentSession = sf.getCurrentSession();
        if (!currentSession.getTransaction().isActive()) {
            currentSession.beginTransaction();
        }
        Query query = currentSession.createQuery(hql);
        query.setParameter("city", city);
        return (List<OfferHistory>) query.list();
    }

    void saveOrUpdate(OfferHistory oh) {
        SessionFactory sf = getSessionFactory();
        Session currentSession = sf.getCurrentSession();
        org.hibernate.Transaction tr;
        if (!currentSession.getTransaction().isActive()) {
            tr = currentSession.beginTransaction();
        } else {
            tr = currentSession.getTransaction();
        }
        try {
            currentSession.saveOrUpdate(oh);
            tr.commit();
        } catch (org.hibernate.exception.ConstraintViolationException cve) {
            log.info("Already in DB: " + oh.toString());
            tr.rollback();
        }

    }

}
