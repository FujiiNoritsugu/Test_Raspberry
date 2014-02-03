<%@ page import="java.util.List" %>
<%@ page import="test.gcm.server.*" %>
<%@ page language="java" contentType="text/html; charset=windows-31j"
    pageEncoding="windows-31j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-31j">
<title>Cloud Hole</title>
<script type="text/javascript">
<!--
function nextAction(type){
	document.form.action_type.value = type;
	document.form.submit();
}
 -->
 </script>
</head>
<body>
<%
String status = (String)this.getServletContext().getAttribute("status");
if (status != null) {
  out.print(status);
}
List<String> devices = Datastore.getDevices();
if (devices.isEmpty()) {
%>
<h2>No devices registered!</h2>
<%
} else {
%>
	<form name='form' method='POST' action='sendAll'>
		<input type="button" value="ピストン" onClick="nextAction(1);" />
		<input type="button" value="ローリング"  onClick="nextAction(2);"/>
		<input type="button" value="逆ローリング"  onClick="nextAction(3);"/>
		<input type="button" value="ピストンストップ"  onClick="nextAction(4);"/>
		<input type="button" value="ローリングストップ" onClick="nextAction(5);"/>
		<input type="button" value="ストップ"  onClick="nextAction(6);"/>
		<input type="hidden" name="action_type" value="" />
	</form>
<%
}
%>
</body>
</html>