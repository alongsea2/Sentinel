/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taobao.csp.sentinel.dashboard.view;

import java.util.Date;
import java.util.List;

import com.alibaba.csp.sentinel.util.StringUtil;

import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.repository.rule.InMemoryRuleRepositoryAdapter;
import com.taobao.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.taobao.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Flow rule controller (v2).
 *
 * @author Eric Zhao
 * @since 1.4.0
 */
@RestController
@RequestMapping(value = "/v3/flow")
public class FlowControllerV3 {

    private final Logger logger = LoggerFactory.getLogger(FlowControllerV2.class);

    @Autowired
    private InMemoryRuleRepositoryAdapter<FlowRuleEntity> repository;

    @Autowired
    @Qualifier("flowRuleDefaultProvider")
    private DynamicRuleProvider<List<FlowRuleEntity>> ruleProvider;
    @Autowired
    @Qualifier("flowRuleDefaultPublisher")
    private DynamicRulePublisher<List<FlowRuleEntity>> rulePublisher;

    @GetMapping("/rules")
    public Result<List<FlowRuleEntity>> apiQueryMachineRules(@RequestParam String app) {
        return null;
    }

    private <R> Result<R> checkEntityInternal(FlowRuleEntity entity) {
        return null;
    }

    @PostMapping("/rule")
    public Result<FlowRuleEntity> apiAddFlowRule(@RequestBody FlowRuleEntity entity) {
        return null;

    }

    @PutMapping("/rule/{id}")
    public Result<FlowRuleEntity> apiUpdateFlowRule(@PathVariable("id") Long id, @RequestBody FlowRuleEntity entity) {
        return null;

    }

    @DeleteMapping("/rule/{id}")
    public Result<Long> apiDeleteRule(@PathVariable("id") Long id) {
        return null;

    }

    private void publishRules(/*@NonNull*/ String app) throws Exception {
        List<FlowRuleEntity> rules = repository.findAllByApp(app);
        rulePublisher.publish(app, rules);
    }
}
