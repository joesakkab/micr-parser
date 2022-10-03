import java.util.regex.Pattern;

public class MicrInfo implements MicrParser {

    private String chequeNumber;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String chequeDigit;
    private Pattern pattern_;

    @Override
    public MicrInfo parse(String micr) {
        String modifiedMicr = micr.substring(1);
        if (micr.contains("=")) {
            parseWithEqualSign(modifiedMicr);
        } else {
            parseWithoutEqualSign(modifiedMicr);
        }
        System.out.println(toString());
        return this;
    }

    private void parseWithEqualSign(String micr) {
        setPattern(true);
        String[] values = pattern_.split(micr);
        assignValuesToFields(getArrayOfFields(values, true));

    }

    private void parseWithoutEqualSign(String micr) {
        setPattern(false);
        String[] values = pattern_.split(micr);
        assignValuesToFields(getArrayOfFields(values, false));

    }

    private void setPattern(boolean withEqualSign) {
        String delimiter;
        if (withEqualSign) {
            delimiter = "<:?|<.*?|:|=";
        } else {
            delimiter = ":<?|<.*?";
        }

        pattern_ = Pattern.compile(delimiter);
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
            if (!hasEqualSign && j == 1) {
                values[j] = s.substring(2, 4);
                values[j + 1] = s.substring(4);
                j = 3;
            } else {
                values[j] = s;
                j++;
            }
        }
        return values;
    }
}
