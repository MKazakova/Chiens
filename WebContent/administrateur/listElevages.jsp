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
                  <h1> La liste des élévages : </h1>
                  <table class="dogtable">
                    <tr>
                      <th><h3>Code de l'elevage</h3></th>
                      <th><h3>Nom de l'élévage</h3></th>
                      <th><h3>Telephone</h3></th>
                      <th><h3>Adresse</h3></th>
                      <th><h3>Site internet</h3></th>
                      <th><h3>Email</h3></th>
                      <th><h3>Les races élévées</h3></th>
                      <th class = "small2"><h3>Options</h3></th>
                     </tr> 
                    <c:forEach var="elevage" items="${list_elevages}">
                     <tr>
	                   <td><c:out value="${elevage.codeElevage}"/></td>
	                   <td><c:out value="${elevage.nomElevage}"/></td>
	                   <td><c:out value="${elevage.telephone}"/></td>
	                   <td><c:out value="${elevage.adresse}"/></td>
	                   <td><c:out value="${elevage.siteInternet}"/></td>
	                   <td><c:out value="${elevage.email}"/></td>
	                   <td><c:out value="${elevages_races[elevage.codeElevage]}"/></td>
	                   <td class = "small1">
	                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST">
                        <input type="hidden" name="whatsend" value="deleteElevage">
                        <input name="element" value="${elevage.codeElevage}" type="hidden" >
                        <input type="image" src="style/supprimer.png" class="delete">   
                       </form>     
                       <form action="${pageContext.request.contextPath}/ElevageServlet" method="POST">
                        <input type="hidden" name="whatsend" value="editerElevage">
                        <input name="element" value="${elevage.codeElevage}" type="hidden" >
                        <input type="image" src="style/editer.png" class="details">   
                       </form>       
                       </td>
	                  </tr>
                    </c:forEach>
                   </table>
                  <a href="<c:url value="/ElevageServlet?whatsend=creerElevage"/>" class="homePage"><input type="button" value="Ajouter un élévage" class="TabbuttonLeftFirst" ></a>               

                  
             <div style="display:none;" class="pop-outer" >
                 <div class="pop-inner">
                     <button class="close">X</button>
                     <h4>Etes vous sûr de vouloir supprimer l'elevage <c:out value="${nom}"/> definitivement?</h4>

                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="GET">
                        <input type="hidden" name="whatsend" value="Voir la liste elevages" class="leftFirst" >  
                        <input type="submit" value="Non" class="leftFirst" >               
                   </form>
                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST" >
                        <input type="hidden" name="whatsend" value="SuppressionConfirmeElevage">  
                        <input name="element" value="${code}" type="hidden" >
                        <input type="submit" value="Oui" class="left">              
                   </form>
                  </div>
              </div>
    </body>
</html>