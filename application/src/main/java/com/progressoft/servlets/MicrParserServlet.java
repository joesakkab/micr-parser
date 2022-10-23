package com.progressoft.servlets;

import com.progressoft.info.MicrInfo;
import com.progressoft.parser.MicrParser;
import com.progressoft.parser.MicrParserExecution;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MicrParserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String micr = req.getParameter("micr").trim();
        String countryName = req.getParameter("country").trim();
        RequestDispatcher errorDispatcher = req.getRequestDispatcher("errorPage.jsp");
        String[] tokens = req.getRequestURL().toString().split("/");
        String page = tokens[tokens.length - 2];
        req.setAttribute("url",page);
        try {
            MicrParser micrParser = new MicrParserExecution(countryName);
            MicrInfo micrInfo = micrParser.parse(micr);
            req.setAttribute("chequeNumber", getValue(micrInfo.getChequeNumber_()));
            req.setAttribute("bankCode", getValue(micrInfo.getBankCode_()));
            req.setAttribute("branchCode", getValue(micrInfo.getBranchCode_()));
            req.setAttribute("accountNumber", getValue(micrInfo.getAccountNumber_()));
            req.setAttribute("chequeDigit", getValue(micrInfo.getChequeDigit_()));
            req.setAttribute("micrStatus", getValue(micrInfo.getMicrStatus_().toString()));
            req.setAttribute("Micr", micr);
            req.setAttribute("CountryName", countryName);
            RequestDispatcher successDispatcher = req.getRequestDispatcher("result.jsp");
            successDispatcher.forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("Error", "Country is not a country or does not have regex");
            errorDispatcher.forward(req, resp);
        }


    }
    private String getValue(String value) {
        return (value == null) ? "-" : value;
    }
}
