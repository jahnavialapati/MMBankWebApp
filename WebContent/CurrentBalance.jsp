<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="currentBalance.mm">
		<label>Enter account number to get current balance</label><input type="number" name="getcurrentbalance">
		<input type="submit" name="balance">
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>