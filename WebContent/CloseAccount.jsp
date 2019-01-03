<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Close Account</H1>
<form action="deleteAccount.mm">
  AcountNumber:<br>
  <input type="text" name="aNumber" value="">
   <input type="submit" value="Submit"><br>
    <input type="reset" value="Clear" />
  <br>
  </form>
    <div>
		<jsp:include page="homeLink.jsp"></jsp:include>
	</div>
</body>
</html>