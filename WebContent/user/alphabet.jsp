<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<ul class="tabs">
     <c:set var="first" value="true" scope="page" /><!-- pour que la première lettre soit choisie par default -->
     <c:forEach var="listraces" items="${listraces}">
	     <li>
	    	<input type="radio" name="tabs" id="tab<c:out value="${listraces.key}"/>" <c:if test="${first}">checked</c:if>>
			  <label for="tab<c:out value="${listraces.key}"/>"><c:out value="${listraces.key}"/></label>
		      <c:set var="first" value="false" scope="page" />
			  <div class="tab-content">
	             <c:forEach var="races" items="${listraces.value}"> 
   		          <div onclick="location.href='/chiens/RaceChienServlet?whatsend=rechercheAlpha&&code=${races.code_race}';" class="resultatReceherche"> 
   		            <img src="/chiens/images/${races.image}" class="imageRecherche" />
   		            <h4><c:out value="${races.nom_race}"/></h4>
   		          </div>
  		         </c:forEach>
 			  </div>
		 </li>
	 </c:forEach>
 </ul>