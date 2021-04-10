package com.example.getwifiscan.model;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WifiObjectCustom {
    private static final String BSSID = "bssid";
    private static final String INDEX_AT = "indexAt";
    public String bssid;
    public int indexAt;

    public WifiObjectCustom(String bssid, int indexAt) {
        this.bssid = bssid;
        this.indexAt = indexAt;
    }

    @Nullable
    public static WifiObjectCustom convertFrom(JSONObject jsonObject) {
        if (null == jsonObject) return null;
        WifiObjectCustom wifiObjectCustom = new WifiObjectCustom(
                jsonObject.optString(BSSID),
                jsonObject.optInt(INDEX_AT));
        return wifiObjectCustom;
    }

    @Nullable
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(BSSID, this.bssid);
            jsonObject.put(INDEX_AT, this.indexAt);
        } catch (JSONException e) {
            return null;
        }
        return jsonObject;
    }
}
