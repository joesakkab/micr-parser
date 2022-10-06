package com.progressoft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MicrInfo implements MicrParser {

    private String chequeNumber_;
    private String bankCode_;
    private String branchCode_;
    private String accountNumber_;
    private String chequeDigit_;
    private final Pattern pattern_;
    private final boolean[] mandatoryFields_;
    private Status status_;
    private int emptyFields;

    public MicrInfo(String countryName) {
        CountryConfig countryConfig = new CountryConfig();
        String regex = countryConfig.getRegex(countryName);
        pattern_ = Pattern.compile(regex);
        mandatoryFields_ = countryConfig.getMandatoryFields(countryName);
        emptyFields = 0;
    }
    @Override
    public MicrInfo parse(String micr) {
        Matcher matcher = pattern_.matcher(micr);
        setStatus_(Enum.valueOf(Status.class, "FULLY_READ"));
        while (matcher.find()) {
            setChequeNumber_(setStatusGivenField(matcher.group("chequeNumber"), 0));
            setBankCode_(setStatusGivenField(matcher.group("bankCode"), 1));
            setBranchCode_(setStatusGivenField(matcher.group("branchCode"), 2));
            setAccountNumber_(setStatusGivenField(matcher.group("accountNumber"), 3));
            setChequeDigit_(setStatusGivenField(matcher.group("chequeDigit"), 4));
            System.out.println(toString());
        }

        return this;
    }

    private String setStatusGivenField(String field, int i) {
        if (!mandatoryFields_[i] && field.equals("")) {
            return null;
        } else if (mandatoryFields_[i] && field.equals("")) {
            setStatus_(Enum.valueOf(Status.class, "PARTIALLY_READ"));
            emptyFields++;
        }
        if (emptyFields == 5) {
            setStatus_(Enum.valueOf(Status.class, "CORRUPTED"));
        }
        return field;
    }
    @Override
    public String toString() {
        return "Cheque number is: " + getChequeNumber_()
                + "\nBank Code is: " + getBankCode_()
                + "\nBranch Code is: " + getBranchCode_()
                + "\nAccount Number is: " + getAccountNumber_()
                + "\nCheque Digit is: " + getChequeDigit_() + "\n";
    }

    public String getChequeNumber_() {
        return chequeNumber_;
    }

    public void setChequeNumber_(String chequeNumber) {
        this.chequeNumber_ = chequeNumber;
    }

    public String getBankCode_() {
        return bankCode_;
    }

    public void setBankCode_(String bankCode) {
        this.bankCode_ = bankCode;
    }

    public String getBranchCode_() {
        return branchCode_;
    }

    public void setBranchCode_(String branchCode) {
        this.branchCode_ = branchCode;
    }

    public String getAccountNumber_() {
        return accountNumber_;
    }

    public void setAccountNumber_(String accountNumber) {
        this.accountNumber_ = accountNumber;
    }

    public String getChequeDigit_() {
        return chequeDigit_;
    }

    public void setChequeDigit_(String chequeDigit) {
        this.chequeDigit_ = chequeDigit;
    }

    public Status getStatus_() {
        return status_;
    }

    public void setStatus_(Status status_) {
        this.status_ = status_;
    }

    public enum Status {
        FULLY_READ,
        PARTIALLY_READ,
        CORRUPTED
    }

}