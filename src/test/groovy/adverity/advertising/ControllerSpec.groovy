package adverity.advertising

import adverity.advertising.domain.AdvertisingData
import adverity.advertising.domain.AdvertisingService
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ControllerSpec extends Specification {
    @Inject
    @Client("/")
    RxHttpClient client
    @Inject
    AdvertisingService advertisingService

    def "should get datasource"() {
        given:
        final datasource = "datasource"
        advertisingService.datasources() >> [datasource]
        HttpRequest request = HttpRequest.GET('/advertising/datasources')
        when:
        def response = client.toBlocking().retrieve(request, List)
        then:
        response == [datasource]
    }

    def "should get campaigns"() {
        given:
        final campaign = "campaign"
        advertisingService.campaigns() >> [campaign]
        HttpRequest request = HttpRequest.GET('/advertising/campaigns')
        when:
        def response = client.toBlocking().retrieve(request, List)
        then:
        response == [campaign]
    }

    def "should get date and metrics by datasource and campaign "() {
        given:
        final datasource = "datasource"
        final campaign = "campaign"
        final metric = new AdvertisingData(date: "date", datasource: "datasource", campaign: "campaign", clicks: 1, impressions: 2)
        advertisingService.metrics(datasource, campaign) >> [metric]
        HttpRequest request = HttpRequest.GET('/advertising/metrics/' + datasource + '/' + campaign)
        when:
        def response = client.toBlocking().retrieve(request, List)
        then:
        response.size() == 1
        response.first().date == metric.date
        response.first().datasource == metric.datasource
        response.first().campaign == metric.campaign
        response.first().clicks == metric.clicks
        response.first().impressions == metric.impressions
    }

    @MockBean(AdvertisingService)
    AdvertisingService AdvertisingService() {
        Mock(AdvertisingService)
    }
}
