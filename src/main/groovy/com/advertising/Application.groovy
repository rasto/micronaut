package com.advertising


import com.advertising.domain.AdvertisingService
import com.advertising.infrastructure.CsvReader
import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import javax.inject.Inject

@CompileStatic
class Application {
    @Inject
    AdvertisingService advertisingService

    @EventListener
    void onStartup(ServerStartupEvent event) {
        advertisingService.loadData(new CsvReader(new File(getClass().getResource("/advertising/advertising-data.csv").toURI())))

    }
    static void main(String[] args) {
        Micronaut.run(Application)
    }
}