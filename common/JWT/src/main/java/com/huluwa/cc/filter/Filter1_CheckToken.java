
package com.huluwa.cc.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huluwa.cc.config.TokenStateCode;
import com.huluwa.cc.jwt.Jwt;
import com.huluwa.cc.jwt.ProjectToken;
import com.huluwa.cc.jwt.TokenState;
import net.minidev.json.JSONObject;

/**
 * toekn校验过滤器，所有的API接口请求都要经过该过滤器(除了登陆接口)
 * @author hxyHelloWorld@163.com
 *
 */

@WebFilter(urlPatterns="/** ")
public class Filter1_CheckToken  implements Filter {


	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1,
			FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) argo;
		HttpServletResponse response=(HttpServletResponse) arg1;
		if(request.getRequestURI().endsWith("/servlet/login")){
			//登陆接口不校验token，直接放行
			chain.doFilter(request, response);
			return;
		}
		//其他API接口一律校验token
		System.out.println("开始校验token1111");
		//从请求头中获取token
		ProjectToken projectToken = new ProjectToken();
		TokenStateCode tokenStateCode = new TokenStateCode();
		int code = projectToken.checkToken(request,response);

		if(code == tokenStateCode.SUCCESS) {
			chain.doFilter(request, response);
			return;
		}

	}
	public void output(String jsonStr,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
//		out.println();
		out.write(jsonStr);
		out.flush();
		out.close();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("token过滤器初始化了");
	}

	@Override
	public void destroy() {

	}

}

