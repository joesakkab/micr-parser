public interface MicrParser {
    MicrInfo parse(String Micr);
    String getChequeNumber();
    String getBankCode();
    String getBranchCode();
    String getAccountNumber();
    String getChequeDigit();

}
