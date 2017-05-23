package com.example.integration.scattergather.tools;

import org.apache.commons.io.IOUtils;
import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class ByteArrayToString implements Deserializer<String> {

	@Override
	public String deserialize(InputStream inputStream) throws IOException {

		return IOUtils.toString(new GZIPInputStream(inputStream));

	}

}
