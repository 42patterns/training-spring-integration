package com.example.integration.protocoladapter.services;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;

public class UnGzipIt extends AbstractTransformer  {

    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        byte[] bytes = (byte[]) message.getPayload();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream gzIn = new GZIPInputStream(new ByteArrayInputStream(bytes));
        FileCopyUtils.copy(gzIn, out);
        return out.toString();
    }
}