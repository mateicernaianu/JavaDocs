package ro.teamnet.zth.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 7/12/2016.
 */
public class ZeroToHeroServlet extends HttpServlet{

    private String handleRequest(HttpServletRequest req){
        StringBuilder response = new StringBuilder();
        response.append("Hello <b>");
        response.append(req.getParameter("firstName")+" ");
        response.append(req.getParameter("lastName"));
        response.append("</b>! Enjoy Zero To Hero!!!");
        return response.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String response = handleRequest(req);
        resp.getWriter().write(response);
    }
}
