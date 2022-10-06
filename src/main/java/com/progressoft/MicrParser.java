package com.progressoft;

public interface MicrParser {
    MicrInfo parse(String Micr);
    String getChequeNumber_();
    String getBankCode_();
    String getBranchCode_();
    String getAccountNumber_();
    String getChequeDigit_();

}