package com.alibaba.otter.manager.biz.monitor.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.manager.biz.config.pipeline.PipelineService;
import com.alibaba.otter.manager.biz.monitor.Monitor;
import com.alibaba.otter.manager.biz.prometheus.MetricService;
import com.alibaba.otter.shared.arbitrate.ArbitrateViewService;
import com.alibaba.otter.shared.arbitrate.model.PositionEventData;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRule;
import com.alibaba.otter.shared.common.model.config.pipeline.Pipeline;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sunpengfei on 2019/5/23.
 */
public class PositionMonitor implements Monitor {

    @Resource(name = "pipelineService")
    private PipelineService pipelineService;

    @Resource(name = "arbitrateViewService")
    private ArbitrateViewService arbitrateViewService;

    @Override
    public void explore() {
        List<Pipeline> pipelineList = pipelineService.listAll();
        if (pipelineList != null) {
            for (Pipeline pipeline : pipelineList) {
                exploreSingle(pipeline);
            }
        }
    }

    @Override
    public void explore(Long... pipelineIds) {
        for (Long pid : pipelineIds) {
            Pipeline pipeline = pipelineService.findById(pid);
            if (pipeline != null){
                exploreSingle(pipeline);
            }
        }
    }

    private void exploreSingle(Pipeline pipeline){
        PositionEventData positionData = arbitrateViewService.getCanalCursor(pipeline.getParameters().getDestinationName(),
                pipeline.getParameters().getMainstemClientId());
        if (positionData != null && StringUtils.isNotEmpty(positionData.getPosition())) {
            JSONObject position = JSONObject.parseObject(positionData.getPosition());
            if (position != null){
                MetricService.getInstance().metric(pipeline.getId(),position.getLong("timestamp"));
            }
        }
    }

    @Override
    public void explore(List<AlarmRule> rules) {

    }
}
