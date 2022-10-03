public class MicrInfo implements MicrParser {

    private String chequeNumber;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String chequeDigit;

    @Override
    public MicrInfo parse(String micr) {
        if (micr.contains("=")) {
            parseWithEqualSign(micr);
        } else {
            parseWithoutEqualSign(micr);
        }
        System.out.println(toString());
        return this;
    }

    private void parseWithEqualSign(String micr) {
        assignValuesToFields(getArrayOfFields(micr.split("<|:|="), true));
    }

    private void parseWithoutEqualSign(String micr) {
        assignValuesToFields(getArrayOfFields(micr.split("<|:"), false));

    }
    @Override
    public String toString() {
        return "Cheque number is: " + getChequeNumber()
        + "\nBank Code is: " + getBankCode()
        + "\nBranch Code is: " + getBranchCode()
        + "\nAccount Number is: " + getAccountNumber()
        + "\nCheque Digit is: " + getChequeDigit() + "\n";
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getChequeDigit() {
        return chequeDigit;
    }

    public void setChequeDigit(String chequeDigit) {
        this.chequeDigit = chequeDigit;
    }

    private void assignValuesToFields(String[] values) {
        setChequeNumber(values[0]);
        setBankCode(values[1]);
        setBranchCode(values[2]);
        setAccountNumber(values[3]);
        String chequeDigit = values[4];
        if (chequeDigit == null) {
            setChequeDigit(null);
        } else if (chequeDigit.contains(" ")) {
            setChequeDigit(chequeDigit.trim());
        } else {
            setChequeDigit(null);
        }
    }

    private String[] getArrayOfFields(String[] tokens, boolean hasEqualSign) {
        String[] values = new String[5];
        int j = 0;
        for (String s : tokens) {
            if (!s.equals("")) {
                if (!hasEqualSign && j == 1) {
                    values[j] = s.substring(2, 4);
                    values[j + 1] = s.substring(4);
                    j = 3;
                } else {
                    values[j] = s;
                    j++;
                }

            }
        }
        return values;
    }
}
