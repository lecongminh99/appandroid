package com.example.getwifiscan.util;

import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.os.Bundle;


import com.example.getwifiscan.model.WifiObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import org.threeten.bp.LocalDateTime;

public class WifiHelper {
    private static final String DISTINCT_PATTERN = "%s@%s";

    public static String createDistinctKey(WifiObjectCustom wifiObjectCustom) {
        if (null == wifiObjectCustom) return null;
        return String.format(DISTINCT_PATTERN, wifiObjectCustom.getBssid());
    }

//    public static void saveToPref(List<WifiObjectCustom> wifiObjectCustomList,
//                                  SharedPreferences preferences) {
//        if (null == wifiObjectCustomList || wifiObjectCustomList.isEmpty()) return;
//        JSONArray array = new JSONArray();
//        wifiObjectCustomList.forEach(o -> array.put(o.toJson()));
//        preferences.edit().putString(PreferenceKeyConst.KEY_BSSID_LIST, array.toString()).apply();
//    }

    public static void saveDataToFile(File file, int totalNum, List<ScanResult> data,
                                      SharedPreferences preferences)
            throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        StringBuilder textFilter = new StringBuilder();
        int currentAt = preferences.getInt(PreferenceKeyConst.KEY_NO_CURRENT_AT, 1);

        String content = "[{\"bssid\":\"74:83:c2:c2:b5:67\",\"indexAt\":\"0\"} , {\"bssid\":\"5c:1a:6f:bd:cf:87\",\"indexAt\":\"1\"}, {\"bssid\":\"c0:b5:d7:c9:5e:71\",\"indexAt\":\"2\"},{\"bssid\":\"c0:b1:01:d3:32:38\",\"indexAt\":\"3\"},{\"bssid\":\"a8:25:eb:8a:56:dc\",\"indexAt\":\"4\"},{\"bssid\":\"5c:3a:3d:86:0a:6d\",\"indexAt\":\"5\"},{\"bssid\":\"00:ad:24:65:27:7a\",\"indexAt\":\"6\"},{\"bssid\":\"18:7c:0b:2a:94:9c\",\"indexAt\":\"7\"}]";
        //String content = "[{\"bssid\":\"00:22:6b:e6:73:e4\",\"indexAt\":\"0\"} , {\"bssid\":\"ec:f0:fe:85:75:d6\",\"indexAt\":\"1\"}, {\"bssid\":\"00:23:cd:d9:19:96\",\"indexAt\":\"2\"},{\"bssid\":\"e0:19:54:f0:37:43\",\"indexAt\":\"3\"},{\"bssid\":\"a8:25:eb:5f:99:e2\",\"indexAt\":\"4\"},{\"bssid\":\"68:ff:7b:d8:17:6b\",\"indexAt\":\"5\"},{\"bssid\":\"e0:19:54:f0:37:45\",\"indexAt\":\"6\"},{\"bssid\":\"00:d0:cb:fc:47:d3\",\"indexAt\":\"7\"}]";


        JSONObject jsonObject;
        WifiObjectCustom wifiObjectCustom;
        List<WifiObjectCustom> objectCustoms = new ArrayList<>();
        //save cho lan 2
        if (!content.isEmpty()) {
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); ++i) {
                jsonObject = jsonArray.optJSONObject(i);
                if (null == jsonObject) {
                    continue;
                }
                wifiObjectCustom = WifiObjectCustom.convertFrom(jsonObject);
                objectCustoms.add(wifiObjectCustom);
            }
            for (int j = 0; j < objectCustoms.size(); j++) {
                int listSize = data.size();
                for (int x = 0; x < listSize; x++) {
                    if (objectCustoms.get(j).getBssid().equals(data.get(x).BSSID)) {
                        //step 1:add vao data tra ve
                        textFilter.append(data.get(x).level);
                        if (j == objectCustoms.size() - 1) {
                            textFilter.append(GetWiFConfig.COMMA);
                        } else {
                            textFilter.append(GetWiFConfig.COMMA);
                        }
                        //step 2: remove element ra khoi list
                        data.remove(x);
                        break;
                    }
                    if (x == listSize - 1) {
                        textFilter.append("-127").append(GetWiFConfig.COMMA);
                    }
                }
            }
             int a = 1;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(PreferenceKeyConst.KEY_NO_CURRENT_AT, currentAt + 1);
            editor.putString(PreferenceKeyConst.TEMP_DATA, String.valueOf(textFilter));
            editor.apply();
            sb.append(textFilter);
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(sb.append(GetWiFConfig.NEW_LINE).toString().getBytes());
            fos.close();
        }
    }
}


