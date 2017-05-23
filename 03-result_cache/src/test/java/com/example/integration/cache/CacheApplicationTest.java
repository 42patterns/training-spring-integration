package com.example.integration.cache;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CacheApplicationTest {

    @LocalServerPort
    int localServerPort;
    String phrase = "This%20is%20ok";

    @Test
    public void check_caching() throws IOException {
        HttpResponse firstResult = execute();
        HttpResponse secondResult = execute();

        assertEquals(EntityUtils.toString(firstResult.getEntity()),
                EntityUtils.toString(secondResult.getEntity()));

        assertEquals("immutable", secondResult.getFirstHeader("Cache-Control").getValue());
    }

    private HttpResponse execute() throws IOException {
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet getReq = new HttpGet("http://localhost:" + localServerPort + "/cache/" + phrase);

        return httpclient.execute(getReq);
    }

}
