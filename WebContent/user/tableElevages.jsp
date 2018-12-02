<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <c:if test="${!empty elevages}">
    <table class="dogtable">
                    <tr>
                      <th><h3>Nom de l'élévage</h3></th>
                      <th><h3>Telephone</h3></th>
                      <th><h3>Adresse</h3></th>
                      <th><h3>Site internet</h3></th>
                      <th><h3>Email</h3></th>
                      <th><h3>Les races élévées</h3></th>
                    </tr> 
                    
                    <c:forEach var="elevage" items="${elevages}">
                     <tr>
	                   <td><c:out value="${elevage.nomElevage}"/></td>
	                   <td><c:out value="${elevage.telephone}"/></td>
	                   <td><c:out value="${elevage.adresse}"/></td>
	                   <td><c:out value="${elevage.siteInternet}"/></td>
	                   <td><c:out value="${elevage.email}"/></td>
	                   <td><c:out value="${elevages_races[elevage.codeElevage]}"/></td>
	                  </tr>
                    </c:forEach>
    </table>
  </c:if>
  <c:if test="${empty elevages}">
    <h2 class="echec">Nous sommes désolé, il n'y a pas d'elevages de <c:out value="${nom}"/> dans notre base</h2> 
  </c:if>