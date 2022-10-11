package com.progressoft;

import java.util.HashMap;

import static com.progressoft.CountryConfig.GroupNames.*;

//TODO use advanced builder
//TODO separate MicrInfo from MicrParser (remove all getters from the interface)
public class MicrInfo {

    private String chequeNumber_;
    private String bankCode_;
    private String branchCode_;
    private String accountNumber_;
    private String chequeDigit_;
    private MicrStatus micrStatus_;

    private MicrInfo() {
        chequeNumber_ = MicrStepBuilder.chequeNumber_;
        bankCode_ = MicrStepBuilder.bankCode_;
        branchCode_ = MicrStepBuilder.branchCode_;
        accountNumber_ = MicrStepBuilder.accountNumber_;
        chequeDigit_ = MicrStepBuilder.chequeDigit_;
        micrStatus_ = MicrStepBuilder.micrStatus_;

    }

//    @Override
    public String infoToString() {
        return "Cheque number is: " + getChequeNumber_()
                + "\nBank Code is: " + getBankCode_()
                + "\nBranch Code is: " + getBranchCode_()
                + "\nAccount Number is: " + getAccountNumber_()
                + "\nCheque Digit is: " + getChequeDigit_() + "\n";
    }

    public String getChequeNumber_() {
        return chequeNumber_;
    }

    public void setChequeNumber_(String chequeNumber) {
        this.chequeNumber_ = chequeNumber;
    }

    public String getBankCode_() {
        return bankCode_;
    }

    public void setBankCode_(String bankCode) {
        this.bankCode_ = bankCode;
    }

    public String getBranchCode_() {
        return branchCode_;
    }

    public void setBranchCode_(String branchCode) {
        this.branchCode_ = branchCode;
    }

    public String getAccountNumber_() {
        return accountNumber_;
    }

    public void setAccountNumber_(String accountNumber) {
        this.accountNumber_ = accountNumber;
    }

    public String getChequeDigit_() {
        return chequeDigit_;
    }

    public void setChequeDigit_(String chequeDigit) {
        this.chequeDigit_ = chequeDigit;
    }

    public MicrStatus getMicrStatus_() {
        return micrStatus_;
    }

    public void setMicrStatus_(MicrStatus micrStatus) { this.micrStatus_ = micrStatus; }

    public enum MicrStatus {
        FULLY_READ,
        PARTIALLY_READ,
        CORRUPTED
    }

    public static class MicrStepBuilder implements MicrParser {

        public static String chequeNumber_;
        public static String bankCode_;
        public static String branchCode_;
        public static String accountNumber_;
        public static String chequeDigit_;
        public static MicrStatus micrStatus_;
        public static HashMap<String, Boolean> mandatoryFields_;

        public MicrStepBuilder() {
        }

        public static ChequeNumber newBuilder() {
            return new Steps();
        }

        @Override
        public MicrInfo parse(String micr) {
            return null;
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
            MicrInfo build(HashMap<String, Boolean> mandatoryFields);
        }


        public static class Steps implements ChequeNumber, BankCode, BranchCode, AccountNumber,
                ChequeDigit, BuildStep {

            @Override
            public BankCode setChequeNumber(String chequeNumber) {
                chequeNumber_ = chequeNumber;
                return this;
            }

            @Override
            public BranchCode setBankCode(String bankCode) {
                bankCode_ = bankCode;
                return this;
            }

            @Override
            public AccountNumber setBranchCode(String branchCode) {
                branchCode_ = branchCode;
                return this;
            }

            @Override
            public ChequeDigit setAccountNumber(String accountNumber) {
                accountNumber_ = accountNumber;
                return this;
            }

            @Override
            public BuildStep setChequeDigit(String chequeDigit) {
                chequeDigit_ = chequeDigit;
                return this;
            }


            public MicrInfo build(HashMap<String, Boolean> mandatoryFields) {
                mandatoryFields_ = mandatoryFields;
                retrieveMicrStatus();
                MicrInfo info = new MicrInfo();
                info.setChequeNumber_(chequeNumber_);
                info.setBankCode_(bankCode_);
                info.setBranchCode_(branchCode_);
                info.setAccountNumber_(accountNumber_);
                info.setChequeDigit_(chequeDigit_);
                info.setMicrStatus_(micrStatus_);
                return info;
            }

            private void retrieveMicrStatus() {
                int nullAndMandatoryCount = setEmptyFieldsToNullAndGetCount();
                if (getNumberOfMandatoryFields() == nullAndMandatoryCount) {
                    micrStatus_ = MicrStatus.CORRUPTED;
                } else if (nullAndMandatoryCount > 0){
                    micrStatus_ = MicrStatus.PARTIALLY_READ;
                } else {
                    micrStatus_ = MicrStatus.FULLY_READ;
                }

            }

            private int setEmptyFieldsToNullAndGetCount() {
                int count = 0;
                if (mandatoryFields_.get(CHEQUE_NUMBER) && chequeNumber_.isEmpty()) {
                    count++;
                    setChequeNumber(null);
                }
                if (mandatoryFields_.get(BANK_CODE) && bankCode_.isEmpty()) {
                    count++;
                    setBankCode(null);
                }
                if (mandatoryFields_.get(BRANCH_CODE) && branchCode_.isEmpty()) {
                    count++;
                    setBranchCode(null);
                }
                if (mandatoryFields_.get(ACCOUNT_NUMBER) && accountNumber_.isEmpty()) {
                    count++;
                    setAccountNumber(null);
                }
                if (mandatoryFields_.get(CHEQUE_DIGIT) && chequeDigit_.isEmpty()) {
                    count++;
                    setChequeDigit(null);
                }
                return count;
            }

            private int getNumberOfMandatoryFields() {
                int result = 0;
                for (Boolean fieldIsMandatory: mandatoryFields_.values()) {
                    if (fieldIsMandatory) { result++;}
                }
                return result;
            }

        }
    }

}