package com.jitin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Hello world!
 */
public class FootballMatch {

    private static final String MATCH_URL = "https://jsonmock.hackerrank.com/api/football_matches";
    public int getTeamGoals(String team, int year) throws IOException {
        String homeMatchesEndpoint = String.format(MATCH_URL+"?year=%d&team1=%s",year, URLEncoder.encode(team, "UTF-8"));
        String awayMatchesEndpoint = String.format(MATCH_URL+"?year=%d&team2=%s",year, URLEncoder.encode(team, "UTF-8"));

        int homeMatchGoals = getResult(homeMatchesEndpoint, "team1", 1, 0);
        int awayMatchGoals = getResult(awayMatchesEndpoint, "team2", 1, 0);

        return homeMatchGoals+ awayMatchGoals;
    }

    private int getResult(String endPoint, String teamType, int page, int totalGoals) throws IOException {

        String response = getResponsePerPage(endPoint, page);
        JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
        int totalPages = jsonResponse.get("total_pages").getAsInt();
        JsonArray data = jsonResponse.getAsJsonArray("data");

        for (JsonElement e: data) {
            totalGoals += e.getAsJsonObject().get(teamType+"goals").getAsInt();
        }
        return totalPages == page? totalGoals: getResult(endPoint, teamType, page+1, totalGoals);
    }

    public String getResponsePerPage(String endPoint, int page) throws IOException {

        URL url = new URL(endPoint+"&page="+page);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Content-Type", "application/json");

        int status = connection.getResponseCode();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response;
        StringBuilder stringBuilder = new StringBuilder();

        while ((response = bufferedReader.readLine()) != null) {
            stringBuilder.append(response);
        }

        bufferedReader.close();
        connection.disconnect();
        return stringBuilder.toString();
    }
}
