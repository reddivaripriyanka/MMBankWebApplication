<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Deposit Amount</h1>
<form action="depositAmountInAccount.mm">
  AcountNumber:<br>
  <input type="text" name="accNumber" value="">
  <br>
  DepositAmount:<br>
  <input type="text" name="dBalance" value="">
  <br><br>
  <input type="submit" value="Submit">
  <br>
    <input type="reset" value="Clear" />
  </form>
    <div>
		<jsp:include page="homeLink.jsp"></jsp:include>
	</div>
</body>
</html>