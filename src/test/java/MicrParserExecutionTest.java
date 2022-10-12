import com.progressoft.MicrInfo;
import com.progressoft.MicrParser;
import com.progressoft.MicrParserExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MicrParserExecutionTest {

    @ParameterizedTest(name = "{index}: {0} - {3}")
    @DisplayName("Micr Test")
    @MethodSource("micrs")
    void parseMicr(String countryName, String micr, MicrInfoDto expectedInfo,
                  String statusDisplay) {
        MicrParser micrParser = new MicrParserExecution(countryName);
        MicrInfo actualInfo = micrParser.parse(micr);
        assertAll(() -> assertEquals(expectedInfo.getChequeNumber(), actualInfo.getChequeNumber_()),
                () -> assertEquals(expectedInfo.getBankCode(), actualInfo.getBankCode_()),
                () -> assertEquals(expectedInfo.getBranchCode(), actualInfo.getBranchCode_()),
                () -> assertEquals(expectedInfo.getAccountNumber(), actualInfo.getAccountNumber_()),
                () -> assertEquals(expectedInfo.getChequeDigit(), actualInfo.getChequeDigit_())
        );
        assertEquals(statusDisplay, actualInfo.getMicrStatus_().name());
    }

    private static Stream<Arguments> micrs() {
        return Stream.of(
                Arguments.of("Oman", "<00002019<:02=0003:00000010220474< 001",
                        new MicrInfoDto("00002019", "02", "0003", "00000010220474", "001"), "FULLY_READ"),
                Arguments.of("Oman", "<12345678<:41=0003:34345354645656< 222",
                        new MicrInfoDto("12345678", "41", "0003", "34345354645656", "222"), "FULLY_READ"),
                Arguments.of("Oman", "<<:02=0003:00000010220474< 001",
                        new MicrInfoDto(null, "02", "0003", "00000010220474", "001"), "PARTIALLY_READ"),
                Arguments.of("Oman", "<00002019<:=0003:00000010220474< 001",
                        new MicrInfoDto("00002019", null, "0003", "00000010220474", "001"), "PARTIALLY_READ"),
                Arguments.of("Oman", "<00002019<:02=0003:< 001",
                        new MicrInfoDto("00002019", "02", "0003", null, "001"), "PARTIALLY_READ"),
                Arguments.of("Oman", "<<:=:<", new MicrInfoDto(), "CORRUPTED"),
                Arguments.of("Oman", "<<", new MicrInfoDto(), "CORRUPTED"),

                Arguments.of("United Arab Emirates", "<00002019:01340003:<00000010220474< 001",
                        new MicrInfoDto("00002019", "34", "0003", "00000010220474", "001"), "FULLY_READ"),
                Arguments.of("United Arab Emirates", "<12345678:01934903:<34345354645656<",
                        new MicrInfoDto("12345678", "93", "4903", "34345354645656", null), "FULLY_READ"),
                Arguments.of("United Arab Emirates", "<00002019::<00000010220474< 001",
                        new MicrInfoDto("00002019", null, null, "00000010220474", "001"), "PARTIALLY_READ"),
                Arguments.of("United Arab Emirates", "<:01934903:<34345354645656<",
                        new MicrInfoDto(null, "93", "4903", "34345354645656", null), "PARTIALLY_READ"),
                Arguments.of("United Arab Emirates", "<:01:<<", new MicrInfoDto(), "CORRUPTED"),
                Arguments.of("United Arab Emirates", "<:01<", new MicrInfoDto(), "CORRUPTED"),

                Arguments.of("Bahrain", "<000013<09=01:0001077181611<01",
                        new MicrInfoDto("000013", "09", "01", "0001077181611", "01"), "FULLY_READ"),
                Arguments.of("Bahrain", "<0000713<06=54:00010771611<43",
                        new MicrInfoDto("0000713", "06", "54", "00010771611", "43"), "FULLY_READ"),
                Arguments.of("Bahrain", "<<09=01:<01",
                        new MicrInfoDto(null, "09", "01", null, "01"), "PARTIALLY_READ"),
                Arguments.of("Bahrain", "<<=54:00010771611<43",
                        new MicrInfoDto(null, null, "54", "00010771611", "43"), "PARTIALLY_READ"),
                Arguments.of("Bahrain", "<<=:<", new MicrInfoDto(), "CORRUPTED"),
                Arguments.of("Bahrain", "<=:<", new MicrInfoDto(), "CORRUPTED")

        );
    }


    static class MicrInfoDto {
        private String chequeNumber;
        private String bankCode;
        private String branchCode;
        private String accountNumber;
        private String chequeDigit;

        public MicrInfoDto() {
        }

        public MicrInfoDto(String chequeNumber,
                           String bankCode,
                           String branchCode,
                           String accountNumber,
                           String chequeDigit) {
            this.chequeNumber = chequeNumber;
            this.bankCode = bankCode;
            this.branchCode = branchCode;
            this.accountNumber = accountNumber;
            this.chequeDigit = chequeDigit;
        }

        public String getChequeNumber() {
            return chequeNumber;
        }

        public String getBankCode() {
            return bankCode;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getChequeDigit() {
            return chequeDigit;
        }
    }
}