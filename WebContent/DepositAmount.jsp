<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="deposit.mm">
		<label>Enter account number to deposit:</label><input type="number" name="depositaccnum"><br><br>
		<label>Enter amount to deposit:</label><input type="number" name="depositamount">
		<input type="submit" name="deposit" value="deposit">
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>