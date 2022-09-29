package com.progressoft.micr;

public class MicrInfo implements MicrParser {

    private String chequeNumber;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String chequeDigit;

    public MicrInfo() {

    }
    public MicrInfo parse(String micr) {
        return new MicrInfo();
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
