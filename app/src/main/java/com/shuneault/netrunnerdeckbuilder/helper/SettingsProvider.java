package com.shuneault.netrunnerdeckbuilder.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import com.shuneault.netrunnerdeckbuilder.SettingsActivity;
import com.shuneault.netrunnerdeckbuilder.db.CardRepository;
import com.shuneault.netrunnerdeckbuilder.game.Format;
import com.shuneault.netrunnerdeckbuilder.prefs.ListPreferenceMultiSelect;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsProvider implements ISettingsProvider {
    private Context context;

    public SettingsProvider(Context context) {
        this.context = context;
    }

    @Override
    @NonNull
    public CardRepository.CardRepositoryPreferences getCardRepositoryPreferences() {
        // does this do the actual reading, or can it be in constructor?
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        int mCoreCount = Integer.parseInt(preferences.getString(SettingsActivity.KEY_PREF_AMOUNT_OF_CORE_DECKS, SettingsActivity.DEFAULT_CORE_DECKS));
//        boolean bDisplayAllPacksPref = preferences.getBoolean(SettingsActivity.KEY_PREF_DISPLAY_ALL_DATA_PACKS, true);
//        String packsPref = preferences.getString(SettingsActivity.KEY_PREF_DATA_PACKS_TO_DISPLAY, "");
//        String[] storedValue = ListPreferenceMultiSelect.parseStoredValue(packsPref);
//        ArrayList<String> globalPackFilter = storedValue != null ? new ArrayList<>(Arrays.asList(storedValue)) : new ArrayList<>();

        return new CardRepository.CardRepositoryPreferences(3, false, new ArrayList<>());
    }

    @Override
    public String getLanguagePref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SettingsActivity.KEY_PREF_LANGUAGE, "en");
    }

    @Override
    public int getDefaultFormatId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(SettingsActivity.KEY_PREF_DEFAULT_FORMAT, Format.FORMAT_STANDARD);
    }

}
