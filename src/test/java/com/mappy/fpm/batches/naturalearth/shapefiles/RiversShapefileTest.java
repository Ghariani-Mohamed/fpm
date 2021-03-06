package com.mappy.fpm.batches.naturalearth.shapefiles;

import com.mappy.fpm.utils.MemoryGeometrySerializer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class RiversShapefileTest {
    private final MemoryGeometrySerializer serializer = new MemoryGeometrySerializer();
    private final RiversShapefile shp = new RiversShapefile(getClass().getResource("/naturalearth").getPath());

    @Test
    public void should_serialize_rivers() {
        shp.serialize(serializer);

        assertThat(serializer.getMultilinestrings()).extracting(m -> "waterway".equals(m.get("stream"))).hasSize(10);
    }
}