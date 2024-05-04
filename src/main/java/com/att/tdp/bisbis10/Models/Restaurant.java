package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private boolean isKosher;

    public Restaurant() {
    }
//    @Column
//    private List<String> cuisines = new ArrayList<>();

    public long getId(){ return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }
    public boolean getIsKosher(){ return this.isKosher; }
    public void setIsKosher(boolean kosher) { this.isKosher = kosher; }

}
