<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>          
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pays</title>
</head>
<body>

<c:set var="pays" value="${requestScope['listePays']}" />

<c:if test="${fn:length(pays) > 1 }" >
	<h2>Liste des pays</h2>
	<c:forEach var="a" items="${pays}">
		<li>
		<form action="${pageContext.request.contextPath}/pays" method="get">
			<c:out value="${a.name}"/>
			(<c:out value="${a.abbreviation}"/>)						 
		</form>		
		</li>

	</c:forEach>
</c:if>	

<h2>Ajout d'un nouveau pays</h2>

<form action="${pageContext.request.contextPath}/pays" method="post">
	abreviation: <input type="text" name="abrev"/>
	<br/>
	nom: <input type="text" name="nom"/>
	<br/>	
	<input type="submit" value="ajouter"/>
</form>

<p><a href="${pageContext.request.contextPath}/controleur">index</a></

</body>
</html>