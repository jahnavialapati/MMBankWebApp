<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="transfer.mm">
		<label>Enter sender's account number:</label><input type="number" name="senderaccnum">
		<label>Enter receiver's account number:</label><input type="number" name="receiveraccnum">
		<label>Enter amount to transfer:</label><input type="number" name="amounttotransfer">
		<input type="submit" name="submit" value="Transfer Amount">
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>