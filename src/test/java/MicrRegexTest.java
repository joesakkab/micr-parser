import org.junit.jupiter.api.Test;
import com.progressoft.regex.MicrRegex;

import static org.junit.jupiter.api.Assertions.*;

class MicrRegexTest {

    @Test
    void getRegexForBahrain() {
        MicrRegex micrRegex = new MicrRegex();
        assertEquals("\\D+(?<chequeNumber>\\d+)\\D+(?<bankCode>\\d+)\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d+)",
                micrRegex.getRegex("Oman"));
    }

    @Test
    void getRegexForOman() {
        MicrRegex micrRegex = new MicrRegex();
        assertEquals("\\D+(?<chequeNumber>\\d+)\\D+(?<bankCode>\\d+)\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d+)",
                        micrRegex.getRegex("Oman"));
    }

    @Test
    void getRegexForUae() {
        MicrRegex micrRegex = new MicrRegex();
        assertEquals("\\D+(?<chequeNumber>\\d+)\\D01(?<bankCode>\\d{2})\\D*(?<branchCode>\\d+)" +
                        "\\D+(?<accountNumber>\\d+)\\D+(?<chequeDigit>\\d*)",
                        micrRegex.getRegex("United Arab Emirates"));
    }
}