package com.progressoft.servlets;

import com.progressoft.info.CountryConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listOfRegisteredCountries", new CountryConfig().getRegisteredCountries());
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        System.out.println("Servlet is called");
        dispatcher.forward(req, resp);

    }
}
