<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action ="updateAcc.mm">
	           	<h1>UPDATE AN ACCOUNT</h1>
	           	Account Number : <br><input type="text" name="txtNum" readonly="readonly" value="${requestScope.accounts.bankAccount.accountNumber}"><br><br>
	           	Name :<br><input type="text" name="txtAccHn" value="${requestScope.accounts.bankAccount.accountHolderName}"/><br><br>
				AccountBalance :<br><input type="text" name="txtBal" readonly="readonly" value="${requestScope.accounts.bankAccount.accountBalance}"><br><br>
				Salaried :
				<input type="radio" name="rdSal" ${requestScope.accounts.salary==true?"checked":""}>YES
				<input type="radio" name="rdSal" ${requestScope.accounts.salary==true?"":"checked"}>NO<br><br>
				<input type="submit" name="submit" value="Submit">
				<input type="reset" name="reset" value="Reset">
   
        </form>
</body>
</html>