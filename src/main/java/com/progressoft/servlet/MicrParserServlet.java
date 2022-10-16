package com.progressoft.servlet;

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
        String micr = req.getParameter("micr");
        String countryName = req.getParameter("country");
        MicrParser micrParser = null;
        MicrInfo micrInfo = null;
        boolean success = false;
        RequestDispatcher errorDispatcher = req.getRequestDispatcher("errorPage.jsp");
        try {
            micrParser = new MicrParserExecution(countryName);
            success = true;
        } catch (Exception e) {
            req.setAttribute("Error", "Country is not a country or does not have regex");
            errorDispatcher.forward(req, resp);
        }
        if (success) {
            micrInfo = micrParser.parse(micr);
            if (micrInfo.getChequeNumber_() == null) {
                req.setAttribute("Error", "Micr code is invalid for chosen country");
                errorDispatcher.forward(req, resp);
            }
        }
        req.setAttribute("chequeNumber", micrInfo.getChequeNumber_());
        req.setAttribute("bankCode", micrInfo.getBankCode_());
        req.setAttribute("branchCode", micrInfo.getBranchCode_());
        req.setAttribute("accountNumber", micrInfo.getAccountNumber_());
        req.setAttribute("chequeDigit", micrInfo.getChequeDigit_());
        req.setAttribute("micrStatus", micrInfo.getMicrStatus_());
        req.setAttribute("Micr", micr);
        req.setAttribute("CountryName", countryName);
        RequestDispatcher successDispatcher = req.getRequestDispatcher("result.jsp");
        successDispatcher.forward(req, resp);
    }
}
