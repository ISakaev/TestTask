package com.isakaev.schedule.dao;

import com.isakaev.schedule.model.Record;

import java.util.List;

/**
 * DAO для работы с record
 */
public interface RecordDao {

    /**
     * Получение объекта Record по первичному ключу.
     */
    Record getById(int id);

    /**
     * Сохранение новой record
     */
    void save(Record record);

    /**
     * Удаление record по id.
     */
    void deleteById(int id);

    /**
     * Получение records
     */
    List<Record> list(Record record);
}
