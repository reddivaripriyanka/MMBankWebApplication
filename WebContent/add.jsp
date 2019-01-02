<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Add Account</h1>
<form action="createAccount.mm">
  AcountHolderName:<br>
  <input type="text" name="aHolderName" value="">
  <br>
  AccountBalance:<br>
  <input type="text" name="accountBalance" value="">
  <br><br>
  <input type="radio" name="salary" value="salary">Salaried
   <input type="radio" name="salary" value="n">NotSalaried<br>
   <input type="submit" value="Submit">
    <input type="reset" value="Clear" />
    </form> 
<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>