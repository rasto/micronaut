package adverity.advertising.domain

import adverity.advertising.infrastructure.CsvReader
import groovy.transform.Synchronized

import javax.inject.Singleton

@Singleton
class AdvertisingService {
    private Collection<String> datasources = []
    private Collection<String> campaigns = []
    private Map<String, Collection<AdvertisingData>> allMetrics = [:]

    void loadData(CsvReader csvReader) {
        final Set<String> datasources = []
        final Set<String> campaigns = []
        Map<String, Collection<AdvertisingData>> allMetrics = [:]
        csvReader.parse().forEach { advertisingData ->
            datasources.add(advertisingData.datasource)
            campaigns.add(advertisingData.campaign)

            def key = advertisingData.datasource
            def metrics = allMetrics.get(key, [])
            metrics.add(advertisingData)
            allMetrics.put(key, metrics)
        }
        replace(datasources, campaigns, allMetrics)
    }

    @Synchronized
    Set<String> datasources() {
        return datasources
    }

    @Synchronized
    Set<String> campaigns() {
        return campaigns
    }

    @Synchronized
    List<AdvertisingData> metrics(List<String> datasources, List<String> campaigns) {
        return datasources.collect { allMetrics.get(it) }
                          .flatten()
                          .findAll {campaigns.contains(it.campaign) }
    }

    @Synchronized
    private void replace(Set<String> datasources, Set<String> campaigns, Map<String, Collection<AdvertisingData>> allMetrics) {
        this.datasources = datasources
        this.campaigns = campaigns
        this.allMetrics = allMetrics
    }
}
