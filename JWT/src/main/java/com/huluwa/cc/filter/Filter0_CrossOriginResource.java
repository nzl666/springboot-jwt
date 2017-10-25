/*
package com.huluwa.cc.filter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.boot.web.servlet.ServletComponentScan;

*/
/**
 *  2017 10 19/hxy
 * 服务端跨域处理过滤器,该过滤器需要依赖cors-filter-2.2.1.jar和java-property-utils-1.9.1.jar
 * @author running@vip.163.com
 * @param <ServletRequest>
 *
 *//*

@WebFilter(urlPatterns={"*/
/*"},asyncSupported=true,
initParams={
	@WebInitParam(name="cors.allowOrigin",value="*"),
	//支持的方法
	@WebInitParam(name="cors.supportedMethods",value="CONNECT, DELETE, GET, HEAD, OPTIONS, POST, PUT, TRACE"),
	// 注意，如果token字段放在请求头传到后端，这里需要配置
	@WebInitParam(name="cors.supportedHeaders",value="token,Accept, Origin, X-Requested-With, Content-Type, Last-Modified"),
	@WebInitParam(name="cors.exposedHeaders",value="Set-Cookie"),
	@WebInitParam(name="cors.supportsCredentials",value="true")
})
public class Filter0_CrossOriginResource<ServletRequest> extends CORSFilter implements Filter{

	//过滤器初始化
	public void init(FilterConfig config) throws ServletException {
		System.out.println("跨域资源处理过滤器初始化了");
		super.init(config);
	}
	
	public void doFilter(javax.servlet.ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("跨域过滤器");
		super.doFilter(request, response, chain);
	}


	public void setConfiguration(CORSConfiguration config) {
		super.setConfiguration(config);
	}
	
}
*/
