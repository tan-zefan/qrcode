package com.example.antifake.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Production {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1024)
    private String info;


    @Column(length = 500)
    private String serialNum;

    @Column(length = 1024)
    private String qrCode;

    @Column
    private int flag = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {return info;}

    public void setInfo(String info) {this.info = info;}

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setFlag(Integer flag){
        this.flag = flag;
    }

    public Integer getFlag(){
        return this.flag;
    }

    public void updateFlag(){
        this.flag++;
    }
}
