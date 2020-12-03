package com.isakaev.schedule.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

@Entity
@Data
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "phone")
    private String phone;

    public Record() {
    }

    public Record(Integer id, String name, String date, String phone) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.phone = phone;
    }
}
