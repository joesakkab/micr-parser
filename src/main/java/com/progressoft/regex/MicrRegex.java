package com.progressoft.regex;

import java.util.HashMap;
import java.util.Locale;

public class MicrRegex {

    private final HashMap<String, String> countryCodesToMicrRegex = new HashMap<>();
    private final HashMap<String, String> countryToCountryCodes = new HashMap<>();
    private final String[] groups = new String[5];
    private final String[] delimiters = {"\\D+", "\\D+", "\\D*", "\\D+", "\\D+"};
    private final String[] groupsAdditions = {"+", "+", "+", "+", "+"};

    public MicrRegex() {
        for (String iso: Locale.getISOCountries()) {
            String countryName = new Locale("", iso).getDisplayCountry();
            countryToCountryCodes.put(countryName, iso);
            countryCodesToMicrRegex.put(iso, assignRegex(iso));
            restoreDelimitersAndGroupAdditions();
        }
    }

    private void restoreDelimitersAndGroupAdditions() {
        for (int i = 0; i < delimiters.length; i++) {
            delimiters[i] = (i == 2) ? "\\D*" : "\\D+";
            groupsAdditions[i] = "+";
        }
    }

    private String assignRegex(String countryCode) {
        setRegexParameters(countryCode);
        setGroups();
        return buildRegex();
    }

    private void setRegexParameters(String countryCode) {
        if (countryCode.equals("AE")) {
            groupsAdditions[1] = "{2}";
            delimiters[1] = "\\D01";
            groupsAdditions[4] = "*";
        }
    }

    private void setGroups() {
        String[] groupNames = {"chequeNumber", "bankCode", "branchCode", "accountNumber", "chequeDigit"};
        int idx = 0;
        for (String groupName: groupNames) {
            groups[idx] = "(?<" + groupName + ">\\d" + groupsAdditions[idx] + ")";
            idx++;
        }
    }

    private String buildRegex() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < groups.length; i++) {
            result.append(delimiters[i]).append(groups[i]);
        }
        return result.toString();

    }

    public String getRegex(String countryName) {
        return countryCodesToMicrRegex.get(countryToCountryCodes.get(countryName));
    }
}
