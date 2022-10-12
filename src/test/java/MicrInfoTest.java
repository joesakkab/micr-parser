import com.progressoft.MicrParser;
import com.progressoft.MicrParserExecution;
import org.junit.jupiter.api.*;
import com.progressoft.MicrInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MicrInfoTest {
    MicrParser micrParser;
    MicrInfo actualInfo;

    void setup(String countryName, String micr) {
        micrParser = new MicrParserExecution(countryName);
        actualInfo = micrParser.parse(micr);
    }

    @ParameterizedTest(name = "{index}: {0} - {3}")
    @DisplayName("Micr Test")
    @MethodSource("micrs")
    void MicrTest(String countryName, String micr, MicrInfo expectedInfo, String statusDisplay) {
        setup(countryName, micr);
        assertAll(() -> assertEquals(expectedInfo.getChequeNumber_(), actualInfo.getChequeNumber_()),
                () -> assertEquals(expectedInfo.getBankCode_(), actualInfo.getBankCode_()),
                () -> assertEquals(expectedInfo.getBranchCode_(), actualInfo.getBranchCode_()),
                () -> assertEquals(expectedInfo.getAccountNumber_(), actualInfo.getAccountNumber_()),
                () -> assertEquals(expectedInfo.getChequeDigit_(), actualInfo.getChequeDigit_())
        );
        assertEquals(expectedInfo.getMicrStatus_(), actualInfo.getMicrStatus_());
    }

    //TODO to support country as an arg --> will result to produce all the test to one test
    private static Stream<Arguments> micrs() {
        return Stream.of(
                Arguments.of("Oman", "<00002019<:02=0003:00000010220474< 001",
                        new MicrInfo("00002019", "02", "0003", "00000010220474", "001", "FULLY_READ"), "Fully Read"),
                Arguments.of("Oman", "<12345678<:41=0003:34345354645656< 222",
                        new MicrInfo("12345678", "41", "0003", "34345354645656", "222", "FULLY_READ"), "Fully Read"),
                Arguments.of("Oman", "<<:02=0003:00000010220474< 001",
                        new MicrInfo(null, "02", "0003", "00000010220474", "001", "PARTIALLY_READ"), "Partially Read"),
                Arguments.of("Oman", "<00002019<:=0003:00000010220474< 001",
                        new MicrInfo("00002019", null, "0003", "00000010220474", "001", "PARTIALLY_READ"), "Partially Read"),
                Arguments.of("Oman", "<00002019<:02=0003:< 001",
                        new MicrInfo("00002019", "02", "0003", null, "001", "PARTIALLY_READ"),"Partially Read"),
                Arguments.of("Oman", "<<:=:<", new MicrInfo("CORRUPTED"), "Corrupted"),
                Arguments.of("Oman", "<<", new MicrInfo("CORRUPTED"),"Corrupted"),

                Arguments.of("United Arab Emirates", "<00002019:01340003:<00000010220474< 001",
                        new MicrInfo("00002019", "34", "0003", "00000010220474", "001", "FULLY_READ"), "Fully Read"),
                Arguments.of("United Arab Emirates", "<12345678:01934903:<34345354645656<",
                        new MicrInfo("12345678", "93", "4903", "34345354645656", null, "FULLY_READ"), "Fully Read"),
                Arguments.of("United Arab Emirates", "<00002019::<00000010220474< 001",
                        new MicrInfo("00002019", null, null, "00000010220474", "001", "PARTIALLY_READ"), "Partially Read"),
                Arguments.of("United Arab Emirates", "<:01934903:<34345354645656<",
                        new MicrInfo(null, "93", "4903", "34345354645656", null, "PARTIALLY_READ"), "Partially Read"),
                Arguments.of("United Arab Emirates", "<:01:<<", new MicrInfo("CORRUPTED"), "Corrupted"),
                Arguments.of("United Arab Emirates", "<:01<", new MicrInfo( "CORRUPTED"),"Corrupted"),

                Arguments.of("Bahrain", "<000013<09=01:0001077181611<01",
                        new MicrInfo("000013", "09", "01", "0001077181611", "01", "FULLY_READ"), "Fully Read"),
                Arguments.of("Bahrain", "<0000713<06=54:00010771611<43",
                        new MicrInfo("0000713", "06", "54", "00010771611", "43", "FULLY_READ"), "Fully Read"),
                Arguments.of("Bahrain", "<<09=01:<01",
                        new MicrInfo(null, "09", "01", null, "01", "PARTIALLY_READ"), "Partially Read"),
                Arguments.of("Bahrain", "<<=54:00010771611<43",
                        new MicrInfo(null, null, "54", "00010771611", "43", "PARTIALLY_READ"), "Partially Read"),
                Arguments.of("Bahrain", "<<=:<", new MicrInfo("CORRUPTED"),"Corrupted"),
                Arguments.of("Bahrain", "<=:<", new MicrInfo( "CORRUPTED"), "Corrupted")

        );
    }
}