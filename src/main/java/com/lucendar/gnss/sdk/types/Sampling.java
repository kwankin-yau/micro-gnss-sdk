package com.lucendar.gnss.sdk.types;

public record Sampling(String name, long min, long max, double mean, double p50, double p75, double p95) {
}
