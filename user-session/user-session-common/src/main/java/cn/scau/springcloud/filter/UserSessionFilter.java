package cn.scau.springcloud.filter;

import cn.scau.springcloud.context.UserInfo;
import cn.scau.springcloud.context.UserSessionContextHolder;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.util.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSessionFilter implements Filter {

//    @Resource
//    private RedisUtils redisUtils;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("OnceRequestFilter just supports HTTP requests");
        }
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        RedisUtils redisUtils = ctx.getBean(RedisUtils.class);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String idToken = httpRequest.getHeader("token");
        System.out.println(idToken);
        UserDO userDO  = (UserDO) redisUtils.get(idToken);
        // 未登陆
        if (userDO == null ){
            return;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userDO, userInfo);
        System.out.println(userInfo.toString());
        //解析用户数据
        if (StringUtils.isNotEmpty(idToken) && userInfo != null) {
//            AuthHelper authHelper = SpringContextUtil.getBean(AuthHelper.class);
            UserSessionContextHolder.setUserInfo(userInfo);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            UserSessionContextHolder.clearAllContext();
        }
    }

    @Override
    public void destroy() {

    }
}
