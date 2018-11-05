package com.mappy.fpm.batches.tomtom.dbf.timedomains;

import com.mappy.fpm.batches.tomtom.TomtomFolder;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeDomainsProviderTest {

    private final TomtomFolder tomtomFolder = mock(TomtomFolder.class);

    private TimeDomainsProvider timeDomainsProvider;

    @Before
    public void setup() {
        when(tomtomFolder.getFile("td.dbf")).thenReturn("src/test/resources/tomtom/road/td.dbf");
        timeDomainsProvider = new TimeDomainsProvider(tomtomFolder);
    }

    @Test
    public void should_read_time_domain_restriction() {
        assertThat(timeDomainsProvider.getTimeDomains(14420000000590L)).containsExactly(
                new TimeDomains(14420000000590L, 2, "[(h11){h7}]"),
                new TimeDomains(14420000000590L, 3, "[(h22){h8}]"));
    }
}
