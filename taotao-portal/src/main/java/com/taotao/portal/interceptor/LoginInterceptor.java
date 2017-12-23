package com.taotao.portal.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

/**
 * @author Charle Chung created on 2017年12月20日
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Resource
	private UserServiceImpl userServiceImpl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		TbUser tbUser = userServiceImpl.getUserByToken(token);
		if (null == tbUser) {
			response.sendRedirect(userServiceImpl.SSO_BASE_URL + userServiceImpl.SSO_PAGE_LOGIN + "?redirect="
					+ request.getRequestURL());
			// 中断执行
			return false;
		}
		// 保存登录信息
		request.setAttribute("user", tbUser);

		// 继续执行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {

	}

}
