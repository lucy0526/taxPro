package core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import nsfw.user.entity.User;
import core.constant.Constant;
import core.permission.PermissionCheck;

public class LoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String uri = request.getRequestURI();//uri没有域名、端口号；url有
		if(!uri.contains("sys/login_")){
			if(request.getSession().getAttribute(Constant.USER) != null){
				//权限访问
				if(uri.contains("/nsfw/")){
					User user = (User)request.getSession().getAttribute(Constant.USER);
					
					/**
					 * 如果 new一个类 形成全新的IOC容器
					 */
					WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());//在 随着应用服务器启动好的IOC容器取值
					PermissionCheck pc = (PermissionCheck) webApplicationContext.getBean("permissionCheck");
					
					if(pc.isAccessible(user, "nsfw")){
						//有权限
						chain.doFilter(request, response);
					}else{
//						chain.doFilter(request, response);
						//没有权限
						response.sendRedirect(request.getContextPath() + "/sys/login_toNoPermissionUI.action");
					}
				}else{
					chain.doFilter(request, response);
				}
				
			}else{//没有登陆
				response.sendRedirect(request.getContextPath() + "/sys/login_toLoginUI.action");
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
