package com.innovatechmobile.picoyplaca.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.innovatechmobile.picoyplaca.R;
import com.innovatechmobile.picoyplaca.adapters.AdapterLog;
import com.innovatechmobile.picoyplaca.databinding.ActivityMainBinding;
import com.innovatechmobile.picoyplaca.db.entity.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterLog.OnItemClickListener {

    private long time;
    private ActivityMainBinding binding;
    private Calendar calendar;
    private ModelView model;
    private AdapterLog adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = ViewModelProviders.of(this).get(ModelView.class);
        adapter = new AdapterLog();
        binding.rVLog.setAdapter(adapter);
        binding.rVLog.setLayoutManager(new LinearLayoutManager(this));
        model.getAllLogs().observe(this, this::refresh);
        model.getStatus().observe(this, this::errorStatus);
        model.getCount().observe(this, this::showDialog);
        binding.eTPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tVmssg.setVisibility(View.GONE);
            }
        });
    }

    private void refresh(List<Log> logs) {
        adapter.setWords(logs);
    }

    //refresh result
    private void errorStatus(int status) {
        binding.tVmssg.setText(status);
        binding.tVmssg.setVisibility(View.VISIBLE);
    }

    //select time
    public void selectTime(View view) {
        view.setEnabled(false);
        binding.tVmssg.setVisibility(View.GONE);
        final View dialogView = View.inflate(this, R.layout.dialog_date_time, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        dialogView.findViewById(R.id.date_time_set).setOnClickListener(view1 -> {

            DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = dialogView.findViewById(R.id.time_picker);

            calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute());
            time = calendar.getTimeInMillis();
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(time);
            String formattedTime = new SimpleDateFormat("HH:mm").format(time);
            binding.tVScheduleDate.setText(formattedDate);
            binding.tVScheduleTime.setText(formattedTime);
            alertDialog.dismiss();
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
        view.setEnabled(true);
    }

    // launch query
    public void consult(View view) {
        view.setEnabled(false);
        model.consult(calendar, binding.eTPlate.getText().toString());
        view.setEnabled(true);
    }

    // show number of coincidences
    private void showDialog(Integer number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.dialog_message, number))
                .setTitle(R.string.dialog_title);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // ask for permission to erase
    public void erase(View view) {
        view.setEnabled(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_message_erase))
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, (dialog, id) -> model.erase());
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        view.setEnabled(true);
    }

    // show a given message
    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(R.string.dialog_title);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void info(View view) {
        showDialog(getResources().getString(R.string.info));
    }
}
