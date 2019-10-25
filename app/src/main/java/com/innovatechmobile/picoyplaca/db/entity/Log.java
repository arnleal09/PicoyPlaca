package com.innovatechmobile.picoyplaca.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log_db")
public class Log {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "date_log")
    private String dateLog;
    @ColumnInfo(name = "date_consulted")
    private String dateConsulted;
    @ColumnInfo(name = "plate_number")
    private String plate;
    @ColumnInfo(name = "result_value")
    private String result;

    public String getDateLog() {
        return dateLog;
    }

    public void setDateLog(String dateLog) {
        this.dateLog = dateLog;
    }

    public String getDateConsulted() {
        return dateConsulted;
    }

    public void setDateConsulted(String dateConsulted) {
        this.dateConsulted = dateConsulted;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}