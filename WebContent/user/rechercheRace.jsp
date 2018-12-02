<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rechercher une race de chien</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
    <header>
      <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a><br>
      <a href="<c:url value="/user/page_d_accueil.jsp"/>" class="homePage">Page d'accueil</a>
    </header> 
      <c:import url="/user/formulaireRaces.jsp" />     
 </body>
</html>