<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pays est enregistré</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
   <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
   </header>
  
   <h3>Le pays est ajouté avec succes!</h3> 
   <fieldset>
     Le pays <c:out value="${pays.nom_pays}"/> est enregistré sous code <c:out value="${pays.code_pays}"/>
   </fieldset> 
   <a href="<c:url value="/administrateur/formPays.jsp"/>" >
        <input type="button" value="Ajouter autre pays" class="buttonLeftFirst"/> 
   </a>

</body>
</html>