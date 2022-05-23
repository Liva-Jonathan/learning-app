package com.example.learning.fragment;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.learning.R;
import com.example.learning.controller.MainActivity;
import com.example.learning.utils.ReminderBroadcast;

import java.util.Calendar;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SharedPreferences sharedPreferences;
    private boolean sound = true;
    private boolean reminder = true;
    private int hourReminder = 8;
    private int minuteReminder = 0;

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        fetchPreferences();

        handleSound();
        handleReminder();
        handleHourReminder();
    }

    public void fetchPreferences() {
        sound = sharedPreferences.getBoolean("sound", true);
        reminder = sharedPreferences.getBoolean("reminder", true);
        hourReminder = sharedPreferences.getInt("hourReminder", 8);
        minuteReminder = sharedPreferences.getInt("minuteReminder", 0);
    }

    public void handleSound() {
        SwitchPreference pref = (SwitchPreference) findPreference("sound_preference");
        pref.setChecked(sound);
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                sound = (boolean) newValue;
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putBoolean("sound", sound);
                prefsEditor.commit();
                return true;
            }
        });
    }

    public void handleReminder() {
        SwitchPreference pref = (SwitchPreference) findPreference("reminder_preference");
        pref.setChecked(reminder);
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                reminder = (boolean) newValue;
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putBoolean("reminder", reminder);
                prefsEditor.commit();

                if(reminder) {
                    setupNotification(getContext());
                }

                return true;
            }
        });
    }

    public void handleHourReminder() {
        Preference pref = (Preference) findPreference("reminderHour_preference");
        pref.setSummary(formatTime(hourReminder) + ":" + formatTime(minuteReminder));
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        SettingsFragment.this.hourReminder = selectedHour;
                        SettingsFragment.this.minuteReminder = selectedMinute;
                        pref.setSummary(formatTime(selectedHour) + ":" + formatTime(selectedMinute));
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        prefsEditor.putInt("hourReminder", selectedHour);
                        prefsEditor.putInt("minuteReminder", selectedMinute);
                        prefsEditor.commit();

                        if(reminder) {
                            setupNotification(getContext());
                        }
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hourReminder, minuteReminder, true);

                timePickerDialog.setTitle("Selectionner votre heure de rappel");
                timePickerDialog.show();

                return true;
            }
        });
    }

    public void setupNotification(Context context) {
        setupNotification(context, hourReminder, minuteReminder);
    }

    public static void setupNotification(Context context, int hour, int minute) {
        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private String formatTime(int time) {
        if(time < 10) return "0" + time;
        return ""+time;
    }

    public boolean getSound() {
        return sound;
    }

    public boolean getReminder() {
        return reminder;
    }

}