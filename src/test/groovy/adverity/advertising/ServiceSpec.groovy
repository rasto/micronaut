package adverity.advertising

import adverity.advertising.domain.AdvertisingData
import adverity.advertising.domain.AdvertisingService
import adverity.advertising.infrastructure.CsvReader
import spock.lang.Specification

class ServiceSpec extends Specification {
    private final static String DATASOURCE = "Facebook Ads"
    private final static String CAMPAIGN = "Like Ads"
    def "should load data on startup"() {
        given:
        final service = new AdvertisingService()
        final csvReader = Stub(CsvReader)
        csvReader.parse() >> [
                    new AdvertisingData(date: "01.01.2019", datasource: "Facebook Ads", campaign: "Like Ads", clicks: 274, impressions: 1979)]
        when:
        service.loadData(csvReader)
        then:
        !service.campaigns().isEmpty()
        !service.datasources().isEmpty()
        !service.metrics(DATASOURCE, CAMPAIGN).isEmpty()
    }
}
