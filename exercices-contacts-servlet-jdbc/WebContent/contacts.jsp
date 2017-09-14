<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact</title>
</head>
<body>

<c:set var="contact" value="${requestScope['listeContacts']}" />
<c:set var="pays" value="${requestScope['listePays']}" />
<c:set var="tag" value="${requestScope['listeTags']}" />
<c:set var="tagid" value="${requestScope['tagParContact']}" /> 

<%-- <c:if test="${fn:length(contact) > 1 }" > --%>
<c:if test="${fn:length(tagid) > 1 }">
	<h2>Liste des contacts</h2>
<%-- 	<c:forEach var="a" items="${contact}"> --%>
	<c:forEach var="a" items="${tagid}">
		<li>		
			<c:out value="${a.contact.firstname}"/> 
			<c:out value="${a.contact.name}"/> 
			<c:if test="${a.contact.country.abbreviation != null}">
				(<c:out value="${a.contact.country.abbreviation}"/>) :
			</c:if>
			<c:forEach var="b" items="${a.tag}" varStatus="status">
				${b.value}<c:if test="${not status.last}">,</c:if>
			</c:forEach>
			- <a href="${pageContext.request.contextPath}/contact?delete=${a.contact.id}">delete
<%-- 				<input type="hidden" name="delete" value="${a.id}"/> --%>
			  </a>
		</li>
	</c:forEach>
</c:if>	

<h2>Ajout d'un nouveau contact</h2>

<form action="${pageContext.request.contextPath}/contact" method="post">
	prenom:<input type="text" name="prenom"/>
	<br/>
	nom: <input type="text" name="nom"/>
	<br/>	
	email: <input type="text" name="email"/>
	<br/>
	pays: <select name="listepays">	
  			<option value="aucun">Aucun</option>
			<c:forEach var="p" items="${pays}">
				<option value="${p.abbreviation}"><c:out value="${p.name}"/>	
				</option>					
			</c:forEach>
		  </select>
	<br/>	
	tags: <select name="listetags" multiple>	
  			<option value="aucun">aucun</option>
			<c:forEach var="t" items="${tag}">
				<option value="${t.value}"><c:out value="${t.value}"/> </option>			
			</c:forEach>
		  </select>	
	<br/>	
	
	<input type="submit" value="ajouter"/>
</form>

<p><a href="${pageContext.request.contextPath}/controleur">index</a></p>

</body>
</html>