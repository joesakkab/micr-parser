import com.progressoft.MicrParser;
import com.progressoft.MicrParserExecution;
import org.junit.jupiter.api.*;
import com.progressoft.MicrInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

//TODO add parameterized test for all corrupted and partially read micr
class MicrInfoTest {
    MicrParser micrParser;
    MicrInfo micrInfo;
    void setup(String countryName, String micr) {
        micrParser = new MicrParserExecution(countryName);
        micrInfo = micrParser.parse(micr);
    }

    @ParameterizedTest
    @DisplayName("Oman Fully Read Micr Test")
    @ValueSource(strings = {"<00002019<:02=0003:00000010220474< 001"})
    void omanFullyReadMicrTest(String micr) {
        setup("Oman", "<00002019<:02=0003:00000010220474< 001");
        assertAll(() -> assertEquals("00002019", micrInfo.getChequeNumber_()),
                () -> assertEquals("02", micrInfo.getBankCode_()),
                () -> assertEquals("0003", micrInfo.getBranchCode_()),
                () -> assertEquals("00000010220474", micrInfo.getAccountNumber_()),
                () -> assertEquals("001", micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.FULLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Oman Partially Read Micr Test")
    @ValueSource(strings = {"<<:02=0003:00000010220474< 001"})
    void omanPartiallyReadMicrTest(String micr) {
        setup("Oman", micr);
        assertAll(() ->  assertNull(micrInfo.getChequeNumber_()),
                () ->  assertEquals("02", micrInfo.getBankCode_()),
                () ->  assertEquals("0003", micrInfo.getBranchCode_()),
                () ->  assertEquals("00000010220474", micrInfo.getAccountNumber_()),
                () ->  assertEquals("001", micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.PARTIALLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Oman Corrupted Micr Test")
    @ValueSource(strings = {"<<:=:<"})
    void omanCorruptedMicrTest(String micr) {
        setup("Oman", micr);
        assertAll(() -> assertNull(micrInfo.getChequeNumber_()),
                () -> assertNull(micrInfo.getBankCode_()),
                () -> assertNull(micrInfo.getBranchCode_()),
                () -> assertNull(micrInfo.getAccountNumber_()),
                () -> assertNull(micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.CORRUPTED, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Bahrain Fully Read Micr Test")
    @ValueSource(strings = {"<000013<09=01:0001077181611<01"})
    void bahrainFullyReadMicrTest(String micr) {
        setup("Bahrain", micr);
        assertAll(() ->  assertEquals("000013", micrInfo.getChequeNumber_()),
                () ->  assertEquals("09", micrInfo.getBankCode_()),
                () ->  assertEquals("01", micrInfo.getBranchCode_()),
                () ->  assertEquals("0001077181611", micrInfo.getAccountNumber_()),
                () -> assertEquals("01", micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.FULLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Bahrain Partially Read Micr Test")
    @ValueSource(strings = {"<000013<09=01:<01"})
    void bahrainPartiallyReadMicrTest(String micr) {
        setup("Bahrain", micr);
        assertAll(() ->  assertEquals("000013", micrInfo.getChequeNumber_()),
                () ->  assertEquals("09", micrInfo.getBankCode_()),
                () ->  assertEquals("01", micrInfo.getBranchCode_()),
                () ->  assertNull(micrInfo.getAccountNumber_()),
                () -> assertEquals("01", micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.PARTIALLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Bahrain Corrupted Micr Test")
    @ValueSource(strings = {"<<=:<"})
    void bahrainCorruptedMicrTest(String micr) {
        setup("Bahrain", micr);
        assertAll(() ->  assertNull(micrInfo.getChequeNumber_()),
                () ->  assertNull(micrInfo.getBankCode_()),
                () ->  assertNull(micrInfo.getBranchCode_()),
                () ->  assertNull(micrInfo.getAccountNumber_()),
                () -> assertNull(micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.CORRUPTED, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("UAE Fully Read Micr Test")
    @ValueSource(strings = {"<001590:013320143:<5003352420<"})
    void uaeFullyReadMicrTest(String micr) {
        setup("United Arab Emirates", micr);
        assertAll(() ->  assertEquals("001590", micrInfo.getChequeNumber_()),
                () ->  assertEquals("33", micrInfo.getBankCode_()),
                () ->  assertEquals("20143", micrInfo.getBranchCode_()),
                () ->  assertEquals("5003352420", micrInfo.getAccountNumber_()),
                () -> assertTrue(micrInfo.getChequeDigit_().isEmpty())
        );
        assertEquals(MicrInfo.MicrStatus.FULLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("UAE Partially Read Micr Test")
    @ValueSource(strings = {"<001590:01:<5003352420<"})
    void uaePartiallyReadMicrTest(String micr) {
        setup("United Arab Emirates", micr);
        assertAll(() ->  assertEquals("001590", micrInfo.getChequeNumber_()),
                () ->  assertNull(micrInfo.getBankCode_()),
                () ->  assertNull(micrInfo.getBranchCode_()),
                () ->  assertEquals("5003352420", micrInfo.getAccountNumber_()),
                () -> assertTrue(micrInfo.getChequeDigit_().isEmpty())
        );
        assertEquals(MicrInfo.MicrStatus.PARTIALLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("UAE Corrupted Micr Test")
    @ValueSource(strings = {"<:01:<<"})
    void uaeCorruptedMicrTest(String micr) {
        setup("United Arab Emirates", micr);
        assertAll(() ->  assertNull(micrInfo.getChequeNumber_()),
                () ->  assertNull(micrInfo.getBankCode_()),
                () ->  assertNull(micrInfo.getBranchCode_()),
                () ->  assertNull(micrInfo.getAccountNumber_()),
                () -> assertTrue(micrInfo.getChequeDigit_().isEmpty())
        );
        assertEquals(MicrInfo.MicrStatus.CORRUPTED, micrInfo.getMicrStatus_());
    }

}