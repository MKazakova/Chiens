<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fiche d'elevage</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
  <c:if test="${newElevage}">
  <h3>     L'elevage ${elevage.nomElevage} est ajouté sous le numero ${elevage.codeElevage}</h3>
  </c:if>
  <h3 class="elevage">Fiche de l'elevage</h3>
     <table class="fiche2">
                 <tr>
                <td>Le nom d'elevage</td>
                <td><c:out value="${elevage.nomElevage}" /></td>
                 </tr>
                <tr><td>Numero de telephone</td>
                <td><c:out value="${elevage.telephone}" /></td>
                <tr><td>Adresse</td><td>
                 <c:out value="${elevage.adresse}"/>
                </td>
                <tr><td>Site d'internet</td>
                <td><c:out value="${elevage.siteInternet}"/></td>
                 </tr>              
                <tr><td>E-mail</td>
                <td><c:out value="${elevage.email}"/>    
                </td>
                 </tr>
    </table>
    <a href="/chiens/CataloguesServlet?whatsend=Voir+la+liste+elevages"><input type="button" value="Voir tous les elevages" class="buttonLeftFirst"/></a>
 </body>
</html>

