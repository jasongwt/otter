package com.alibaba.otter.manager.biz.prometheus;

import io.prometheus.client.Gauge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunpengfei on 2019/5/23.
 */
public class MetricService {

    private static volatile MetricService instance = new MetricService();

    private Map<String,Gauge> gaugeMap = new HashMap<String,Gauge>();

    private MetricService(){}

    public static MetricService getInstance(){
        return instance;
    }

    private static final String HANDLE_TIME_PREFIX = "handle_time_";
    private static final String HANDLE_POSITION_PREFIX = "handle_position_";

    public void metricTimeStamp(String name, long timestamp){
        Gauge gauge = gaugeMap.get(HANDLE_TIME_PREFIX+name);
        if (gauge == null){
            gauge = Gauge.build()
                    .name(HANDLE_TIME_PREFIX+name).help("latest handle timestamp.").register();
            gaugeMap.put(HANDLE_TIME_PREFIX+name, gauge);
        }
        gauge.set(timestamp);
    }

    public void metricPosition(String name, long position){
        Gauge gauge = gaugeMap.get(HANDLE_POSITION_PREFIX+name);
        if (gauge == null){
            gauge = Gauge.build()
                    .name(HANDLE_POSITION_PREFIX+name).help("latest handle position.").register();
            gaugeMap.put(HANDLE_POSITION_PREFIX+name, gauge);
        }
        gauge.set(position);
    }
}
