import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.progressoft.MicrInfo;

import static org.junit.jupiter.api.Assertions.*;

//TODO add parameterized test for all corrupted and partially read micr
class MicrInfoTest {
    @Test
//    @Disabled
    @DisplayName("Oman MICR Test")
    void omanMircTest() {
        MicrInfo micrInfo = new MicrInfo("Oman");
        String micr = "<00002019<:02=0003:00000010220474< 001";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("00002019", micrInfo.getChequeNumber_()),
                () ->  assertEquals("02", micrInfo.getBankCode_()),
                () ->  assertEquals("0003", micrInfo.getBranchCode_()),
                () ->  assertEquals("00000010220474", micrInfo.getAccountNumber_()),
                () ->  assertEquals("001", micrInfo.getChequeDigit_()),
                () -> assertEquals(Enum.valueOf(MicrInfo.Status.class, "FULLY_READ"),
                        micrInfo.getStatus_()));
    }

    @Test
//    @Disabled
    @DisplayName("Oman MICR Test Partially Read")
    void omanMircTestPartiallyRead() {
        MicrInfo micrInfo = new MicrInfo("Oman");
        String micr = "<<:02=0003:00000010220474< 001";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("", micrInfo.getChequeNumber_()),
                () ->  assertEquals("02", micrInfo.getBankCode_()),
                () ->  assertEquals("0003", micrInfo.getBranchCode_()),
                () ->  assertEquals("00000010220474", micrInfo.getAccountNumber_()),
                () ->  assertEquals("001", micrInfo.getChequeDigit_()),
                () -> assertEquals(Enum.valueOf(MicrInfo.Status.class, "PARTIALLY_READ"),
                        micrInfo.getStatus_())
        );
    }

    @Test
//    @Disabled
    @DisplayName("Oman MICR Test Corrupted")
    void omanMircTestCorrupted() {
        MicrInfo micrInfo = new MicrInfo("Oman");
        String micr = "<<:=:<";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("", micrInfo.getChequeNumber_()),
                () ->  assertEquals("", micrInfo.getBankCode_()),
                () ->  assertEquals("", micrInfo.getBranchCode_()),
                () ->  assertEquals("", micrInfo.getAccountNumber_()),
                () ->  assertEquals("", micrInfo.getChequeDigit_()),
                () -> assertEquals(Enum.valueOf(MicrInfo.Status.class, "CORRUPTED"),
                        micrInfo.getStatus_())
        );
    }

    @Test
//    @Disabled
    @DisplayName("UAE MICR Test")
    void uaeMicrTest() {
        MicrInfo micrInfo = new MicrInfo("United Arab Emirates");
        String micr = "<001590:013320143:<5003352420<";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("001590", micrInfo.getChequeNumber_()),
                () ->  assertEquals("33", micrInfo.getBankCode_()),
                () ->  assertEquals("20143", micrInfo.getBranchCode_()),
                () ->  assertEquals("5003352420", micrInfo.getAccountNumber_()),
                () -> assertNull(micrInfo.getChequeDigit_()),
                () -> assertEquals(Enum.valueOf(MicrInfo.Status.class, "FULLY_READ"),
                        micrInfo.getStatus_())
        );
    }

    @Test
//    @Disabled
    @DisplayName("Bahrain MICR Test")
    void bahrainMicrTest() {
        MicrInfo micrInfo = new MicrInfo("Bahrain");
        String micr = "<000013<09=01:0001077181611<01";
        micrInfo.parse(micr);
        assertAll(() ->  assertEquals("000013", micrInfo.getChequeNumber_()),
                () ->  assertEquals("09", micrInfo.getBankCode_()),
                () ->  assertEquals("01", micrInfo.getBranchCode_()),
                () ->  assertEquals("0001077181611", micrInfo.getAccountNumber_()),
                () ->  assertEquals("01", micrInfo.getChequeDigit_()),
                () -> assertEquals(Enum.valueOf(MicrInfo.Status.class, "FULLY_READ"),
                        micrInfo.getStatus_())
        );
    }

}