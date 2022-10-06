import org.junit.jupiter.api.Test;
import com.progressoft.CountryConfig;

import static org.junit.jupiter.api.Assertions.*;

class CountryConfigTest {

    @Test
    void getRegexForBahrain() {
        CountryConfig micrRegex = new CountryConfig();
        assertEquals("\\D(?<chequeNumber>\\d+|\s*)\\D{1,2}(?<bankCode>\\d+|\s*)\\D*(?<branchCode>\\d+|\s*)" +
                        "\\D+(?<accountNumber>\\d+|\s*)\\D+\s*(?<chequeDigit>\\d+|\s*)",
                micrRegex.getRegex("Oman"));
    }

    @Test
    void getRegexForOman() {
        CountryConfig countryConfig = new CountryConfig();
        assertEquals("\\D(?<chequeNumber>\\d+|\s*)\\D{1,2}(?<bankCode>\\d+|\s*)\\D*(?<branchCode>\\d+|\s*)" +
                        "\\D+(?<accountNumber>\\d+|\s*)\\D+\s*(?<chequeDigit>\\d+|\s*)",
                countryConfig.getRegex("Oman"));
    }

    @Test
    void getRegexForUae() {
        CountryConfig countryConfig = new CountryConfig();
        assertEquals("\\D(?<chequeNumber>\\d+|\s*)\\D01(?<bankCode>\\d{2}|\s*)\\D*(?<branchCode>\\d+|\s*)" +
                        "\\D+(?<accountNumber>\\d+|\s*)\\D+\s*(?<chequeDigit>\\d*|\s*)",
                countryConfig.getRegex("United Arab Emirates"));
    }
}