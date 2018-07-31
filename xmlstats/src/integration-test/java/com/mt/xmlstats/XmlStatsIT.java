package com.mt.xmlstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.xmlstats.mapper.StatsMapper;
import com.mt.xmlstats.model.UrlWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@CommonsLog
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, StatsMapper.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XmlStatsIT {

    private static final String ANALYZE_ENDPOINT = "/analyze";
    private static final String VALID_PATH = "http://localhost:8888/web/Posts.xml";
    private static final String INVALID_PATH = "http://localhost:8888/web/totallyNonExistantFile.xml";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldGetXmlStats() throws Exception {
        UrlWrapper urlWrapper = new UrlWrapper();
        urlWrapper.setUrl(VALID_PATH);
        mockMvc.perform(post(ANALYZE_ENDPOINT)
                .content(objectMapper.writeValueAsString(urlWrapper))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.details.totalPosts", is(621)))
                .andExpect(jsonPath("$.details.avgScore", is(2.848631239935588)))
                .andExpect(jsonPath("$.details.firstPost", is("2017-08-02T19:22:36.567")))
                .andExpect(jsonPath("$.details.lastPost", is("2018-03-07T20:21:34.083")));
    }

    @Test
    public void shouldThrowBadRequest() throws Exception {
        UrlWrapper urlWrapper = new UrlWrapper();
        urlWrapper.setUrl(INVALID_PATH);
        mockMvc.perform(post(ANALYZE_ENDPOINT)
                .content(objectMapper.writeValueAsString(urlWrapper))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }
}
