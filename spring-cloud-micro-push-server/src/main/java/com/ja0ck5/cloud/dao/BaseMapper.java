package com.ja0ck5.cloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseMapper {


	protected Long getLongByRS(ResultSet rs, String columnLabel) throws SQLException {
		if (null == rs.getObject(columnLabel)) {
			return null;
		}
		return rs.getLong(columnLabel);
	}

	protected Integer getIntegerByRS(ResultSet rs, String columnLabel) throws SQLException {
		if (null == rs.getObject(columnLabel)) {
			return null;
		}
		return rs.getInt(columnLabel);
	}

	protected Double getDoubleByRS(ResultSet rs, String columnLabel) throws SQLException {
		if (null == rs.getObject(columnLabel)) {
			return null;
		}
		return rs.getDouble(columnLabel);
	}
}
