<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
                <h1> La liste d'aptitudes  : </h1>
                  <table class="dogtable">
                    <tr>
                      <th><h3>Code d'aptitude</h3></th>
                      <th><h3>Nom d'aptitude</h3></th>
                      <th><h3>Races correspondantes</h3></th>
                      <th class = "small1"></th>
                     </tr> 
                    <c:forEach var="aptitudes" items="${listatributes}">
                     <tr>
	                   <td><c:out value="${aptitudes.code_aptitude}"/></td>
	                   <td><c:out value="${aptitudes.nom_aptitude}"/></td>
	                   <th><c:out value="${races_aptitudes[aptitudes.code_aptitude]}"/></th>
	                   <td class = "small1">
	                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST">
                        <input type="hidden" name="whatsend" value="deleteApt">
                        <input name="element" value="${aptitudes.code_aptitude}" type="hidden" >
                        <input type="image" src="style/supprimer.png" class="delete">   
                       </form>          
                       </td>
	                  </tr>
                    </c:forEach>
                   </table>
                  <a href="<c:url value="/administrateur/formAptitude.jsp"/>" class="homePage"><input type="button" value="Ajouter une aptitude" class="TabbuttonLeftFirst" ></a>               

                  
                 <div style="display:none;" class="pop-outer" >
                  <div class="pop-inner">
                     <button class="close">X</button>
                     <h4>Etes vous sûr de vouloir supprimer une aptitude <c:out value="${nom}"/> definitivement?</h4>

                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="GET">
                        <input type="hidden" name="whatsend" value="Voir la liste aptitudes" class="leftFirst" >  
                        <input type="submit" value="Non" class="leftFirst" >               
                   </form>
                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST" >
                        <input type="hidden" name="whatsend" value="SuppressionConfirmeApt">  
                        <input name="element" value="${code}" type="hidden" >
                        <input type="submit" value="Oui" class="left">              
                   </form>
                  </div>
              </div>
    </body>
</html>