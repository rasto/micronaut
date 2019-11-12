package adverity.advertising.boundary

import adverity.advertising.domain.AdvertisingData
import adverity.advertising.domain.AdvertisingService
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.inject.Inject

@CompileStatic
@Controller("/advertising")
class AdvertisingController {
    private AdvertisingService advertisingService;

    @Inject
    AdvertisingController(AdvertisingService advertisingService) {
        this.advertisingService = advertisingService
    }

    @Get("/campaigns")
    Set<String> compaigns() {
        return advertisingService.campaigns()
    }

    @Get("/datasources")
    Set<String> datasources() {
        return advertisingService.datasources()
    }

    @Post("/metrics")
    List<AdvertisingData> metrics(@Body MetricsQuery metricsQuery) {
        return advertisingService.metrics(metricsQuery.getDatasources(), metricsQuery.getCampaigns())
    }
}
