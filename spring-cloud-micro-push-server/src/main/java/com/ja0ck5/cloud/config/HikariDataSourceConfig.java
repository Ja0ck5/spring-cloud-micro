package com.ja0ck5.cloud.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojia.base.proxy.JdbcTemplateProxy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据库源配置
 *
 * @author sysu
 * @version 2017/6/12 18:14
 */
@Configuration
public class HikariDataSourceConfig {


	@Bean(name = "whaleHomeHikariConfig")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Primary
	@Bean(name = "whaleHomeDataSource", destroyMethod = "close")
	public HikariDataSource dataSource(@Qualifier("whaleHomeHikariConfig") HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}


	@Bean(name = "whaleDataHikariConfig")
	@ConfigurationProperties(prefix = "spring.data-datasource.hikari")
	public HikariConfig dataHikariConfig() {
		return new HikariConfig();
	}

	@Bean(name = "whaleDataDataSource", destroyMethod = "close")
	public HikariDataSource dataDataSource(@Qualifier("whaleDataHikariConfig") HikariConfig dataHikariConfig) {
		return new HikariDataSource(dataHikariConfig);
	}



	@Primary
	@Bean(name = "whaleJdbcTemplateMaster")
	public JdbcTemplate whaleJdbcTemplateMaster(
			@Qualifier("whaleHomeDataSource") HikariDataSource whaleHomeDataSource) {
		return new JdbcTemplate(whaleHomeDataSource);
	}

	@Bean(name = "whaleDataJdbcTemplateMaster")
	public JdbcTemplate whaleDataJdbcTemplateMaster(
			@Qualifier("whaleDataDataSource") HikariDataSource whaleDataDataSource) {
		return new JdbcTemplate(whaleDataDataSource);
	}

	@Bean(name = "whaleHomeJdbcTemplate")
	public JdbcTemplateProxy whaleHomeJdbcTemplate(
			@Qualifier("whaleJdbcTemplateMaster") JdbcTemplate jdbcTemplate,
			@Qualifier("whaleJdbcTemplateMaster") JdbcTemplate jdbcTemplate4Slave) {
		return new JdbcTemplateProxy(jdbcTemplate, jdbcTemplate4Slave);
	}

	@Bean(name = "whaleDataJdbcTemplate")
	public JdbcTemplateProxy whaleDataJdbcTemplate(
			@Qualifier("whaleDataJdbcTemplateMaster") JdbcTemplate jdbcTemplate,
			@Qualifier("whaleDataJdbcTemplateMaster") JdbcTemplate jdbcTemplate4Slave) {
		return new JdbcTemplateProxy(jdbcTemplate, jdbcTemplate4Slave);
	}

}
