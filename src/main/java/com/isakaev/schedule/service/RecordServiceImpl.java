package com.isakaev.schedule.service;

import com.isakaev.schedule.dao.RecordDao;
import com.isakaev.schedule.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordDao recordDao;

    @Autowired
    public RecordServiceImpl(RecordDao recordDao){
        this.recordDao = recordDao;
    }

    @Override
    @Transactional
    public Record getById(int id) {
        Record record = recordDao.getById(id);
        return new Record(record.getId(),
                record.getName(),
                record.getDate(),
                record.getPhone());
    }

    @Override
    @Transactional
    public void save(Record record) {
        Record recordNew = new Record(record.getId(),
                record.getName(),
                record.getDate(),
                record.getPhone());
        recordDao.save(recordNew);
    }

    @Override
    @Transactional
    public void delete(int id) {
        recordDao.deleteById(id);
    }

    @Override
    @Transactional
    public List<Record> getAll(Record record) {
        List<Record> recordList = recordDao.list(record);
        return recordList;
    }
}
