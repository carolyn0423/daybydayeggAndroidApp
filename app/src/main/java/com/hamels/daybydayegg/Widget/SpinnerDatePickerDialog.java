package com.hamels.daybydayegg.Widget;

import android.app.DatePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.DatePicker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class SpinnerDatePickerDialog extends DatePickerDialog {
    public SpinnerDatePickerDialog(@NonNull Context context, int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
        fixSpinner(context, year, monthOfYear, dayOfMonth);
    }

    private void fixSpinner(Context context, int year, int month, int dayOfMonth) {
        // The spinner vs not distinction probably started in lollipop but applying this
        // for versions < nougat leads to a crash trying to get DatePickerSpinnerDelegate
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            try {
                // Get the theme's android:datePickerMode
                final int MODE_SPINNER = 2;
                Class<?> styleableClass = Class.forName("com.android.internal.R$styleable");
                Field datePickerStyleableField = styleableClass.getField("DatePicker");
                int[] datePickerStyleable = (int[]) datePickerStyleableField.get(null);
                final TypedArray a = context.obtainStyledAttributes(null, datePickerStyleable,
                        android.R.attr.datePickerStyle, 0);
                Field datePickerModeStyleableField = styleableClass.getField("DatePicker_datePickerMode");
                int datePickerModeStyleable = datePickerModeStyleableField.getInt(null);
                final int mode = a.getInt(datePickerModeStyleable, MODE_SPINNER);
                a.recycle();

                if (mode == MODE_SPINNER) {
                    DatePicker datePicker = (DatePicker) findField(DatePickerDialog.class,
                            DatePicker.class, "mDatePicker").get(this);
                    Class<?> delegateClass = Class.forName("android.widget.DatePicker$DatePickerDelegate");
                    Field delegateField = findField(DatePicker.class, delegateClass, "mDelegate");
                    Object delegate = delegateField.get(datePicker);

                    Class<?> spinnerDelegateClass = Class.forName("android.widget.DatePickerSpinnerDelegate");

                    // In 7.0 Nougat for some reason the datePickerMode is ignored and the
                    // delegate is DatePickerCalendarDelegate
                    if (delegate.getClass() != spinnerDelegateClass) {
                        delegateField.set(datePicker, null); // throw out the DatePickerCalendarDelegate!
                        datePicker.removeAllViews(); // remove the DatePickerCalendarDelegate views

                        Constructor spinnerDelegateConstructor = spinnerDelegateClass
                                .getDeclaredConstructor(DatePicker.class, Context.class,
                                        AttributeSet.class, int.class, int.class);
                        spinnerDelegateConstructor.setAccessible(true);

                        // Instantiate a DatePickerSpinnerDelegate
                        delegate = spinnerDelegateConstructor.newInstance(datePicker, context,
                                null, android.R.attr.datePickerStyle, 0);

                        // set the DatePicker.mDelegate to the spinner delegate
                        delegateField.set(datePicker, delegate);

                        // Set up the DatePicker again, with the DatePickerSpinnerDelegate
                        datePicker.updateDate(year, month, dayOfMonth);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Field findField(Class objectClass, Class fieldClass, String expectedName) {
        try {
            Field field = objectClass.getDeclaredField(expectedName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
        }

        for (Field searchField : objectClass.getDeclaredFields()) {
            if (searchField.getType() == fieldClass) {
                searchField.setAccessible(true);
                return searchField;
            }
        }
        return null;
    }
}