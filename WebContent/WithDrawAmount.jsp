<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="withdraw.mm">
		<label>Enter account number to withdraw:</label><input type="number" name="withdrawaccnum"><br><br>
		<label>Enter amount to withdraw:</label><input type="number" name="withdrawamount">
		<input type="submit" name="withdraw" value="withdraw">
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>