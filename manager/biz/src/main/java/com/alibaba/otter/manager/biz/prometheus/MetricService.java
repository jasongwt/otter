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

    private static final String HANDLE_TIME_PREFIX = "_handle_time";
    private static final String HANDLE_POSITION_PREFIX = "_handle_position";

    public void metricTimeStamp(Long id, long timestamp){
        Gauge gauge = gaugeMap.get(id+HANDLE_TIME_PREFIX);
        if (gauge == null){
            gauge = Gauge.build()
                    .name(id+HANDLE_TIME_PREFIX).help("latest handle timestamp.").register();
            gaugeMap.put(id+HANDLE_TIME_PREFIX, gauge);
        }
        gauge.set(timestamp);
    }

    public void metricPosition(Long id, long position){
        Gauge gauge = gaugeMap.get(id+HANDLE_POSITION_PREFIX);
        if (gauge == null){
            gauge = Gauge.build()
                    .name(id+HANDLE_POSITION_PREFIX).help("latest handle position.").register();
            gaugeMap.put(id+HANDLE_POSITION_PREFIX, gauge);
        }
        gauge.set(position);
    }
}
