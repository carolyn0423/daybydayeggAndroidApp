package com.hamels.daybydayegg.Utils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomBottomNavigationView extends BottomNavigationView {

    public CustomBottomNavigationView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Set OnNavigationItemSelectedListener
        setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle item selection here if needed
                return true;
            }
        });

        // Set ScaleType for each icon in the menu
        Menu menu = getMenu();
        for (int i = 0; i < menu.size(); i++) {
            setIconScaleType(menu.getItem(i));
        }
    }

    public void setIconScaleType(MenuItem menuItem) {
        ImageView iconView = findIconViewForMenuItem(menuItem);
        if (iconView != null) {
            iconView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private ImageView findIconViewForMenuItem(MenuItem item) {
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View child = getChildAt(i);
            if (child instanceof Menu) {
                Menu menu = (Menu) child;
                for (int j = 0; j < menu.size(); j++) {
                    MenuItem menuItem = menu.getItem(j);
                    if (menuItem.getItemId() == item.getItemId()) {
                        return (ImageView) menuItem.getActionView().findViewById(com.google.android.material.R.id.icon);
                    }
                }
            }
        }
        return null;
    }
}