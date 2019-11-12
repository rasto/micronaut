package adverity.advertising.domain

import javax.inject.Singleton

@Singleton
class AdvertisingService {

    List<String> campaigns() {
        return []
    }

    List<String> datasources() {
        return []
    }

    List<AdvertisingData> metrics(String campaign, String datasource) {
        return []
    }
}
