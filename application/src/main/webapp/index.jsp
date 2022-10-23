<html>
    <body>
    <h2>Welcome to MICR parser!</h2>
    <p>
        Enter your MICR code below and click submit to view its information.
    </p>

    <form action="micr" method="get">
        <select name="country">
            <option select>--Select a country--</option>
            <% String[] list = (String[]) request.getAttribute("listOfRegisteredCountries");
            for (int i= 0; i < list.length; i++) {%>
            <option value="<%=list[i]%>"><%=list[i]%></option>
            <%}%>
        </select>
	    <br><br>
	    <input type = "text" name = "micr" placeholder="Enter your MICR code" size="50"><br><br>
        <input type="submit" value = "Submit">
    </form>

    </body>
</html>