<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Changer le mot de passe</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>     
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header> 
 <h3>Chengement du mot de passe administrateur : </h3>
 
      <form  method="post" action="<c:url value="/AccesAdminServlet"/>" >
      
         <fieldset>     
             
                 <p>
                 <label for="ancienMP">Le mot de passe actuel : <span class="requis">*</span></label>
                 <input type="password" id="ancienMP" name="ancienMP" value="" 
                                                                          size="30" maxlength="30"/>
                 <span class="echec">${message}</span>
                 </p>
                 
                 <p>
                 <label for="nouveauMP">Le nouveau mot de passe : <span class="requis">*</span></label>
                 <input type="password" id="nouveauMP" name="nouveauMP" value="" 
                                                                          size="30" maxlength="30"/>
                 <span class="echec">${message2}</span>
                 </p>
         </fieldset>
         
             <input name="whatsend" value="changerMotPasse" type="hidden" > 
                <input type="submit" value="Valider"  class="buttonLeftFirst"/>
                <input type="reset" value="Remettre à zéro" class="buttonLeft" />
              </form>

    </body>     
</body>
</html>