<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Withdraw Amount</h1>
<form action="withdrawAmountInAccount.mm">
  AcountNumber:<br>
  <input type="text" name="aNumber" value="">
  <br>
  WithdrawAmount:<br>
  <input type="text" name="wBalance" value="">
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