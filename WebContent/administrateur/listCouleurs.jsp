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
                  <h1> La liste de couleurs  : </h1>
                  <table class="dogtable">
                    <tr>
                      <th><h3>Code de couleurs</h3></th>
                      <th><h3>Nom de couleur</h3></th>
                      <th><h3>Les races de chien</h3></th>
                     </tr> 
                    <c:forEach var="colors" items="${listcouleurs}">
                     <tr>
	                   <td><c:out value="${colors.code_couleur}"/></td>
	                   <td><c:out value="${colors.nom_couleur}"/></td>
	                   <th><c:out value="${races_couleurs[colors.code_couleur]}"/></th>
	                   <td class = "small1">
	                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST">
                        <input type="hidden" name="whatsend" value="delete">
                        <input name="element" value="${colors.code_couleur}" type="hidden" >
                        <input type="image" src="style/supprimer.png" class="delete">   
                       </form>          
                       </td>
	                  </tr>
                    </c:forEach>
                   </table>
                  <a href="<c:url value="/administrateur/formCouleur.jsp"/>" class="homePage"><input type="button" value="Ajouter une couleur" class="TabbuttonLeftFirst" ></a>               

                  
             <div style="display:none;" class="pop-outer" >
                 <div class="pop-inner">
                     <button class="close">X</button>
                     <h4>Etes vous sûr de vouloir supprimer la couleur <c:out value="${nom}"/> definitivement?</h4>

                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="GET">
                        <input type="hidden" name="whatsend" value="Voir la liste des couleurs" class="leftFirst" >  
                        <input type="submit" value="Non" class="leftFirst" >               
                   </form>
                   <form action="${pageContext.request.contextPath}/CataloguesServlet" method="POST" >
                        <input type="hidden" name="whatsend" value="SuppressionConfirme">  
                        <input name="element" value="${code}" type="hidden" >
                        <input type="submit" value="Oui" class="left">              
                   </form>
                  </div>
              </div>
    </body>
</html>