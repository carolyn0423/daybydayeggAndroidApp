package com.hamels.daybydayegg.Utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ViewUtils {
    public static void addUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }

    public static void colorizeDatePicker(DatePicker datePicker) {
        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("day", "id", "android");
        int monthId = system.getIdentifier("month", "id", "android");
        int yearId = system.getIdentifier("year", "id", "android");

        NumberPicker dayPicker = datePicker.findViewById(dayId);
        NumberPicker monthPicker = datePicker.findViewById(monthId);
        NumberPicker yearPicker = datePicker.findViewById(yearId);

        setDividerColor(dayPicker);
        setDividerColor(monthPicker);
        setDividerColor(yearPicker);
    }

    private static void setDividerColor(NumberPicker picker) {
        if (picker == null) return;

        // ✅ API 34+ 禁止反射，避免崩潰
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            // Android 15+（API 34 以上）不能改 divider，直接 return
            Log.w("setDividerColor", "Skipping divider color change on Android 15+");
            return;
        }

        try {
//            Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
//            dividerField.setAccessible(true);
//            dividerField.set(picker, new ColorDrawable(Color.BLACK));
            picker.invalidate();
        } catch (Exception e) {
            Log.w("setDividerColor", "Failed to set divider color", e);
        }
    }

}
