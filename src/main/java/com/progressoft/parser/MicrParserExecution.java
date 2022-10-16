package com.progressoft.parser;

import com.progressoft.info.CountryConfig;
import com.progressoft.info.MicrInfo;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.progressoft.info.CountryConfig.GroupNames.*;

public class MicrParserExecution implements MicrParser {
    private final HashMap<String, Boolean> mandatoryFields;
    private final String regex;

    public MicrParserExecution(String countryName) {
        CountryConfig countryConfig = new CountryConfig();
        this.mandatoryFields = countryConfig.getMandatoryFields(countryName);
        this.regex = countryConfig.getRegex(countryName);
    }

    @Override
    public MicrInfo parse(String micr) {
        Matcher matcher = Pattern.compile(regex).matcher(micr);
        if (matcher.find()) {
            return MicrInfo.MicrStepBuilder.newBuilder()
                    .setMandatoryFields(mandatoryFields)
                    .setChequeNumber(matcher.group(CHEQUE_NUMBER).trim())
                    .setBankCode(matcher.group(BANK_CODE).trim())
                    .setBranchCode(matcher.group(BRANCH_CODE).trim())
                    .setAccountNumber(matcher.group(ACCOUNT_NUMBER).trim())
                    .setChequeDigit(matcher.group(CHEQUE_DIGIT).trim())
                    .build();
        } else {
            return MicrInfo.CORRUPTED_MICR;
        }
    }

}
