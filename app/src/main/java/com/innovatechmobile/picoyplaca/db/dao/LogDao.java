package com.innovatechmobile.picoyplaca.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.innovatechmobile.picoyplaca.db.entity.Log;

import java.util.List;

@Dao
public interface LogDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Log log);

    //allow erase registers
    @Query("DELETE FROM log_db")
    void delete();

    //return register order by id
    @Query("SELECT * from log_db ORDER BY id ASC")
    LiveData<List<Log>> getLogs();

    //return number of coincidences for a plate and result
    @Query("SELECT COUNT(*) FROM log_db WHERE plate_number = :plateValue AND result_value= :resultValue")
    Integer count(String plateValue, String resultValue);

}