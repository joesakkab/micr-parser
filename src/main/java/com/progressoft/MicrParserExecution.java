package com.progressoft;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.progressoft.CountryConfig.GroupNames.*;

public class MicrParserExecution implements MicrParser {
    private static HashMap<String, Boolean> mandatoryFields;
    private static String regex;

    public MicrParserExecution(String countryName) {
        CountryConfig countryConfig = new CountryConfig();
        mandatoryFields = countryConfig.getMandatoryFields(countryName);
        regex = countryConfig.getRegex(countryName);
    }

    @Override
    public MicrInfo parse(String micr) {
        Matcher matcher = Pattern.compile(regex).matcher(micr);
        if (regex == null) {
            throw new MicrParserException("Provided Regex is null");
        }

        if (matcher.find()) {
            return createMicrInfoObject(matcher);
        } else {
            throw new MicrParserException("Provided micr did not find a match to the regex.");
        }
    }

    private MicrInfo createMicrInfoObject(Matcher matcher) {
        return MicrInfo.MicrStepBuilder.newBuilder()
                .setChequeNumber(matcher.group(CHEQUE_NUMBER).trim())
                .setBankCode(matcher.group(BANK_CODE).trim())
                .setBranchCode(matcher.group(BRANCH_CODE).trim())
                .setAccountNumber(matcher.group(ACCOUNT_NUMBER).trim())
                .setChequeDigit(matcher.group(CHEQUE_DIGIT).trim())
                .build(mandatoryFields);
    }

}
