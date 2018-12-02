<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fiche de race</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
  <c:if test="${newRace}">
      <h3><c:out value="${race.nom_race }" /> est ajouté</h3>
  </c:if>
  
  <c:import url="/user/ficheRace.jsp" />  
      
   <a href="<c:url value="/RaceChienServlet?whatsend=form"/>" ><input type="button" value="Ajouter autre race de chien" class="buttonLeftFirst"/> </a>
   <a href="<c:url value="/ListRaceChienServlet?whatsend=toutesRaces"/>" ><input type="button" value="Voir toutes les races" class="buttonLeft"/> </a>
   <form  method="post" action="<c:url value="/RaceChienServlet"/>">
          <input name="whatsend" value="editer" type="hidden" > 
          <input name="element" value="${race.code_race}" type="hidden" >
          <input type="submit" value="Editer"  class="buttonLeft"/>         
    </form>
</body>
</html>