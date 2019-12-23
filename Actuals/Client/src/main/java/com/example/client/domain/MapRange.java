package com.example.client.domain;

import lombok.Data;

@Data
public class MapRange {
    private double minLatitude;
    private double maxLatitude;
    private double minLongitude;
    private double maxLongitude;
}
