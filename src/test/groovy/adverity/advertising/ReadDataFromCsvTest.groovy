package adverity.advertising

import spock.lang.Specification

class ReadDataFromCsvTest extends Specification {
    def "should read from CSV"() {
        given:
        final file = File.createTempFile("test", ".tmp")
        file.write(csv)
        final reader = new CsvReader(file)
        when:
        final advertisingData = reader.parse()

        then:
        advertisingData.size == 3

        where:
        csv << ["Date,Datasource,Campaign,Clicks,Impressions\n01.01.2019,Facebook Ads,Like Ads,274,1979\n01.01.2019,Facebook Ads,Offer Campaigns - Conversions,10245,764627"]
    }
}
