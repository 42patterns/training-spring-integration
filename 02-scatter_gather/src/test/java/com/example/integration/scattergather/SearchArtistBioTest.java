package com.example.integration.scattergather;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SearchArtistBioTest {

    @LocalServerPort
    int localServerPort;

    @Test
    public void test_with_compression_disabled() throws Exception {

        HttpClient httpClient = HttpClientBuilder.create().disableContentCompression().build();

        HttpGet request = new HttpGet("http://localhost:" + localServerPort + "/" +
                "scatter-and-gather/gateway/?artist=Killing+Joke");

        HttpResponse response = httpClient.execute(request);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(response.getEntity().getContent(), outputStream);

        System.out.println("outputStream = " + outputStream.toString());

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

    }

}
