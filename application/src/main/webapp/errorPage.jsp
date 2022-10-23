<html>

    <h3> <%out.println(request.getAttribute("Error")); %></h3>
    <br>
    <% String url = "/" + request.getAttribute("url"); %>
    <a href = "<%=url%>"> Try again <a>

</html>