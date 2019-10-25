package com.innovatechmobile.picoyplaca.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovatechmobile.picoyplaca.db.dao.LogDao;
import com.innovatechmobile.picoyplaca.db.entity.Log;

import java.util.List;

public class LogRepository {

    private final LogDao logDao;
    private final LiveData<List<Log>> listLiveData;
    private static final MutableLiveData<Integer> failCount = new MutableLiveData<>();

    public LogRepository(Application application) {
        LogDataBase db = LogDataBase.getDatabase(application);
        logDao = db.logDao();
        listLiveData = logDao.getLogs();
    }

    public LiveData<List<Log>> getAllLogs() {
        return listLiveData;
    }

    public LiveData<Integer> getCount() {
        return failCount;
    }

    public void insert(Log word) {
        new InsertAsyncTask(logDao).execute(word);
    }

    public void count(Log log) {
        new CountAsyncTask(logDao).execute(log);
    }

    public void erase() {
        new EraseAsyncTask(logDao).execute();
    }


    private static class EraseAsyncTask extends AsyncTask<Void, Void, Void> {

        private final LogDao mAsyncTaskDao;

        EraseAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.delete();
            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Log, Void, Void> {

        private final LogDao mAsyncTaskDao;

        InsertAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Log... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class CountAsyncTask extends AsyncTask<Log, Void, Integer> {

        private final LogDao mAsyncTaskDao;

        CountAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Log... params) {
            return mAsyncTaskDao.count(params[0].getPlate(), params[0].getResult());
        }

        protected void onPostExecute(Integer result) {
            failCount.setValue(result);
        }
    }
}