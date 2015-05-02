<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Grocery</title>
</head>
<body>
	<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost/grocery"
    user="ddo"  password="dustindo"/>
    
    <sql:query dataSource="${snapshot}" var="result">
	SELECT * from Sales_fact limit 10;
	</sql:query>
	
	<table border="1">
	<tr>
	   <th>time_key</th>
	   <th>prodcut_key</th>
	   <th>store_key</th>
	</tr>
	<c:forEach var="row" items="${result.rows}">
	<tr>
	   <td><c:out value="${row.time_key}"/></td>
	   <td><c:out value="${row.store_key}"/></td>
	   <td><c:out value="${row.product_key}"/></td>
	</tr>
	</c:forEach>
	</table>
</body>
</html>