package com.advertising.boundary

import groovy.transform.TupleConstructor

@TupleConstructor
class MetricsQuery {
    private List<String> datasources;
    private List<String> campaigns;

    List<String> getDatasources() {
        return datasources
    }

    List<String> getCampaigns() {
        return campaigns
    }
}
