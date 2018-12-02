<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un élévage</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>     
  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header> 
 <h3>Ajouter un élévage dans la base : </h3>
 
      <form  method="post" action="<c:url value="/ElevageServlet"/>" >
      
         <fieldset>     
                 <p>
                 <label for="nomElevage">Nom d'elevage : <span class="requis">*</span></label>
                 <input type="text" id="nomElevage" name="nomElevage" value="${elevage.nomElevage}" 
                                                                          size="30" maxlength="30"/>
                 <span class="echec">${erreurs['nom']}</span>                                                          
                 </p>
                 
                 <p>
                 <label for="telephone">Numero de telephone : </label>
                 <input type="text" id="telephone" name="telephone" value="${elevage.telephone}" 
                                                                          size="30" maxlength="30"/>
                 <span class="echec">${erreurs['telephone']}</span>                                                         
                 </p>
                 
                 <p>
                 <label for="adresse">Adresse :</label>
                 <textarea name="adresse" id="adresse" cols="25" rows=3 maxlength="80"><c:out value="${elevage.adresse}"/></textarea>
                 </p>
                 
                 <p>
                 <label for="email">Adresse email :</label>
                 <input type="email" id="email" name="email" value="<c:out value="${elevage.email}"/>" 
                                                                          size="30" maxlength="30" class="inputstyle"/>
                 <span class="echec">${erreurs['email']}</span>
                 </p>
                 
                 <p>
                 <label for="siteInternet">Site internet :</label>
                 <input type="text" id="siteInternet" name="siteInternet" value="<c:out value="${elevage.siteInternet}"/>" 
                                                                          size="30" maxlength="30" class="inputstyle"/>
                 <span class="echec">${erreurs['siteInternet']}</span>
                 </p>
                 
                 <p>
                 <label for="races">Races Elevées: <span class="requis">*</span></label>
                 <select name="races" id="races" multiple size="5" tabindex="30">
                  <c:forEach var="races" items="${races}">
                    <option value="${races.code_race}"><c:out value="${races.nom_race}"/></option>
                  </c:forEach>
                 </select>  
                 <span class="info"> Appuyez sur Ctrl et cliquez pour choisir plusieurs filtres </span>
                 </p>
                 
         </fieldset>
         
                <input name="whatsend" value="creerElevage" type="hidden" > 
                <input type="submit" value="Valider"  class="buttonLeftFirst"/>
                <input type="reset" value="Remettre à zéro" class="buttonLeft" />
              </form>

    </body>     
</body>
</html>