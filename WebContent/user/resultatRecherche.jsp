<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Resultat de recherche</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/infostyle.css"/>" />

</head>
<body>
  <header>
   <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
  <c:import url="/user/resultat.jsp" />
</body>
</html>