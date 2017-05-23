package com.example.integration.loadbalancer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.example.integration.loadbalancer.FileUploadTest.profile;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profile)
public class FileUploadTest {

    final static String profile = "random";
    final static String urlFragment = "gateway/" + profile;

    @LocalServerPort
    int localServerPort;

    @Test
    public void should_upload_file_to_integration_server() throws Exception {

        HttpClient httpclient = HttpClientBuilder.create().build();

        Collection<File> files = Files.list(Paths.get(FileUploadTest.class.getResource("/").toURI()))
                .filter(p -> p.getFileName().toString().startsWith("file"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File file : files) {
            upload(httpclient, file);
        }

    }

    private void upload(HttpClient httpclient, File file) throws IOException {
        HttpPost httppost = new HttpPost("http://localhost:" + localServerPort + "/load-balancer/" + urlFragment);

        HttpEntity reqEntity = new FileEntity(file);
        httppost.setEntity(reqEntity);

        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        if (resEntity != null) {
            String page = EntityUtils.toString(resEntity);
            System.out.println("PAGE :" + page);
        }
    }

}
