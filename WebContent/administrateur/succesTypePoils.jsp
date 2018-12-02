<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Type poils est enregistré</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
  
   <h3>Le type de poils est enregistré avec succes!</h3> 
   <fieldset>
     Le type de poils <c:out value="${poils.description_type_poils}"/> est enregistré sous code <c:out value="${poils.code_type_poils}"/>
   </fieldset> 
   <a href="<c:url value="/administrateur/formPoils.jsp"/>" >
     <input type="button" value="Ajouter autre type de poils" class="buttonLeftFirst"/> 
   </a>

</body>
</html>