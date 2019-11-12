package adverity.advertising.domain

import adverity.advertising.infrastructure.CsvReader
import groovy.transform.Synchronized

import javax.inject.Singleton

@Singleton
class AdvertisingService {
    private Collection<String> datasources = []
    private Collection<String> campaigns = []
    private Map<Tuple2<String, String>, Collection<AdvertisingData>> allMetrics = [:]

    void loadData(CsvReader csvReader) {
        final datasources = []
        final campaigns = []
        Map<Tuple2<String, String>, Collection<AdvertisingData>> allMetrics = [:]
        csvReader.parse().forEach { advertisingData ->
            datasources.add(advertisingData.datasource)
            campaigns.add(advertisingData.campaign)

            def key = new Tuple2(advertisingData.datasource, advertisingData.campaign)
            def metrics = allMetrics.get(key, [])
            metrics.add(advertisingData)
            allMetrics.put(key, metrics)
        }
        replace(datasources, campaigns, allMetrics)
    }

    @Synchronized
    List<String> datasources() {
        return datasources;
    }

    @Synchronized
    List<String> campaigns() {
        return campaigns;
    }

    @Synchronized
    List<AdvertisingData> metrics(String datasource, String campaign) {
        return allMetrics.get(new Tuple2(datasource, campaign));
    }

    @Synchronized
    private void replace(List<String> datasources, List<String> campaigns, Map<Tuple2<String, String>, Collection<AdvertisingData>> allMetrics) {
        this.datasources = datasources
        this.campaigns = campaigns
        this.allMetrics = allMetrics
    }
}
