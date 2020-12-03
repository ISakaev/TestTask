package com.isakaev.schedule.dao;

import com.isakaev.schedule.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RecordDaoImpl implements RecordDao{

    private final EntityManager em;

    /**
     * Конструктор OrganizationDaoImpl
     */
    @Autowired
    public RecordDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Получить Record по id
     */
    @Override
    public Record getById(int id) {
        return em.find(Record.class, id);
    }

    /**
     * Сохранить Record
     */
    @Override
    public void save(Record record) {
        em.persist(record);
    }

    /**
     * Удалить Record по id
     */
    @Override
    public void deleteById(int id) {
        Record record = em.find(Record.class, id);
        em.remove(record);
    }

    /**
     * Получить объекты Record по входным данным
     */
    @Override
    public List<Record> list(Record record) {
        String name = record.getName();
        String date = record.getDate();
        String phone = record.getPhone();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Record> criteriaQuery = criteriaBuilder.createQuery(Record.class);
        Root<Record> recordRoot = criteriaQuery.from(Record.class);

        criteriaQuery.select(recordRoot);

        Predicate criteria = criteriaBuilder.conjunction();

        if (name != null || !name.isEmpty()) {
            Predicate predicate = criteriaBuilder.equal(recordRoot.get("name"), name);
            criteria = criteriaBuilder.and(criteria, predicate);
        }

        if (date != null || !date.isEmpty()) {
            Predicate predicate = criteriaBuilder.equal(recordRoot.get("date"), date);
            criteria = criteriaBuilder.and(criteria, predicate);
        }

        if (phone != null || !phone.isEmpty()) {
            Predicate predicate = criteriaBuilder.equal(recordRoot.get("phone"), phone);
            criteria = criteriaBuilder.and(criteria, predicate);
        }

        criteriaQuery.where(criteria);

        return em.createQuery(criteriaQuery).getResultList();
    }
}
