package com.progressoft.parser;

import com.progressoft.regex.MicrRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MicrInfo implements MicrParser {

    private String chequeNumber;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String chequeDigit;
    private final Pattern pattern_;

    public MicrInfo(String countryName) {
        MicrRegex micrRegex = new MicrRegex();
        String regex = micrRegex.getRegex(countryName);
        pattern_ = Pattern.compile(regex);
    }
    @Override
    public MicrInfo parse(String micr) {
        Matcher matcher = pattern_.matcher(micr);
        while (matcher.find()) {
            setChequeNumber(matcher.group("chequeNumber"));
            setBankCode(matcher.group("bankCode"));
            setBranchCode(matcher.group("branchCode"));
            setAccountNumber(matcher.group("accountNumber"));
            String chequeDigit = matcher.group("chequeDigit");
            if (chequeDigit.trim().equals("")) {
                setChequeDigit(null);
            } else {
                setChequeDigit(matcher.group("chequeDigit"));
            }
            System.out.println(toString());
        }

        return this;
    }
    @Override
    public String toString() {
        return "Cheque number is: " + getChequeNumber()
        + "\nBank Code is: " + getBankCode()
        + "\nBranch Code is: " + getBranchCode()
        + "\nAccount Number is: " + getAccountNumber()
        + "\nCheque Digit is: " + getChequeDigit() + "\n";
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getChequeDigit() {
        return chequeDigit;
    }

    public void setChequeDigit(String chequeDigit) {
        this.chequeDigit = chequeDigit;
    }

}
