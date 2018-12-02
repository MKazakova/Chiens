<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste de races</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/tablestyle.css"/>" />
<c:if test="${confirmation}">
 <script>
    $(document).ready(function(){
	 $('.pop-outer').fadeIn('slow');
	 $(".close").click(function(){
	 $('.pop-outer').fadeOut('slow');
   });
  });
 </script>
</c:if>
<body> 
     <header>
       <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
     </header> 
                  <h1> La liste de races  : </h1>
                  <table class="dogtable">
                    <tr>
                      <th><h3>Nom de race </h3></th>
                      <th><h3>Pays provenance </h3></th>
                      <th><h3>Taille </h3></th>
                      <th><h3>Poids </h3></th>
                      <th><h3>Aptitudes </h3></th>
                      <th class = "small"><h3>Options</h3></th>
                     </tr> 
                    <c:forEach var="races" items="${listraces}">
                     <tr>
	                   <td><c:out value="${races.nom_race}"/></td>
	                   <td><c:out value="${pays[races.pays_provenance]}"/></td>
	                   <td><c:out value="${races.taille_min}"/>-<c:out value="${races.taille_max}"/></td>
	                   <td><c:out value="${races.poids_min}"/>-<c:out value="${races.poids_max}"/></td>
	                   <td><c:out value="${aptitudes[races.code_race]}"/></td>
	                   <td class = "small">
	                   <form action="${pageContext.request.contextPath}/ListRaceChienServlet" method="POST">
                        <input type="hidden" name="whatsend" value="delete">
                        <input name="element" value="${races.code_race}" type="hidden" >
                        <input type="image" src="style/supprimer.png" class="delete">   
                       </form>          
                       <form action="${pageContext.request.contextPath}/RaceChienServlet" method="POST">
                         <input type="hidden" name="whatsend" value="details">
                         <input name="element" value="${races.code_race}" type="hidden" >
                         <input type="image" src="style/details.png"  class="details" >                 
                       </form>
                       </td>
	                  </tr>
                    </c:forEach>
                   </table>
                   
                  <form action="${pageContext.request.contextPath}/RaceChienServlet" method="Get">
                        <input type="submit" name="whatsend" value="Ajouter une Race" type="hidden" class="TabbuttonLeftFirst" >               
                  </form>
                  
                  <form action="${pageContext.request.contextPath}/RaceChienServlet" method="Get">
                        <input type="submit" name="whatsend" value="Chercher une Race" type="hidden" class="buttonLeft" >               
                  </form>
                  
             <div style="display:none;" class="pop-outer" >
               <div class="pop-inner">
                     <button class="close">X</button>
                     <h4>Etes vous sûr de vouloir supprimer <c:out value="${nom}"/> definitivement?</h4>

                  <form action="${pageContext.request.contextPath}/ListRaceChienServlet" method="GET" >
                        <input name="whatsend" value="toutesRaces" type="hidden" >
                        <input type="submit" value="Non" class="leftFirst">              
                  </form>
                  <form action="${pageContext.request.contextPath}/ListRaceChienServlet" method="POST" >
                        <input type="hidden" name="whatsend" value="SuppressionConfirme">  
                        <input name="element" value="${code}" type="hidden" >
                        <input type="submit" value="Oui" class="left">              
                  </form>
                </div>
              </div>
    </body>
</html>