package statics;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/RequestFilter")
public class RequestFilter implements Filter {
private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();

public RequestFilter() {
}

public void destroy() {
}

public static HttpServletRequest getRequest() {
    System.out.println("Fetching the Request!!!");
    return localRequest.get();
}

public static HttpSession getSession() {
    System.out.println("Fetching the Session!!!");
    HttpServletRequest request = localRequest.get();
    return (request != null) ? request.getSession() : null;
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    chain.doFilter(request, response);

    if (request instanceof HttpServletRequest) {
        localRequest.set((HttpServletRequest) request);
    }

    try {
        chain.doFilter(request, response);
    }
    finally {
        localRequest.remove();
    }
}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    }
