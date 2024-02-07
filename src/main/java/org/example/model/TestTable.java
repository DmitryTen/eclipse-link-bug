package org.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class TestTable extends AEntity {

    @Column
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
