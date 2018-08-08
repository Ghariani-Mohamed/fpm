package com.mappy.fpm.batches.tomtom.download.json.downloader;

import com.mappy.fpm.batches.tomtom.download.json.model.Families.Family;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.FileEntity;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FamiliesDownloaderTest {

    private FamiliesDownloader familiesDownloader;

    private final HttpClient client = mock(HttpClient.class);

    @Before
    public void setUp() throws Exception {

        HttpResponse familyResponse = mock(HttpResponse.class);
        when(familyResponse.getEntity()).thenReturn(new FileEntity(new File(getClass().getResource("/tomtom/download/json/families.json").toURI())));

        when(client.execute(any(HttpGet.class))).thenReturn(familyResponse);

        familiesDownloader = new FamiliesDownloader(client, "validToken");
    }

    @Test
    public void should_only_download_useful_families() {

        Stream<Family> families = familiesDownloader.get();

        assertThat(families).containsOnly(//
                //new Family("MNAP", "https://api.test/families/200"),//
                //new Family("MN", "https://api.test/families/300"), //
                //new Family("SP", "https://api.test/families/400"),//
                new Family("2DCM", "https://api.test/families/500")//
                //new Family("MNPOI", "https://api.test/families/600")
                );
    }
}