package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * nacos 具体实现规则拉取
 * ---------------------------------------------------------------------------
 * Date Author Version Comments
 * 2020/11/17 ericer 1.0 InitialVersion
 **/
@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {
	@Autowired
	private ConfigService configService;
	@Autowired
	private Converter<String, List<FlowRuleEntity>> converter;
	public static final String FLOW_DATA_ID_POSTFIX = "-sentinel";

	@Value("${spring.cloud.sentinel.datasource.ds.nacos.group}")
	private String GROUP_ID;

	@Override
	public List<FlowRuleEntity> getRules(String appName) throws Exception {
		String rules = configService.getConfig(appName + FLOW_DATA_ID_POSTFIX, GROUP_ID, 3000);
		if (StringUtil.isEmpty(rules)) {
			return new ArrayList<>();
		}
		return converter.convert(rules);
	}
}
