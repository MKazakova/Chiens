<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couleur est enregistré</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>

  <header>
        <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header> 
  
   <h3>La couleur est enregistré avec succes!</h3> 
   
   <fieldset>
     La couleur <c:out value="${couleur.nom_couleur}"/> est enregistré sous code <c:out value="${couleur.code_couleur}"/>
   </fieldset> 
   
   <a href="<c:url value="/administrateur/formCouleur.jsp"/>" >
        <input type="button" value="Ajouter autre couleur" class="buttonLeftFirst"/> 
   </a>

</body>
</html>