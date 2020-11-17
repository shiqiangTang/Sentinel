package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * nacos规则推送
 * ---------------------------------------------------------------------------
 * Date Author Version Comments
 * 2020/11/17 ericer 1.0 InitialVersion
 **/
@Component("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {
	@Autowired
	private ConfigService configService;
	@Autowired
	private Converter<List<FlowRuleEntity>, String> converter;
	public static final String FLOW_DATA_ID_POSTFIX = "-sentinel";

	@Value("${spring.cloud.sentinel.datasource.ds.nacos.group}")
	private String GROUP_ID;

	@Override
	public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
		AssertUtil.notEmpty(app, "app name cannot be empty");
		if (rules == null) {
			return;
		}
		configService.publishConfig(app + FLOW_DATA_ID_POSTFIX, GROUP_ID, converter.convert(rules));
	}
}
