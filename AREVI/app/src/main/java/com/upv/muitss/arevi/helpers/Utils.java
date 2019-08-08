package com.upv.muitss.arevi.helpers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.upv.muitss.arevi.R;

import org.jetbrains.annotations.Contract;

import java.util.regex.Pattern;

public class Utils {
    private static final String TAG = "Utils";

    public static void saveTheme(@NonNull Activity context, String value) {
        UserPreferences userPreferences = UserPreferences.getInstance();
        userPreferences.saveUserPreferenceString(Constants.USER_SELECTED_THEME, value);
        context.recreate();
    }

    public static boolean isDarkTheme(){
        String selectedTheme = getSavedTheme();
        switch (selectedTheme){
            case Constants.APP_DARK_THEME_DEFAULT_FONT_SIZE:
            case Constants.APP_DARK_THEME_MEDIUM_FONT_SIZE:
            case Constants.APP_DARK_THEME_LARGE_FONT_SIZE:
                return true;
            default:
                return false;
        }
    }

    public static String getSavedTheme(){
        UserPreferences userPreferences = UserPreferences.getInstance();
        return userPreferences.getUserPreferenceString(Constants.USER_SELECTED_THEME);
    }

    public static int getSavedThemeStyle() {
        int theme = -1;
        String selectedTheme = getSavedTheme();
        if (selectedTheme == null) return R.style.AppTheme;

        switch (selectedTheme) {
            case Constants.APP_THEME_DEFAULT_FONT_SIZE:
                theme = R.style.AppTheme;
                break;
            case Constants.APP_THEME_MEDIUM_FONT_SIZE:
                theme = R.style.FontSizeMedium;
                break;
            case Constants.APP_THEME_LARGE_FONT_SIZE:
                theme = R.style.FontSizeLarge;
                break;
            case Constants.APP_DARK_THEME_DEFAULT_FONT_SIZE:
                theme = R.style.AppThemeDark;
                break;
            case Constants.APP_DARK_THEME_MEDIUM_FONT_SIZE:
                theme = R.style.FontSizeMediumDark;
                break;
            case Constants.APP_DARK_THEME_LARGE_FONT_SIZE:
                theme = R.style.FontSizeLargeDark;
                break;
        }
        return theme;
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static boolean emailValidation(@NonNull String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return !pattern.matcher(email.trim()).matches();
    }

    public static void validateForm(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof EditText) {
                    validateInput(v);
                } else{
                    validateForm(v);
                }
            }
        }
    }

    @Contract("null, _ -> null")
    public static String validateInput(View view) {
        if (view instanceof EditText){
            EditText editText = (EditText)view;
            String textFromEditView = editText.getText().toString();
            // Reset errors.
            TextInputLayout parent = (TextInputLayout) view.getParent().getParent();
            parent.setError(null);
            if (Utils.isNullOrEmpty(textFromEditView)) {
                parent.setError("Can not be empty");
            }
            else if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) && Utils.emailValidation(textFromEditView)){
                parent.setError("Enter a valid email");
            }
            return textFromEditView;
        }
        return null;
    }
}
