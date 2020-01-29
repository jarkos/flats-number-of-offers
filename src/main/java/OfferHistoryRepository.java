import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

@Log4j2
class OfferHistoryRepository extends AbstractRepository {

    List getAll() {
        String hql = "FROM OfferHistory AS oh";

        org.hibernate.Transaction tr = null;
        if (getSessionFactory().getCurrentSession().getTransaction() != null) {
            tr = getSessionFactory().getCurrentSession().beginTransaction();
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        return query.list();
    }

    OfferHistory get(String city, LocalDate date) {
        String hql = "FROM OfferHistory AS oh WHERE oh.city = :city AND oh.date = :date";
        org.hibernate.Transaction tr = null;
        if (getSessionFactory().getCurrentSession().getTransaction() != null) {
            tr = getSessionFactory().getCurrentSession().beginTransaction();
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("city", city);
        query.setParameter("date", date);
        return (OfferHistory) query.uniqueResult();
    }

    void saveOrUpdate(OfferHistory oh) {
        SessionFactory sf = getSessionFactory();
        Session currentSession = sf.getCurrentSession();
        org.hibernate.Transaction tr = null;
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
