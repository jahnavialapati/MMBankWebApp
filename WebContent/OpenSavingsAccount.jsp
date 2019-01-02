<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="openSavingsAccount.mm">
		<label>Enter AccountHolderName</label><input type="text" name="txtAccHN" ><br><br>
		<label>Enter Account Balance</label><input type="number" name="txtNumber" ><br><br>
		<label>Salaried:</label><input type="radio" name="rdsal" value="yes">Yes
		<input type="radio" name="rdsal" value="no">No<br><br><br>
		<input type="submit"  name="submit" value="Submit">
		<input type="reset" value="Clear" />
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>