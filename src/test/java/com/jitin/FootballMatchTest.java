package com.jitin;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class FootballMatchTest {

    /**
     * Rigorous Test :-)
     */
    private static final String MATCH_URL = "https://jsonmock.hackerrank.com/api/football_matches?year=2011&team1=Chelsea";
    @Test
    public void shouldGetTeamGoals() throws IOException {
        FootballMatch sut = new FootballMatch();
        assertEquals(93,sut.getTeamGoals("Chelsea",2011));

    }

    @Test
    public void shouldReturnResult() throws IOException, JSONException {
        FootballMatch sut = new FootballMatch();
        String responsePerPage = sut.getResponsePerPage(MATCH_URL, 1);
        assertNotEquals(null, responsePerPage);
        JSONAssert.assertEquals("{ page: 1}", responsePerPage, JSONCompareMode.LENIENT);
    }
}
