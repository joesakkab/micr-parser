import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MicrInfoTest {

    @Test
    @DisplayName("Test number one")
    void testNumberOne() {
        MicrInfo micrInfo = new MicrInfo();
        String micr = "<00002019<:02=0003:00000010220474< 001";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("00002019", micrInfo.getChequeNumber()),
                () ->  assertEquals("02", micrInfo.getBankCode()),
                () ->  assertEquals("0003", micrInfo.getBranchCode()),
                () ->  assertEquals("00000010220474", micrInfo.getAccountNumber()),
                () ->  assertEquals("001", micrInfo.getChequeDigit()));
    }

    @Test
    @DisplayName("Test number two")
    void testNumberTwo() {
        MicrInfo micrInfo = new MicrInfo();
        String micr = "<001590:013320143:<5003352420<";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("001590", micrInfo.getChequeNumber()),
                () ->  assertEquals("33", micrInfo.getBankCode()),
                () ->  assertEquals("20143", micrInfo.getBranchCode()),
                () ->  assertEquals("5003352420", micrInfo.getAccountNumber()),
                () ->  assertEquals(null, micrInfo.getChequeDigit()));
    }

    @Test
    @DisplayName("Test number three")
    void testNumberThree() {
        MicrInfo micrInfo = new MicrInfo();
        String micr = "<000013<09=01:0001077181611<01";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("000013", micrInfo.getChequeNumber()),
                () ->  assertEquals("09", micrInfo.getBankCode()),
                () ->  assertEquals("01", micrInfo.getBranchCode()),
                () ->  assertEquals("0001077181611", micrInfo.getAccountNumber()),
                () ->  assertEquals(null, micrInfo.getChequeDigit()));
    }

}