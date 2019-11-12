package adverity.advertising

import adverity.advertising.domain.AdvertisingService
import adverity.advertising.infrastructure.CsvReader
import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent

import javax.inject.Inject

@CompileStatic
class Application {
    @Inject
    AdvertisingService advertisingService;

    @EventListener
    void onStartup(ServerStartupEvent event) {
        advertisingService.loadData(new CsvReader(new File(getClass().getResource("/advertising/advertising-data.csv").toURI())))

    }
    static void main(String[] args) {
        Micronaut.run(Application)
    }
}