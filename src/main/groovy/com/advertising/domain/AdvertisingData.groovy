package com.advertising.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import jdk.nashorn.internal.ir.annotations.Immutable

@Immutable
@EqualsAndHashCode
@ToString
class AdvertisingData {
    String date
    String datasource
    String campaign
    int clicks
    int impressions
}
