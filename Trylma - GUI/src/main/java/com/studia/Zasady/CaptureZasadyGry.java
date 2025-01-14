package com.studia.Zasady;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class CaptureZasadyGry extends ZasadyGry {

    public CaptureZasadyGry(int liczbaGraczy){
        super(liczbaGraczy);
    }
    @Override
    public String toString() {
        return "Capture";
    }
    

    public JSONArray infoJSON() {
        JSONArray mapa = new JSONArray();
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/config.json")));
            JSONObject jsonObject = new JSONObject(content);
            mapa = jsonObject.getJSONArray("capture");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }
}
