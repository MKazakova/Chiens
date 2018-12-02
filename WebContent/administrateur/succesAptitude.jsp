<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aptitude est enregistrée</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
  
   <h3>L'aptitude est enregistrée avec succes!</h3> 
   
   <fieldset>
      L'aptitude <c:out value="${aptitude.nom_aptitude}"/> est enregistré 
      sous code <c:out value="${aptitude.code_aptitude}"/>
   </fieldset> 
                 <a href="<c:url value="/administrateur/formAptitude.jsp"/>" >
                        <input type="button" value="Ajouter autre aptitude" class="buttonLeftFirst"/> 
                 </a>
                 <form action="${pageContext.request.contextPath}/CataloguesServlet" method="GET">
                        <input type="submit" name="whatsend" value="Voir la liste d'aptitudes" class="leftFirst" >               
                 </form> 
</body>
</html>