<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="transfer.mm">
		<label>Enter sender's account number:<input type="number" name="senderaccnum"></label>
		<label>Enter receiver's account number:<input type="number" name="receiveraccnum"></label>
		<label>Enter amount to transfer:<input type="number" name="amounttotransfer"></label>
		<label><input type="submit" name="submit" value="Transfer Amount"></label>
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>