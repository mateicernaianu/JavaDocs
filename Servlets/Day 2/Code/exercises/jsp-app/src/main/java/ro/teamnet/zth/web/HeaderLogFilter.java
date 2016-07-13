package ro.teamnet.zth.web;

import static ro.teamnet.zth.file.LogFileWriter.logHeader;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;


/**
 * Created by user on 7/13/2016.
 */
public class HeaderLogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Enumeration e = ((HttpServletRequest)servletRequest).getHeaderNames();
        while(e.hasMoreElements())
        {
            String aux = (String) e.nextElement();
            logHeader(aux,((HttpServletRequest) servletRequest).getHeader(aux));
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
