package com.alibaba.otter.manager.biz.prometheus;

import io.prometheus.client.Gauge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunpengfei on 2019/5/23.
 */
public class MetricService {

    private static volatile MetricService instance = new MetricService();

    private Map<Long,Gauge> gaugeMap = new HashMap<Long,Gauge>();

    private MetricService(){}

    public static MetricService getInstance(){
        return instance;
    }

    public void metric(Long id, long timestamp){
        Gauge gauge = gaugeMap.get(id);
        if (gauge == null){
            gauge = Gauge.build()
                    .name("latest_handle_"+id).help("latest handle timestamp.").register();
            gaugeMap.put(id, gauge);
        }
        gauge.set(timestamp);
    }
}
