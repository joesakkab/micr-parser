import com.progressoft.MicrParser;
import com.progressoft.MicrParserExecution;
import org.junit.jupiter.api.*;
import com.progressoft.MicrInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MicrInfoTest {
    MicrParser micrParser;
    MicrInfo micrInfo;

    void setup(String countryName, String micr) {
        micrParser = new MicrParserExecution(countryName);
        micrInfo = micrParser.parse(micr);
    }

    @ParameterizedTest
    @DisplayName("Oman Micr Test")
    @MethodSource("micrs")
    void omanMicrTest(String micr, MicrInfoDto micrInfoDto) {
        setup("Oman", micr);
        assertAll(() -> assertEquals(micrInfoDto.getChequeNumber(), micrInfo.getChequeNumber_()),
                () -> assertEquals(micrInfoDto.getBankCode(), micrInfo.getBankCode_()),
                () -> assertEquals(micrInfoDto.getBranchCode(), micrInfo.getBranchCode_()),
                () -> assertEquals(micrInfoDto.getAccountNumber(), micrInfo.getAccountNumber_()),
                () -> assertEquals(micrInfoDto.getChequeDigit(), micrInfo.getChequeDigit_())
        );
        assertEquals(micrInfoDto.getMicrStatus(), micrInfo.getMicrStatus_().name());
    }

    //TODO to support country as an arg --> will result to produce all the test to one test
    private static Stream<Arguments> micrs() {
        return Stream.of(
                Arguments.of("<00002019<:02=0003:00000010220474< 001",
                        new MicrInfoDto("00002019", "02", "0003", "00000010220474", "001", "FULLY_READ")),
                Arguments.of("<12345678<:41=0003:34345354645656< 222",
                        new MicrInfoDto("12345678", "41", "0003", "34345354645656", "222", "FULLY_READ")),
                Arguments.of("<<:02=0003:00000010220474< 001",
                        new MicrInfoDto(null, "02", "0003", "00000010220474", "001", "PARTIALLY_READ")),
                Arguments.of("<00002019<:=0003:00000010220474< 001",
                        new MicrInfoDto("00002019", null, "0003", "00000010220474", "001", "PARTIALLY_READ")),
                Arguments.of("<00002019<:02=0003:< 001",
                        new MicrInfoDto("00002019", "02", "0003", null, "001", "PARTIALLY_READ")),
                Arguments.of("<<:=:<", new MicrInfoDto("CORRUPTED")),
                Arguments.of("<<", new MicrInfoDto("CORRUPTED")) //TODO fix this scenario


        );
    }


    @ParameterizedTest
    @DisplayName("Bahrain Fully Read Micr Test")
    @ValueSource(strings = {"<000013<09=01:0001077181611<01"})
    void bahrainFullyReadMicrTest(String micr) {
        setup("Bahrain", micr);
        assertAll(() -> assertEquals("000013", micrInfo.getChequeNumber_()),
                () -> assertEquals("09", micrInfo.getBankCode_()),
                () -> assertEquals("01", micrInfo.getBranchCode_()),
                () -> assertEquals("0001077181611", micrInfo.getAccountNumber_()),
                () -> assertEquals("01", micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.FULLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Bahrain Partially Read Micr Test")
    @ValueSource(strings = {"<000013<09=01:<01"})
    void bahrainPartiallyReadMicrTest(String micr) {
        setup("Bahrain", micr);
        assertAll(() -> assertEquals("000013", micrInfo.getChequeNumber_()),
                () -> assertEquals("09", micrInfo.getBankCode_()),
                () -> assertEquals("01", micrInfo.getBranchCode_()),
                () -> assertNull(micrInfo.getAccountNumber_()),
                () -> assertEquals("01", micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.PARTIALLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("Bahrain Corrupted Micr Test")
    @ValueSource(strings = {"<<=:<"})
    void bahrainCorruptedMicrTest(String micr) {
        setup("Bahrain", micr);
        assertAll(() -> assertNull(micrInfo.getChequeNumber_()),
                () -> assertNull(micrInfo.getBankCode_()),
                () -> assertNull(micrInfo.getBranchCode_()),
                () -> assertNull(micrInfo.getAccountNumber_()),
                () -> assertNull(micrInfo.getChequeDigit_())
        );
        assertEquals(MicrInfo.MicrStatus.CORRUPTED, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("UAE Fully Read Micr Test")
    @ValueSource(strings = {"<001590:013320143:<5003352420<"})
    void uaeFullyReadMicrTest(String micr) {
        setup("United Arab Emirates", micr);
        assertAll(() -> assertEquals("001590", micrInfo.getChequeNumber_()),
                () -> assertEquals("33", micrInfo.getBankCode_()),
                () -> assertEquals("20143", micrInfo.getBranchCode_()),
                () -> assertEquals("5003352420", micrInfo.getAccountNumber_()),
                () -> assertTrue(micrInfo.getChequeDigit_().isEmpty())
        );
        assertEquals(MicrInfo.MicrStatus.FULLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("UAE Partially Read Micr Test")
    @ValueSource(strings = {"<001590:01:<5003352420<"})
    void uaePartiallyReadMicrTest(String micr) {
        setup("United Arab Emirates", micr);
        assertAll(() -> assertEquals("001590", micrInfo.getChequeNumber_()),
                () -> assertNull(micrInfo.getBankCode_()),
                () -> assertNull(micrInfo.getBranchCode_()),
                () -> assertEquals("5003352420", micrInfo.getAccountNumber_()),
                () -> assertTrue(micrInfo.getChequeDigit_().isEmpty())
        );
        assertEquals(MicrInfo.MicrStatus.PARTIALLY_READ, micrInfo.getMicrStatus_());
    }

    @ParameterizedTest
    @DisplayName("UAE Corrupted Micr Test")
    @ValueSource(strings = {"<:01:<<"})
    void uaeCorruptedMicrTest(String micr) {
        setup("United Arab Emirates", micr);
        assertAll(() -> assertNull(micrInfo.getChequeNumber_()),
                () -> assertNull(micrInfo.getBankCode_()),
                () -> assertNull(micrInfo.getBranchCode_()),
                () -> assertNull(micrInfo.getAccountNumber_()),
                () -> assertTrue(micrInfo.getChequeDigit_().isEmpty())
        );
        assertEquals(MicrInfo.MicrStatus.CORRUPTED, micrInfo.getMicrStatus_());
    }


    static class MicrInfoDto {
        private String chequeNumber;
        private String bankCode;
        private String branchCode;
        private String accountNumber;
        private String chequeDigit;
        private String micrStatus;

        public MicrInfoDto(String micrStatus) {
            this.micrStatus = micrStatus;
        }

        public MicrInfoDto(String chequeNumber,
                           String bankCode,
                           String branchCode,
                           String accountNumber,
                           String chequeDigit,
                           String micrStatus) {
            this.chequeNumber = chequeNumber;
            this.bankCode = bankCode;
            this.branchCode = branchCode;
            this.accountNumber = accountNumber;
            this.chequeDigit = chequeDigit;
            this.micrStatus = micrStatus;
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

        public String getMicrStatus() {
            return micrStatus;
        }
    }
}