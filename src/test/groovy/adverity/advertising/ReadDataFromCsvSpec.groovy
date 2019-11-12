package adverity.advertising

import adverity.advertising.domain.AdvertisingData
import adverity.advertising.infrastructure.CsvReader
import spock.lang.Specification

class ReadDataFromCsvSpec extends Specification {
    final static file = File.createTempFile("test", ".tmp")

    def setupSpec() {
        final csv = "Date,Datasource,Campaign,Clicks,Impressions\n01.01.2019,Facebook Ads,Like Ads,274,1979\n01.01.2019,Facebook Ads,Offer Campaigns - Conversions,10245,764627"
        file.write(csv)
    }

    def cleanupSpec() {
        file.delete();
    }

    def "should read from CSV"() {
        given:
        final reader = new CsvReader(file)
        when:
        final advertisingData = reader.parse()
        then:
        advertisingData.size == 2
    }

    def "should skip header"() {
        given:
        final reader = new CsvReader(file)
        when:
        final advertisingData = reader.parse()
        then:
        !advertisingData.contains("Date,Datasource,Campaign,Clicks,Impressions")
    }

    def "should parse data"() {
        given:
        final reader = new CsvReader(file)
        when:
        final advertisingData = reader.parse()
        then:
        advertisingData == [
                new AdvertisingData(date: "01.01.2019", datasource: "Facebook Ads", campaign: "Like Ads", clicks: 274, impressions: 1979),
                new AdvertisingData(date: "01.01.2019", datasource: "Facebook Ads", campaign: "Offer Campaigns - Conversions", clicks: 10245, impressions: 764627)]
    }
}
