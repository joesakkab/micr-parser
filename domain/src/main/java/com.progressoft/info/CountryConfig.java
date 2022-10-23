package com.progressoft.info;

import com.progressoft.parser.MicrParserException;

import java.util.HashMap;
public class CountryConfig {
    private final HashMap<String, String> micrRegex = new HashMap<>();
    private final HashMap<String, HashMap<String , Boolean>> mandatoryFields = new HashMap<>();

    public static class GroupNames { ;
        public static final String CHEQUE_NUMBER = "chequeNumber";
        public static final String BANK_CODE = "bankCode";
        public static final String BRANCH_CODE = "branchCode";
        public static final String ACCOUNT_NUMBER = "accountNumber";
        public static final String CHEQUE_DIGIT = "chequeDigit";
    }
    public CountryConfig() {
        updateRegexMap();
        updateMandatoryFields();
    }


    private void updateRegexMap() {
        micrRegex.put("Bahrain",
                "<\s*(?<chequeNumber>\\d+|\s*)\s*<\s*(?<bankCode>\\d+|\s*)\s*=\s*(?<branchCode>\\d+|\s*)" +
                        "\s*:\s*(?<accountNumber>\\d+|\s*)\s*<\s*(?<chequeDigit>\\d+|\s*)");

        micrRegex.put("Oman",
                "<\s*(?<chequeNumber>\\d+|\s*)\s*<:\s*(?<bankCode>\\d+|\s*)\s*=\s*(?<branchCode>\\d+|\s*)" +
                        "\s*:\s*(?<accountNumber>\\d+|\s*)\\s*<\s*(?<chequeDigit>\\d+|\s*)");

        micrRegex.put("United Arab Emirates",
                "<\s*(?<chequeNumber>\\d+|\s*)\s*:(01|\\s*)(?<bankCode>\\d{2}|\s*)\s*\s*(?<branchCode>\\d+|\s*)" +
                        "\s*:<\s*(?<accountNumber>\\d+|\s*)\s*<\s*(?<chequeDigit>\\d+|\s*)");
    }

    private void updateMandatoryFields() {
        mandatoryFields.put("Oman",
                createMap(true, true, true, true, true));
        mandatoryFields.put("Bahrain",
                createMap(true, true, true, true, true));
        mandatoryFields.put("United Arab Emirates",
                createMap(true, true, true, true, false));
    }

    private HashMap<String, Boolean> createMap(boolean chequeNumberIsMandatory, boolean bankCodeIsMandatory,
                                               boolean branchCodeIsMandatory, boolean accountNumberIsMandatory,
                                               boolean chequeDigitIsMandatory) {
        HashMap<String, Boolean> result = new HashMap<>();
        result.put(GroupNames.CHEQUE_NUMBER, chequeNumberIsMandatory);
        result.put(GroupNames.BANK_CODE, bankCodeIsMandatory);
        result.put(GroupNames.BRANCH_CODE, branchCodeIsMandatory);
        result.put(GroupNames.ACCOUNT_NUMBER, accountNumberIsMandatory);
        result.put(GroupNames.CHEQUE_DIGIT, chequeDigitIsMandatory);
        return result;

    }

    public String getRegex(String countryName) {
        String regex = micrRegex.get(countryName);
        if (regex == null) {
            throw new MicrParserException("ERROR: This country does not have a " +
                    "registered regex string for parsing.");
        } else {
            return micrRegex.get(countryName);
        }
    }

    public HashMap<String, Boolean> getMandatoryFields(String countryName) {
        return mandatoryFields.get(countryName);
    }
    public String[] getRegisteredCountries() { return micrRegex.keySet().toArray(new String[0]); }
}