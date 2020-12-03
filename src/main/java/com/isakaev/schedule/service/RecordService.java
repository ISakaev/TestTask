package com.isakaev.schedule.service;

import com.isakaev.schedule.model.Record;

import java.util.List;

/**
 * Сервис для работы с Record
 */
public interface RecordService {

    /**
     * Получить Record по идентификатору
     */
    Record getById (int id);

    /**
     * Сохранить Record
     */
    void save (Record record);

    /**
     * Удалить Record
     */
    void delete (int id);

    /**
     * Полусить объекты Record
     */
    List<Record> getAll(Record record);
}
