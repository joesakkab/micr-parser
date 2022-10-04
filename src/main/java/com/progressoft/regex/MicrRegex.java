package com.progressoft.regex;

import java.util.HashMap;
import java.util.Locale;

public class MicrRegex {
    private final HashMap<String, String> micrRegex = new HashMap<>();
    private final HashMap<String, String> countryCodes = new HashMap<>();

    public MicrRegex() {
        setInitialValuesForClassMaps();
        updateRegexMap();
    }

    private void setInitialValuesForClassMaps() {
        for (String iso: Locale.getISOCountries()) {
            String countryName = new Locale("", iso).getDisplayCountry();
            countryCodes.put(countryName, iso);
            micrRegex.put(iso, null);
        }
    }

    private void updateRegexMap() {
        micrRegex.put(countryCodes.get("Bahrain"),
                "\\D+(?<chequeNumber>\\d+)\\D+(?<bankCode>\\d+)\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d+)");

        micrRegex.put(countryCodes.get("Oman"),
                "\\D+(?<chequeNumber>\\d+)\\D+(?<bankCode>\\d+)\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d+)");

        micrRegex.put(countryCodes.get("United Arab Emirates"),
                "\\D+(?<chequeNumber>\\d+)\\D01(?<bankCode>\\d{2})\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d*)");
    }


    public String getRegex(String countryName) {
        String countryCode = countryCodes.get(countryName);
        String regex = micrRegex.get(countryCode);
        if (regex == null) {
            throw new RuntimeException("ERROR: This country does not have a " +
                    "registered regex string for parsing.");
        } else {
            return micrRegex.get(countryCodes.get(countryName));
        }
    }
}
