package ro.teamnet.zth.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 7/14/2016.
 */
public class HttpSessionZTH extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = "";
        String password = "";

        // Set the response type
        resp.setContentType("text/html");

        // Obtain the user from the request instance
        userName = req.getParameter("username");
        password = req.getParameter("password");

        if(userName.equals("admin") && password.equals("admin")){
            resp.getWriter().write("Welcome back!<br>"+"Username:" +userName+ " " + req.getRequestedSessionId());
        }
        else{
            req.getSession().setAttribute("username", userName);
            req.getSession().setAttribute("session", req.getSession());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/loginFail.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}