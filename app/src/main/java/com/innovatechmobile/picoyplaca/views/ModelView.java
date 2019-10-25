package com.innovatechmobile.picoyplaca.views;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovatechmobile.picoyplaca.R;
import com.innovatechmobile.picoyplaca.db.entity.Log;
import com.innovatechmobile.picoyplaca.db.LogRepository;
import com.innovatechmobile.picoyplaca.utils.CurrentDate;
import com.innovatechmobile.picoyplaca.utils.TimeComparator;

import java.util.Calendar;
import java.util.List;

public class ModelView extends AndroidViewModel {

    //days and plates associates
    enum WeekDays {
        DOMINGO(1),
        LUNES(2, '1', '2'),
        MARTES(3, '3', '4'),
        MIERCOLES(4, '5', '6'),
        JUEVES(5, '7', '8'),
        VIERNES(6, '9', '0'),
        SABADO(7);
        private final int day;
        private char plate1;
        private char plate2;

        WeekDays(final int daysGreeting) {
            this.day = daysGreeting;
        }

        WeekDays(final int daysGreeting, final char plate1, final char plate2) {
            this(daysGreeting);
            this.plate1 = plate1;
            this.plate2 = plate2;
        }
    }

    private final MutableLiveData<Integer> statusLabel = new MutableLiveData<>();
    private final LogRepository mRepository;
    private final LiveData<List<Log>> mAllLogs;
    private final LiveData<Integer> mCount;

    public ModelView(@NonNull Application application) {
        super(application);
        mRepository = new LogRepository(application);
        mAllLogs = mRepository.getAllLogs();
        mCount = mRepository.getCount();
    }

    // live data all logs
    LiveData<List<Log>> getAllLogs() {
        return mAllLogs;
    }

    //live data number of coincidences
    LiveData<Integer> getCount() {
        return mCount;
    }

    //live data status of query
    LiveData<Integer> getStatus() {
        return statusLabel;
    }

    //insert a query
    private void insertLog(Log word) {
        mRepository.insert(word);
    }

    //erase all registers
    public void erase() {
        mRepository.erase();
    }

    // compare plate and day time
    void consult(Calendar time, String plate) {
        if (time == null) {
            statusLabel.setValue(R.string.warning_date);
            return;
        }
        int day = time.get(Calendar.DAY_OF_WEEK);
        if (plate == null || plate.isEmpty() || plate.length() < 4) {
            statusLabel.setValue(R.string.warning_plate);
            return;
        }
        char plateDigit = plate.charAt(plate.length() - 1);
        if (!Character.isDigit(plateDigit)) {
            statusLabel.setValue(R.string.warning_plate);
            return;
        }
        Log log = new Log();
        log.setDateConsulted(CurrentDate.formatDate(time.getTime()));
        log.setDateLog(CurrentDate.currentDate());
        log.setPlate(plate);
        log.setResult(getApplication().getApplicationContext().getResources().getString(R.string.query_result));
        if (WeekDays.DOMINGO.day == day || WeekDays.SABADO.day == day) {
            statusLabel.setValue(R.string.circulation);
            log.setResult(getApplication().getApplicationContext().getResources().getString(R.string.circulation));
            insertLog(log);
            return;
        }
        TimeComparator timeComparator = new TimeComparator();
        boolean timeLapse = timeComparator.timeComparator(time);
        for (WeekDays dayReview : WeekDays.values()) {
            if (timeLapse && day == dayReview.day && (plateDigit == dayReview.plate1 || plateDigit == dayReview.plate2)) {
                statusLabel.setValue(R.string.query_result);
                insertLog(log);
                mRepository.count(log);
                return;
            }
        }
        statusLabel.setValue(R.string.circulation);
        log.setResult(getApplication().getApplicationContext().getResources().getString(R.string.circulation));
        insertLog(log);
    }


}
