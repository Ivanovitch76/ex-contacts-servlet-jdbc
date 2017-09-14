<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tags</title>
</head>
<body>

<c:set var="tag" value="${requestScope['listeTags']}" />

<c:if test="${fn:length(tag) > 1 }" >
	<h2>Liste des tags</h2>
	<c:forEach var="a" items="${tag}">
		<li>
		<form action="${pageContext.request.contextPath}/tag" method="post">
			<c:out value="${a.value}"/> 
			<input type="hidden"  name="delvalue" value="${a.id}"/>
			<input type="submit" value="delete"/>
		</form>		
		</li>

	</c:forEach>
</c:if>	

<h2>Ajout d'un nouveau tag</h2>

<form action="${pageContext.request.contextPath}/tag" method="post">
	valeur: <input type="text" name="valeur"/>
	<br/><br/>
	<input type="submit" value="ajouter"/>
</form>

<p><a href="${pageContext.request.contextPath}/controleur">index</a></p>

</body>
</html>