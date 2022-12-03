# MICR Parser Application

Welcome to the MICR parser application!

This application is meant to parse MICR code from different countries. 

**INPUT:** MICR Code

**OUTPUT:** Summary of MICR code fields:
- Cheque number
- Bank Code
- Branch Code
- Account Number
- Cheque Digit
- MICR Status (Fully Read, Partially Read, Corrupted)

The countries that currently have a regex string configured are:
- Oman
- United Arab Emirates
- Bahrain

In order to run the applicatoin locally. You will have to download apache-tomcat-9.0.68. (later versions will not work).
After that, build the application and insert the .war file in the apache-tomcat-9.0.68/webapps folder.
Then, in your terminal, run the startup command found in the bin folder. The default port number is 8080.
When started, open localhost:8080 and you will find the tomcat-page.
Then navigate to localhost:8080/{micr-parser.war file name}. (default is localhost:8080/micr-parser-web-app/).

#### Enjoy :)
