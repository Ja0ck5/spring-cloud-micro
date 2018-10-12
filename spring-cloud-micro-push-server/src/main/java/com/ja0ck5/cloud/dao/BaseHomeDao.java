package com.ja0ck5.cloud.dao;

import org.apache.commons.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.ojia.base.proxy.JdbcTemplateProxy;

public class BaseHomeDao extends BaseDao {

	@Autowired
	private JdbcTemplateProxy whaleHomeJdbcTemplate;

	@Override
	public JdbcTemplateProxy getJdbcTemplate() {
		return whaleHomeJdbcTemplate;
	}

	protected String generateIncrSql(String tableName, String columnName, String primaryKeyName, Long primaryKeyValue,
			int increment) {
		if (0 == increment) {
			return null;
		}
		if (increment > 0) {
			return new StrBuilder("update ").append(tableName).append(" set ").append(columnName).append("=")
					.append(columnName).append("+").append(increment).append(" where ").append(primaryKeyName)
					.append(" = ").append(primaryKeyValue).toString();
		}
		increment = -1 * increment;
		return new StrBuilder("update ").append(tableName).append(" set ").append(columnName).append("=")
				.append(columnName).append("-").append(increment).append(" where ").append(primaryKeyName).append(" = ")
				.append(primaryKeyValue).append(" and ").append(columnName).append(">=").append(increment).toString();
	}
}
