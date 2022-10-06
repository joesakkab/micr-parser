import org.junit.jupiter.api.Test;
import com.progressoft.regex.CountryConfig;

import static org.junit.jupiter.api.Assertions.*;

class CountryConfigTest {

    @Test
    void getRegexForBahrain() {
        CountryConfig micrRegex = new CountryConfig();
        assertEquals("\\D+(?<chequeNumber>\\d+)\\D+(?<bankCode>\\d+)\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d+)",
                micrRegex.getRegex("Oman"));
    }

    @Test
    void getRegexForOman() {
        CountryConfig countryConfig = new CountryConfig();
        assertEquals("\\D+(?<chequeNumber>\\d+)\\D+(?<bankCode>\\d+)\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d+)",
                        countryConfig.getRegex("Oman"));
    }

    @Test
    void getRegexForUae() {
        CountryConfig countryConfig = new CountryConfig();
        assertEquals("\\D+(?<chequeNumber>\\d+)\\D01(?<bankCode>\\d{2})\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d*)",
                        countryConfig.getRegex("United Arab Emirates"));
    }
}