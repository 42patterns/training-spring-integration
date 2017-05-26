package com.example.integration.protocoladapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spark.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AsyncProtocolAdapterTest {

    @LocalServerPort
    int port;

    @Test
    public void test() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();

        // creating callback handler using spark framework
        Service callback = Service.ignite();
        callback.port(9090);
        callback.post("/callback", (request, response) -> {
            future.complete(request.body());
            response.status(200);
            return "";
        });

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/protocol-adapter/async?artist=King%20Crimson");
        HttpResponse httpResponse = httpClient.execute(httpGet);

        System.out.println("httpResponse = " + httpResponse);
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(302));

        String result = future.get(60, TimeUnit.SECONDS);
        assertThat(result, not(isEmptyOrNullString()));

    }

}