package com.example.integration.pipefilters;

import com.example.integration.pipefilters.model.SearchResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchProductTest {

	final static Path rootFolder = Paths.get(System.getProperty("java.io.tmpdir")).resolve("pipefilter");

	@BeforeClass
	public static void cleanup() throws IOException {
		if (!Files.exists(rootFolder)) Files.createDirectory(rootFolder);
		Files.walkFileTree(rootFolder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@Test
	public void processAGDsearch() throws Exception {

		final CompletableFuture<File> future = new CompletableFuture<>();

		FileAlterationObserver observer = new FileAlterationObserver(rootFolder.resolve("results").toFile());
		observer.addListener(new FileAlterationListenerAdaptor() {
			@Override
			public void onFileCreate(File file) {
				future.complete(file);
			}
		});

		FileAlterationMonitor monitor = new FileAlterationMonitor(100);
		monitor.addObserver(observer);
		monitor.start();

		Files.copy(SearchProductTest.class.getResourceAsStream("/agd-search-request.json"),
				rootFolder.resolve("queries").resolve("agd-search-request.json"),
				StandardCopyOption.REPLACE_EXISTING);

		File file = future.get(5, TimeUnit.MINUTES);

		assertEquals("agd-search-request.json", file.getName());
		SearchResults results = new ObjectMapper().readerFor(SearchResults.class).readValue(file);
		assertEquals("telewizor Samsung", results.getFound().get(0).getName());
	}

}
