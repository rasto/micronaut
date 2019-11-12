package adverity.advertising

import adverity.advertising.domain.AdvertisingData
import adverity.advertising.domain.AdvertisingService
import adverity.advertising.infrastructure.CsvReader
import spock.lang.Specification

class ServiceSpec extends Specification {

    def "should extract data for the same datasource and campaign"() {
        given:
        final advertisingData = new AdvertisingData(date: "01.01.2019", datasource: "Facebook Ads", campaign: "Like Ads", clicks: 274, impressions: 1979)
        final anotherAdvertisingData = new AdvertisingData(date: "02.01.2020", datasource: "Facebook Ads", campaign: "Like Ads", clicks: 10, impressions: 0)
        final service = new AdvertisingService()
        final csvReader = Stub(CsvReader)
        csvReader.parse() >> [advertisingData, anotherAdvertisingData]
        when:
        service.loadData(csvReader)
        then:
        service.datasources() == ["Facebook Ads"] as Set
        service.campaigns() == ["Like Ads"] as Set
        service.metrics(["Facebook Ads"], ["Like Ads"]) == [advertisingData, anotherAdvertisingData]
    }

    def "should extract data for different campaign"() {
        given:
        final advertisingData = new AdvertisingData(date: "01.01.2019", datasource: "Facebook Ads", campaign: "Like Ads", clicks: 274, impressions: 1979)
        final anotherAdvertisingData = new AdvertisingData(date: "02.01.2020", datasource: "Facebook Ads", campaign: "Another Like Ads", clicks: 10, impressions: 0)
        final service = new AdvertisingService()
        final csvReader = Stub(CsvReader)
        csvReader.parse() >> [advertisingData, anotherAdvertisingData]
        when:
        service.loadData(csvReader)
        then:
        service.datasources() == ["Facebook Ads"] as Set
        service.campaigns() == ["Like Ads", "Another Like Ads"] as Set
        service.metrics(["Facebook Ads"], ["Like Ads"]) == [advertisingData]
        service.metrics(["Facebook Ads"], ["Another Like Ads"]) == [anotherAdvertisingData]
    }

    def "should extract data for different datasource"() {
        given:
        final advertisingData = new AdvertisingData(date: "01.01.2019", datasource: "Facebook Ads", campaign: "Like Ads", clicks: 274, impressions: 1979)
        final anotherAdvertisingData = new AdvertisingData(date: "02.01.2020", datasource: "Another Facebook Ads", campaign: "Like Ads", clicks: 10, impressions: 0)
        final service = new AdvertisingService()
        final csvReader = Stub(CsvReader)
        csvReader.parse() >> [advertisingData, anotherAdvertisingData]
        when:
        service.loadData(csvReader)
        then:
        service.datasources() == ["Facebook Ads", "Another Facebook Ads"] as Set
        service.campaigns() == ["Like Ads"] as Set
        service.metrics(["Facebook Ads"], ["Like Ads"]) == [advertisingData]
        service.metrics(["Another Facebook Ads"], ["Like Ads"]) == [anotherAdvertisingData]
    }
}
