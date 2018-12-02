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
                  <h1> La liste de pays : </h1>
                  <table class="dogtable">
                    <tr>
                      <th><h3>Code de pays</h3></th>
                      <th><h3>Nom de pays</h3></th>
                      <th><h3>Les races de chien provenantes du pays</h3></th>
                     </tr> 
                    <c:forEach var="pays" items="${listpays}">
                     <tr>
	                   <td><c:out value="${pays.code_pays}"/></td>
	                   <td><c:out value="${pays.nom_pays}"/></td>
	                   <td><c:out value="${races_pays[pays.code_pays]}"/></td>
	                   <td class = "small1">
	                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST">
                        <input type="hidden" name="whatsend" value="deletePays">
                        <input name="element" value="${pays.code_pays}" type="hidden" >
                        <input type="image" src="style/supprimer.png" class="delete">   
                       </form>          
                       </td>
	                  </tr>
                    </c:forEach>
                   </table>
                  <a href="<c:url value="/administrateur/formPays.jsp"/>" class="homePage"><input type="button" value="Ajouter un pays" class="TabbuttonLeftFirst" ></a>               

                  
                 <div style="display:none;" class="pop-outer" >
                  <div class="pop-inner">
                     <button class="close">X</button>
                     <c:if test="${!liee}">
                     <h4>Etes vous sûr de vouloir supprimer le pays <c:out value="${nom}"/> definitivement?</h4>

                    <form action="${pageContext.request.contextPath}/CataloguesServlet" method="GET">
                        <input type="hidden" name="whatsend" value="Voir la liste des pays" class="leftFirst" >  
                        <input type="submit" value="Non" class="leftFirst" >               
                    </form>
                    <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST" >
                        <input type="hidden" name="whatsend" value="SuppressionConfirmePays">  
                        <input name="element" value="${code}" type="hidden" >
                        <input type="submit" value="Oui" class="left">              
                    </form>
                    </c:if>
                    <c:if test="${liee}">
                     <p class="error">La base contient les races de chien originaires de <c:out value="${nom}"/>.</p>
                     <p>Pour supprimer le pays <c:out value="${nom}"/> changez d'abord le pays de provenance des races correspondantes</p>
                    <form action="${pageContext.request.contextPath}/CataloguesServlet" method="GET">
                        <input type="hidden" name="whatsend" value="Voir la liste des pays" class="leftFirst" >  
                       <input type="submit" value="Ok" class="leftFirst" >               
                    </form>
                    </c:if>
                    </div>
              </div>
    </body>
</html>