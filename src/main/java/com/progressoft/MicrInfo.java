package com.progressoft;

import java.util.HashMap;

import static com.progressoft.CountryConfig.GroupNames.*;
import static com.progressoft.StringUtils.isNullOrEmpty;

public class MicrInfo {

    public static final MicrInfo CORRUPTED_MICR = new MicrInfo(MicrStatus.CORRUPTED);
    private String chequeNumber_;
    private String bankCode_;
    private String branchCode_;
    private String accountNumber_;
    private String chequeDigit_;
    private final MicrStatus micrStatus_;

    private MicrInfo(MicrStatus micrStatus) {
        this.micrStatus_ = micrStatus;
    }

    private MicrInfo(String chequeNumber_,
                     String bankCode_,
                     String branchCode_,
                     String accountNumber_,
                     String chequeDigit_,
                     MicrStatus micrStatus_) {
        this.chequeNumber_ = chequeNumber_;
        this.bankCode_ = bankCode_;
        this.branchCode_ = branchCode_;
        this.accountNumber_ = accountNumber_;
        this.chequeDigit_ = chequeDigit_;
        this.micrStatus_ = micrStatus_;
    }

    public String getChequeNumber_() {
        return chequeNumber_;
    }

    public String getBankCode_() {
        return bankCode_;
    }

    public String getBranchCode_() {
        return branchCode_;
    }

    public String getAccountNumber_() {
        return accountNumber_;
    }


    public String getChequeDigit_() {
        return chequeDigit_;
    }

    public MicrStatus getMicrStatus_() {
        return micrStatus_;
    }

    public enum MicrStatus {
        FULLY_READ,
        PARTIALLY_READ,
        CORRUPTED
    }

    public static class MicrStepBuilder {
        private static String chequeNumber_;
        private static String bankCode_;
        private static String branchCode_;
        private static String accountNumber_;
        private static String chequeDigit_;
        private static HashMap<String, Boolean> mandatoryFields_;

        private MicrStepBuilder() {
        }

        public static MandatoryFields newBuilder() {
            return new Steps();
        }

        public interface MandatoryFields {
            ChequeNumber setMandatoryFields(HashMap<String, Boolean> mandatoryFields);
        }

        public interface ChequeNumber {
            BankCode setChequeNumber(String chequeNumber);
        }

        public interface BankCode {
            BranchCode setBankCode(String bankCode);
        }

        public interface BranchCode {
            AccountNumber setBranchCode(String branchCode);
        }

        public interface AccountNumber {
            ChequeDigit setAccountNumber(String accountNumber);
        }

        public interface ChequeDigit {
            BuildStep setChequeDigit(String chequeDigit);
        }

        public interface BuildStep {
            MicrInfo build();
        }


        public static class Steps implements MandatoryFields, ChequeNumber, BankCode, BranchCode, AccountNumber,
                ChequeDigit, BuildStep {

            private int emptyFieldsCount = 0;

            @Override
            public ChequeNumber setMandatoryFields(HashMap<String, Boolean> mandatoryFields) {
                if (mandatoryFields == null) {
                    throw new IllegalArgumentException("mandatory fields should not be null");
                }
                mandatoryFields_ = mandatoryFields;
                return this;
            }

            @Override
            public BankCode setChequeNumber(String chequeNumber) {
                chequeNumber_ = checkEmptyAndCount(CHEQUE_NUMBER, chequeNumber);
                return this;
            }

            @Override
            public BranchCode setBankCode(String bankCode) {
                bankCode_ = checkEmptyAndCount(BANK_CODE, bankCode);
                return this;
            }

            @Override
            public AccountNumber setBranchCode(String branchCode) {
                branchCode_ = checkEmptyAndCount(BRANCH_CODE, branchCode);
                return this;
            }

            @Override
            public ChequeDigit setAccountNumber(String accountNumber) {
                accountNumber_ = checkEmptyAndCount(ACCOUNT_NUMBER, accountNumber);
                return this;
            }

            @Override
            public BuildStep setChequeDigit(String chequeDigit) {
                chequeDigit_ = checkEmptyAndCount(CHEQUE_DIGIT, chequeDigit);
                return this;
            }

            private String checkEmptyAndCount(String groupName, String field) {
                if (mandatoryFields_.get(groupName) && isNullOrEmpty(field)) {
                    emptyFieldsCount++;
                }
                return isNullOrEmpty(field) ? null : field;
            }

            @Override
            public MicrInfo build() {
                return new MicrInfo(chequeNumber_,
                        bankCode_,
                        branchCode_,
                        accountNumber_,
                        chequeDigit_,
                        retrieveMicrStatus());

            }

            private MicrStatus retrieveMicrStatus() {
                if (getNumberOfMandatoryFields() == emptyFieldsCount) {
                    return MicrStatus.CORRUPTED;
                } else if (emptyFieldsCount > 0) {
                    return MicrStatus.PARTIALLY_READ;
                } else {
                    return MicrStatus.FULLY_READ;
                }

            }

            private int getNumberOfMandatoryFields() {
                int result = 0;
                for (Boolean fieldIsMandatory : mandatoryFields_.values()) {
                    if (fieldIsMandatory) {
                        result++;
                    }
                }
                return result;
            }
        }
    }

}