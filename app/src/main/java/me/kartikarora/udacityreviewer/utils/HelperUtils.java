package me.kartikarora.udacityreviewer.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import java.util.Date;
import java.util.TimeZone;

import chipset.potato.Potato;
import me.kartikarora.udacityreviewer.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.utils
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class HelperUtils {

    private static HelperUtils helperUtils;

    public static HelperUtils getInstance() {
        if (helperUtils == null) {
            helperUtils = new HelperUtils();
        }
        return helperUtils;
    }

    private HelperUtils() {
    }

    public void copyToClipboard(Context context, String message, String what) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(message, message);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, what + " copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public String priceWithCommas(String price) {
        return price.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
    }

    public ArrayMap<String, String> getHeaders(Context context) {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("Authorization", Potato.potate(context).Preferences().getSharedPreferenceString(context.getString(R.string.pref_udacity_token)));
        map.put("Accept", "application/json");
        return map;
    }

    public String capitalize(String original) {
        return (original == null || original.length() == 0 || original.isEmpty()) ? original :
                original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public Date utcToDefault(Date utcDate, Date localDate) {
        return new Date(utcDate.getTime() + TimeZone.getDefault().getOffset(localDate.getTime()));
    }
}
