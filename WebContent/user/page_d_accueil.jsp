<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Page d'accueil</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/page_accueil.css"/>" />
</head>
<body> 
<header>
<c:import url="/user/header.jsp" />
</header>   
   <c:choose>
         <c:when test = "${rechercheRace}">
            <c:import url="/user/formulaireRaces.jsp" />
         </c:when>
         
         <c:when test = "${trouve}">
            <c:import url="/user/resultat.jsp" />
         </c:when>
         <c:when test = "${notrouve}">
            <h2 class="echec">Nous sommes désolé, il n'y a pas de race correspondant à votre recherche</h2>
         </c:when>
         <c:when test = "${alphabet}">
            <c:import url="/user/alphabet.jsp" />
         </c:when>
         
         <c:when test = "${ficheRaceAlpha}">
            <c:import url="/user/ficheRace.jsp" />
              <a href="<c:url value="/ElevageServlet?whatsend=elevagesDeRace&&code=${race.code_race}"/>" >
               <input type="button" value="Voir les élévages de la race" class="buttonLeftFirst"/> </a>
         </c:when>
         
         <c:when test = "${elevrace}">
            <h3>Les elevages de <c:out value="${nom}"/></h3>
            <c:import url="/user/tableElevages.jsp" />
         </c:when>
         <c:when test = "${touteselevages}">
            <h3>La liste des élévages</h3>
            <c:import url="/user/tableElevages.jsp" />
         </c:when>
         <c:otherwise>
            <c:import url="/user/default.jsp" />
         </c:otherwise>
      </c:choose>
      
</body>
</html>