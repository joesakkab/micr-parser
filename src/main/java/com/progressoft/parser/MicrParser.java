package com.progressoft.parser;

import com.progressoft.parser.MicrInfo;

public interface MicrParser {
    MicrInfo parse(String Micr);
    String getChequeNumber_();
    String getBankCode_();
    String getBranchCode_();
    String getAccountNumber_();
    String getChequeDigit_();

}
