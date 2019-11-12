package adverity.advertising.boundary

import adverity.advertising.domain.AdvertisingData
import adverity.advertising.domain.AdvertisingService
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

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
    List<String> compaigns() {
        return advertisingService.campaigns()
    }

    @Get("/datasources")
    List<String> datasources() {
        return advertisingService.datasources()
    }

    @Get("/metrics/{datasource}/{campaign}")
    List<AdvertisingData> metrics(String datasource, String campaign) {
        return advertisingService.metrics(datasource, campaign)
    }
}
