package com.advertising.infrastructure


import com.advertising.domain.AdvertisingData

import static com.xlson.groovycsv.CsvParser.parseCsv

class CsvReader {
    private final File file

    CsvReader(File file) {
        this.file = file
    }

    Collection<AdvertisingData> parse() {
        def data = []
        parseCsv(new FileReader(file), separator: ',').forEachRemaining { data.add(convertLine(it)) }
        return data;
    }

    private AdvertisingData convertLine(def entry) {
        return new AdvertisingData(date: entry.Date,
                                   datasource: entry.Datasource,
                                   campaign: entry.Campaign,
                                   clicks: (entry.Clicks ?: "0").toInteger(),
                                   impressions: (entry.Impressions ?: "0").toInteger())
    }
}
