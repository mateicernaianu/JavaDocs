package ro.teamnet.zth.web;

import com.sun.jmx.snmp.Enumerated;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by user on 7/12/2016.
 */
public class InfoHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder response = new StringBuilder();
        response.append("<html><body><table>");
        Enumeration headers = req.getHeaderNames();

        while(headers.hasMoreElements()){
            String name = headers.nextElement().toString();
            response.append("<tr><th>" + name + "</th><th>"+req.getHeader(name)+"</th></tr>");

        }

        response.append("</table></body></html>");
        resp.getWriter().write(response.toString());
    }
}
