<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Erreur</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
   <h3 class="grosErreur">Nous sommes désolé, <br>
       votre demande n'a pas abouti, veuillez réessayer ultérieurement</h3> 

</body>
</html>