<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
    padding: 10px
}
</style>
</head>
<h2> Summary </h2>
<form action="display" method = "post">
    <div>
    <p style="font-family: Arial"> For the micr
    <% out.println(request.getAttribute("Micr")); %> <br>
    from <% out.println(request.getAttribute("CountryName")); %>.
    <br><br> The following data has been retrieved: <br> </p>
    </div>
    <table style="font-family: Arial">
        <tr>
            <th>Field <strong> </th>
            <th>Values <strong> </th>
        </tr>
        <tr>
            <td>Cheque Number</td>
            <td><%out.println(request.getAttribute("chequeNumber"));%></td>
        </tr>
        <tr>
            <td>Bank Code</td>
            <td><%out.println(request.getAttribute("bankCode"));%></td>
        </tr>
        <tr>
            <td>Branch Code</td>
            <td><%out.println(request.getAttribute("branchCode"));%></td>
        </tr>
        <tr>
            <td>Account Number</td>
            <td><%out.println(request.getAttribute("accountNumber"));%></td>
        </tr>
        <tr>
            <td>Cheque Digit</td>
            <td><%out.println(request.getAttribute("chequeDigit"));%></td>
        </tr>
        <tr>
            <td>Micr Status</td>
            <td><%out.println(request.getAttribute("micrStatus"));%></td>
        </tr>

    </table>
    <br>
    <a href = "index.jsp" > Back to home page <a>
</form>
</html>