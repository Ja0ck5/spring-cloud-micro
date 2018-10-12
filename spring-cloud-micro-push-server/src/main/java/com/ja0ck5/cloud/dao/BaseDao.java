package com.ja0ck5.cloud.dao;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ojia.base.exception.EmptyStringException;
import com.ojia.base.pagination.Page;
import com.ojia.base.proxy.JdbcTemplateProxy;

@Repository
public abstract class BaseDao extends BaseMapper {

	protected static final Logger log = LoggerFactory.getLogger(BaseDao.class);

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public abstract JdbcTemplateProxy getJdbcTemplate();

	protected <T> List<T> getResults4Page(final String sql, Object[] args, Page page, RowMapper<T> rowMapper) {
		String querySql = sql + String.format(" limit %d,%d", page.getFirstResult(), page.getMaxResults());
		log.debug(querySql);

		List<T> results;
		if (null == args) {
			results = getJdbcTemplate().query(querySql, rowMapper);
		} else {
			results = getJdbcTemplate().query(querySql, args, rowMapper);
		}

		if (page.getTotalRecords() == 0) {
			int fromIndex = sql.indexOf("from");
			String sql4Page = "select count(id) " + sql.substring(fromIndex);
			log.debug(sql4Page);
			if (null == args) {
				page.setTotalRecords(getJdbcTemplate().queryForObject(sql4Page, Long.class));
			} else {
				page.setTotalRecords(getJdbcTemplate().queryForObject(sql4Page, args, Long.class));
			}
		}

		return results;
	}

	/**
	 * 获取结果, 并处理page对象, 处理page对象时, 仅处理是否有下一页的判断
	 * 
	 * @param sql
	 * @param args
	 *            参数列表
	 * @param page
	 * @param rowMapper
	 * @return
	 */
	protected <T> List<T> getResults4More(final String sql, Object[] args, Page page, RowMapper<T> rowMapper) {
		String querySql = String.format("%s limit %d,%d", sql, page.getFirstResult(), page.getMaxResults() + 1);
		log.debug(querySql);

		List<T> results;
		if (null == args) {
			results = getJdbcTemplate().query(querySql, rowMapper);
		} else {
			results = getJdbcTemplate().query(querySql, args, rowMapper);
		}

		return processPage4More(results, page);
	}

	/**
	 * 处理for more查询时, 返回数据的问题
	 * 
	 * @param results
	 * @param page
	 * @return
	 */
	protected <T> List<T> processPage4More(List<T> results, Page page) {
		page.setTotalRecords(page.getFirstResult() + results.size());
		if (results.size() > page.getMaxResults()) {
			results.remove(results.size() - 1);
		} else if (results.isEmpty()) {
			page.setTotalRecords(page.getFirstResult());
		}
		return results;
	}


	/**
	 * 生成in的sql语句
	 *
	 * @param ids
	 * @param sqlBuilder
	 */
	protected void createSqlForIn(Collection<Long> ids, StrBuilder sqlBuilder) {
		int i = 0;
		for (Long id : ids) {
			if (i != 0) {
				sqlBuilder.append(',');
			}
			sqlBuilder.append(id);
			i++;
		}
		sqlBuilder.append(")");
	}

	/**
	 * 生成in的sql语句
	 *
	 * @param sqlBuilder
	 * @param params
	 */
	protected void createSqlForIn(StrBuilder sqlBuilder, Object... params) {
		int i = 0;
		for (Object param : params) {
			if (i != 0) {
				sqlBuilder.append(',');
			}
			if (param instanceof String) {
				sqlBuilder.append("'").append(param).append("'");
			} else {
				sqlBuilder.append(param);
			}
			i++;
		}
		sqlBuilder.append(")");
	}


	/**
	 * 处理任意条件的sql拼接
	 * 
	 * @param sqlBuilder
	 * @param conditions
	 */
	protected void anyConditions(final StrBuilder sqlBuilder, ArrayList<String> conditions) {
		if (!conditions.isEmpty()) {
			for (int i = 0; i < conditions.size(); i++) {
				if (0 == i) {
					sqlBuilder.append(" where");
				} else {
					sqlBuilder.append(" and");
				}
				sqlBuilder.append(conditions.get(i));
			}
		}
	}

	protected String filterKeyword(String keyword) throws EmptyStringException {
		if (null == keyword) {
			throw new EmptyStringException();
		}
		StringUtils.remove(keyword, '%');
		keyword = StringUtils.trimToNull(keyword);
		if (null == keyword) {
			throw new EmptyStringException();
		}
		return keyword;
	}

	/**
	 * 获取当天日期索引
	 * 
	 * @return yyyyMMdd
	 */
	public String getDayIndex() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		return String.format("%d%d%d", year, month, day);
	}

	/**
	 * 获取前一天日期索引
	 * 
	 * @param timestamp
	 *            指定时间戳
	 * @return
	 */
	public String getPreviousDayIndex(long timestamp) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp);
		c.add(Calendar.DATE, -1);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		return String.format("%d%d%d", year, month, day);
	}

}
