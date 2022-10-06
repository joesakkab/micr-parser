package com.progressoft;

import java.util.HashMap;
import java.util.Locale;

public class CountryConfig {
    private final HashMap<String, String> micrRegex = new HashMap<>();
    private final HashMap<String, String> countryCodes = new HashMap<>();
    private final HashMap<String, boolean[]> mandatoryFields = new HashMap<>();

    public CountryConfig() {
        setInitialValuesForClassMaps();
        updateRegexMap();
        updateMandatoryFields();
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
                "\\D(?<chequeNumber>\\d+|\s*)\\D{1,2}(?<bankCode>\\d+|\s*)\\D*(?<branchCode>\\d+|\s*)" +
                        "\\D+(?<accountNumber>\\d+|\s*)\\D+\s*(?<chequeDigit>\\d+|\s*)");

        micrRegex.put(countryCodes.get("Oman"),
                "\\D(?<chequeNumber>\\d+|\s*)\\D{1,2}(?<bankCode>\\d+|\s*)\\D*(?<branchCode>\\d+|\s*)" +
                        "\\D+(?<accountNumber>\\d+|\s*)\\D+\s*(?<chequeDigit>\\d+|\s*)");

        micrRegex.put(countryCodes.get("United Arab Emirates"),
                "\\D(?<chequeNumber>\\d+|\s*)\\D01(?<bankCode>\\d{2}|\s*)\\D*(?<branchCode>\\d+|\s*)" +
                        "\\D+(?<accountNumber>\\d+|\s*)\\D+\s*(?<chequeDigit>\\d*|\s*)");
    }

    private void updateMandatoryFields() {
        mandatoryFields.put(countryCodes.get("Oman"),
                new boolean[]{true, true, true, true, true});
        mandatoryFields.put(countryCodes.get("Bahrain"),
                new boolean[]{true, true, true, true, true});
        mandatoryFields.put(countryCodes.get("United Arab Emirates"),
                new boolean[]{true, true, true, true, false});
    }


    public String getRegex(String countryName) {
        String countryCode = countryCodes.get(countryName);
        String regex = micrRegex.get(countryCode);
        if (regex == null) {
            throw new RuntimeException("ERROR: This country does not have a " +
                    "registered regex string for parsing.");
        } else {
            return micrRegex.get(countryCode);
        }
    }

    public boolean[] getMandatoryFields(String countryName) {
        return mandatoryFields.get(countryCodes.get(countryName));
    }
}