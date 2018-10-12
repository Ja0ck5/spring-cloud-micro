package com.ja0ck5.cloud.service;

import java.util.List;
import java.util.Objects;

import com.ja0ck5.cloud.model.BaseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ojia.base.pagination.Page;

public class BaseService {

	public static final Logger log = LoggerFactory.getLogger(BaseService.class);

	protected String renderData(Object obj) {
		return renderJSON(new BaseView(obj));
	}

	/**
		* 获得json渲染字符串
		*/
	protected String renderJSON(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
	}


	/**
		* 处理for more查询时, 返回数据的问题
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

}
