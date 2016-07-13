package ro.teamnet.zth.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 7/13/2016.
 */
public class HelloWorldServletForward extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String user = "";

        // Set the response type
        resp.setContentType("text/html");

        // Obtain the user from the request instance
        user = req.getParameter("user");

        String s = String.valueOf(req.getAttribute("testAttribute"));
        resp.getWriter().write("Hello <b>"+req.getParameter("user")+ " </b> from the Forward Servlet " + s);
    }
}
