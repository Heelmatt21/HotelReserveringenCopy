package sr.unasat.hotelreservering.dao;

import sr.unasat.hotelreservering.Entities.Reserveringen;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.List;

public class ReserveringenDao {
    private EntityManager entityManager;

    public ReserveringenDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Reserveringen> retrieveReserveringenList() {
        entityManager.getTransaction().begin();
        String jpql = "select c from Reserveringen c";
        TypedQuery<Reserveringen> query = entityManager.createQuery(jpql, Reserveringen.class);
        List<Reserveringen> rederveringenList = query.getResultList();
        entityManager.getTransaction().commit();
        return rederveringenList;
    }
    //find by reserveringId
    /*public Reserveringen findByReserveringId(Integer resrveringId) {
        entityManager.getTransaction().begin();
        String jpql = "select c from Reserveringen c  where c.reservering_id = :reservering_id";
        TypedQuery<Reserveringen> query = entityManager.createQuery(jpql, Reserveringen.class);
        Reserveringen reserveringen = query.setParameter("reservering_id", resrveringId).getSingleResult();
        entityManager.getTransaction().commit();
        return reserveringen;
    }*/

    //find by reserveringsnummer
    public Reserveringen findByReserveringsnummer(String reserveringsnummer){
        entityManager.getTransaction().begin();
        String jpql = "select c from Reserveringen c where c.reserveringsnummer= :reserveringsnummer";
        TypedQuery<Reserveringen> query = entityManager.createQuery(jpql, Reserveringen.class);
        Reserveringen reserveringen = query.setParameter("reserveringsnummer", reserveringsnummer).getSingleResult();
        entityManager.getTransaction().commit();
        return reserveringen;
    }

    public Reserveringen insert(Reserveringen reserveringen) {
        entityManager.getTransaction().begin();
        entityManager.persist(reserveringen);
        entityManager.getTransaction().commit();
        return reserveringen;
    }

    public int updateReserveringen(Reserveringen reserveringen) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Reserveringen c SET c.reserveerDatum = :reserveerDatum where c.reserveringsnummer = :reserveringsnummer");
        query.setParameter("reserveringsnummer", reserveringen.getReserveringsnummer());
        query.setParameter("reserveerDatum", reserveringen.getReserveerDatum());
        int rowsUpdated = query.executeUpdate();
        System.out.println("entities Updated: " + rowsUpdated);
        entityManager.getTransaction().commit();
        return rowsUpdated;
    }

    public int delete(String reserveringsNummer) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from Reserveringen c where c.reserveringsnummer = :reserveringsnummer");
        query.setParameter("reserveringsnummer", reserveringsNummer);
        int rowsDeleted = query.executeUpdate();
        System.out.println("entities deleted: " + rowsDeleted);
        entityManager.getTransaction().commit();
        return rowsDeleted;
    }
    //Rapportage
    public List<Reserveringen>getReserveringRapportage(Date startDate, java.util.Date endDate){
        entityManager.getTransaction().begin();
        String jpql = "select c from Reserveringen c where c.reserveerDatum between :startDate and :endDate";
        TypedQuery<Reserveringen> query = entityManager.createQuery(jpql, Reserveringen.class);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate",endDate);

        List<Reserveringen> reserveringenList = query.getResultList();
        System.out.println("=======================================================================================================================");
        System.out.printf("%10s %15s %20s %20s %20s %20s", "reservering_id","reserveer_datum", "reserveringsnummer", "locatie_id","klant_id", "werknemer_id");
        System.out.println();
        System.out.println("=======================================================================================================================");
        for (Reserveringen reserveringen: reserveringenList){
            System.out.format("%5s %20s %20s %20s %20s %20s", reserveringen.getReservering_id(),reserveringen.getReserveerDatum(),reserveringen.getReserveringsnummer(),reserveringen.getLocatieId(),reserveringen.getKlantId(),reserveringen.getWerknemerId());
            System.out.println();
        }
        entityManager.getTransaction().commit();
        System.out.println("======================================================================================================================");
        return reserveringenList;
    }

}
