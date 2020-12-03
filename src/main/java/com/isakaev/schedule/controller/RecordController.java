package com.isakaev.schedule.controller;

import com.isakaev.schedule.exception.ErrorException;
import com.isakaev.schedule.model.Record;
import com.isakaev.schedule.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    
    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService){
        this.recordService = recordService;
    }

    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable int id){
        Record record = new Record();
        if(record == null){
            throw new ErrorException("The record was not found");
        }
        return recordService.getById(id);
    }

    @GetMapping("/")
    public List<Record> getListRecords(@RequestBody Record record){

        return recordService.getAll(record);

    }

    @PostMapping(value = "/")
    public void saveRecord (@RequestBody Record record){

        if((Integer)record.getId() == null || record.getName() == null ||
                record.getDate() == null || record.getPhone() == null){

            throw new ErrorException("Insufficient information about the record");
        }
        // Проверка дня недели, запись только в будни
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = null;
        try{
            date = dateFormat.parse(record.getDate());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < 2 || dayOfWeek > 7){        // График для выходных
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            if (hourOfDay < 8 || hourOfDay > 20){
                throw new ErrorException("The record on a nonworking time is not possible");
            }
        }else if(dayOfWeek > 1 && dayOfWeek < 8){   // График для будней
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            if (hourOfDay < 10 || hourOfDay > 17){
                throw new ErrorException("The record on a nonworking time is not possible");
            }
        }else {
            throw new ErrorException("The record on a day off is not possible");
        }
        record.setDate(dateFormat.format(date));

        //Проверка количества записей (не более 10)
        Record checkRecord = new Record();
        checkRecord.setDate(record.getDate());

        if (recordService.getAll(checkRecord).size() > 9){
            throw new ErrorException("There are no available entries for this date and time");
        }

        //Проверка количества записей в день на человека(не более 1)
        int dayOfRecord = c.get(Calendar.DAY_OF_MONTH);
        int monthOfRecord = c.get(Calendar.MONTH);
        int yearOfRecord = c.get(Calendar.YEAR);
        Record recordOfDay = new Record();
        recordOfDay.setName(record.getName());
        List<Record> itemRecordOfDay = recordService.getAll(recordOfDay);
        for (Record item: itemRecordOfDay) {
            Date dateOfItem = null;
            try{
                dateOfItem = dateFormat.parse(item.getDate());
            } catch (Exception ex){
                ex.printStackTrace();
            }
            c.setTime(dateOfItem);
            if (c.get(Calendar.DAY_OF_MONTH) == dayOfRecord &&
                    c.get(Calendar.MONTH) == monthOfRecord &&
                    c.get(Calendar.YEAR) == yearOfRecord){
                throw new ErrorException("There are no available entries for this date more than 1 time");
            }

        }

        this.recordService.save(record);

    }

    @DeleteMapping(value = "/{id}")
    public void deleteRecord(@PathVariable("id") int id){
        Record record = this.recordService.getById(id);
        if (record == null){
            throw new ErrorException("The record was not found");
        }

        this.recordService.delete(id);

    }
}
