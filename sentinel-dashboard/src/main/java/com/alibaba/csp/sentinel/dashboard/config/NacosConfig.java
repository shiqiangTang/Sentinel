package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * nacos配置
 * ---------------------------------------------------------------------------
 * Date Author Version Comments
 * 2020/11/17 ericer 1.0 InitialVersion
 **/
@Configuration
public class NacosConfig {
	@Value("${spring.cloud.sentinel.datasource.ds.nacos.server-addr}")
	private String serverAddr;
	@Value("${spring.cloud.sentinel.datasource.ds.nacos.namespace}")
	private String namespace;

	@Bean
	public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
		return s -> JSON.parseArray(s, FlowRuleEntity.class);
	}

	@Bean
	public ConfigService nacosConfigService() throws Exception {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
		properties.put(PropertyKeyConst.NAMESPACE, namespace);
		return ConfigFactory.createConfigService(properties);
	}
}
