<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="fundTrans.mm">
<h1>Fund Transfer</h1>
  SenderAcountNumber:<br>
  <input type="text" name="saccNumber" value="">
  <br>
 ReceiverAcountNumber:<br>
  <input type="text" name="raccNumber" value="">
  <br><br>
  TransferAmount:<br>
  <input type="text" name="tAmount" value="">
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