package com.master.navdrawerbottomnva;

import android.app.Application;

public class UserSettings extends Application {

    public static final String PREFERANCES = "preferences";
    public static final String CUSTOME_THEME = "customeTheme";
    public static final String LIGHT_THEME = "light_Theme";
    public static final String DARKTHEME = "dark_theme";

    private String customeTheme;

    public String getCustomeTheme() {
        return customeTheme;
    }

    public void setCustomeTheme(String customeTheme) {
        this.customeTheme = customeTheme;
    }
}
