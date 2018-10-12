package com.ja0ck5.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可参考 {@link org.springframework.cloud.netflix.zuul.filters.pre.DebugFilter} 等
 * 编写复杂 Filter Created by Jack on 2017/10/19.
 */
public class PreCustomZuulFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreCustomZuulFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		String remoteHost = RequestContext.getCurrentContext().getRequest().getRemoteHost();
		LOGGER.info("remote host:{}", remoteHost);
		return remoteHost;
	}
}
