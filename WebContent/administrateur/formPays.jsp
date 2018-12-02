<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un pays</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>     
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header> 
 <h3>Ajouter un pays dans la base : </h3>
 
      <form  method="post" action="<c:url value="/PaysServlet"/>" >
      
         <fieldset>     
             
                 <p>
                 <label for="nomPays">Nom de pays : <span class="requis">*</span></label>
                 <input type="text" id="nomPays" name="nomPays" value="" 
                                                                          size="30" maxlength="30"/>
                 <span class="echec">${message}</span>
                 </p>
                
         </fieldset>
         
             <input name="whatsend" value="newPays" type="hidden" > 
                <input type="submit" value="Valider"  class="buttonLeftFirst"/>
                <input type="reset" value="Remettre à zéro" class="buttonLeft" />
              </form>

    </body>     
</body>
</html>