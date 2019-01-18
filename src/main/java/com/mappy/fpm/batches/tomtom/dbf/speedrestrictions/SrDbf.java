package com.mappy.fpm.batches.tomtom.dbf.speedrestrictions;

import com.google.common.collect.ArrayListMultimap;
import com.mappy.fpm.batches.tomtom.TomtomFolder;
import com.mappy.fpm.batches.tomtom.dbf.speedrestrictions.SpeedRestriction.Validity;
import com.mappy.fpm.batches.tomtom.helpers.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.jamel.dbf.DbfReader;
import org.jamel.dbf.structure.DbfRow;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

@Slf4j
public class SrDbf {

    private final ArrayListMultimap<Long, SpeedRestriction> speedRestrictionsMap;

    @Inject
    public SrDbf(TomtomFolder folder) {
        speedRestrictionsMap = loadSpeedRestrictions(folder.getFile("sr.dbf"));
    }

    private static ArrayListMultimap<Long, SpeedRestriction> loadSpeedRestrictions(String filename) {
        ArrayListMultimap<Long, SpeedRestriction> restrictions = ArrayListMultimap.create();
        File file = new File(filename);
        if (!file.exists()) {
            log.info("File not found : {}", file.getAbsolutePath());
            return restrictions;
        }
        log.info("Reading SR {}", file);
        try (DbfReader reader = new DbfReader(file)) {
            DbfRow row;
            while ((row = reader.nextRow()) != null) {
                SpeedRestriction restriction = new SpeedRestriction(row.getLong("ID"), row.getInt("SEQNR"), row.getInt("SPEED"), Validity.values()[row.getInt("VALDIR") - 1], VehicleType.fromId(row.getInt("VT")));
                restrictions.put(restriction.getId(), restriction);
            }
        }
        log.info("Loaded {} speed restrictions", restrictions.size());

        return restrictions;
    }

    public List<SpeedRestriction> getSpeedRestrictions(long id) {
        return speedRestrictionsMap.get(id);
    }
}
