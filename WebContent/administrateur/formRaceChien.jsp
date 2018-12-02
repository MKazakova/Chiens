<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ajouter une race de chien</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
     <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
     </header>  
      <form  method="post" action="<c:url value="/RaceChienServlet"/>"  enctype = "multipart/form-data">
       <h3>Fiche de race de chien</h3>
         <fieldset>        
                  <img src="${pageContext.request.contextPath}/style/dogbw.jpg" class="dog" /> 
                 <p>
                  <label for="nomRace">Nom de la race : <span class="requis">*</span></label>
                  <input type="text" id="nomRace" name="nomRace" value="<c:out value="${newrace.nom_race}"/>" 
                                                                          size="40" maxlength="50"/>
                  <span class="echec">${erreurs['nom_race']}</span>
                 </p>
                 
                 <p>
                  <label for="paysProvenance">Pays d'origine : <span class="requis">*</span></label>
                  <select name="paysProvenance" id="paysProvenance" tabindex="30">
                  <option value="rien"> </option>  
                   <c:forEach var="payslist" items="${payslist}">
                     <option value="${payslist.code_pays}"><c:out value="${payslist.nom_pays}"/></option>
                   </c:forEach>
                  </select>  
                  <span class="echec">${erreurs['pays']}</span>
                 </p>
                 
                 <p>
                  <label for="Aptitude">Aptitude : </label>
                  <select name="Aptitude" id="Aptitude" multiple size="5" tabindex="30">
                   <c:forEach var="aptitude" items="${aptitudelist}">
                     <option value="${aptitude.code_aptitude}"><c:out value="${aptitude.nom_aptitude}"/></option>
                   </c:forEach>
                  </select>  
                  <span class="info"> Appuyez sur Ctrl et cliquez pour choisir plusieurs filtres </span>
                 </p>
                 
                 <p>
                  <label for="facilite_education">Facilité d'éducation : </label>
                  <select name="facilite_education" id="facilite_education" tabindex="10">
                   <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"><c:out value="${i}"/></option>
                   </c:forEach>
                  </select>  
                 </p>
                 
                 <p>
                  <label for="activite">Activite : </label>
                  <select name="activite" id="activite" tabindex="10">
                   <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"><c:out value="${i}"/></option>
                   </c:forEach>
                  </select>  
                 </p>
                 
                 <p>
                  <label for="apte_garde">Aptitude à la garde : </label>
                  <select name="apte_garde" id="apte_garde" tabindex="10">
                   <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"><c:out value="${i}"/></option>
                   </c:forEach>
                  </select>  
                 </p>
                     
                 <p>
                  <label for="entente_enfants">Compatibilité avec les enfants :</label>
                  <select name="entente_enfants" id="entente_enfants" tabindex="10">
                   <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"><c:out value="${i}"/></option>
                   </c:forEach>
                  </select>  
                 </p>
                 
                 <p>
                  <label for="entente_animaux">Compatibilité avec les autres animaux : </label>
                  <select name="entente_animaux" id="entente_animaux" tabindex="10">
                   <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"><c:out value="${i}"/></option>
                   </c:forEach>
                  </select>  
                 </p>
                 
                 <p>
                  <label>Type de poils : <span class="requis">*</span></label>
                  <select name="typePoils" id="typePoils" multiple size="5" tabindex="30">
                   <c:forEach var="typePoilslist" items="${tplist}">
                     <option value="${typePoilslist.code_type_poils}"><c:out value="${typePoilslist.description_type_poils}"/></option>
                   </c:forEach>
                  </select>  
                  <span class="info"> Appuyez sur Ctrl et cliquez pour choisir plusieurs filtres </span>
                 </p>
                 
                 <p>
                  <label for="couleurChien">Couleurs possibles : </label>
                  <select name="couleurChien" id="couleurChien" multiple size="5" tabindex="30">
                   <c:forEach var="couleurlist" items="${cclist}">
                     <option value="${couleurlist.code_couleur}"><c:out value="${couleurlist.nom_couleur}"/></option>
                   </c:forEach>
                  </select>  
                  <span class="info"> Appuyez sur Ctrl et cliquez pour choisir plusieurs filtres </span>
                 </p>
                                  
                 <p>
                  <label for="poids">Poids : <span class="requis">*</span></label>
                  <input type="text" id="poidsMin" name="poidsMin" value="<c:out value="${newrace.poids_min}"/>" 
                                                                          size="6" maxlength="6"/>
                                                                          -
                  <input type="text" id="poidsMax" name="poidsMax" value="<c:out value="${newrace.poids_max}"/>" 
                                                                          size="6" maxlength="6"/> kg
                  <span class="echec">${erreurs['poids']}</span>
                 </p>
                 
                 <p>
                  <label for="taille">Taille : <span class="requis">*</span></label>
                  <input type="text" id="tailleMin" name="tailleMin" value="<c:out value="${newrace.taille_min}"/>" 
                                                                          size="6" maxlength="6"/>
                                                                           -
                  <input type="text" id="tailleMax" name="tailleMax" value="<c:out value="${newrace.taille_max}"/>" 
                                                                          size="6" maxlength="6"/> cm
                  <span class="echec">${erreurs['taille']}</span>
                 </p>
                 
                 <p>
                  <label for="description">Description</label>
                  <textarea name="description" id="description" cols="50" rows=5 maxlength="500"><c:out value="${newrace.description}"/></textarea>
                 </p>
                 
                 <p>
                  <label for="imageRace">Image</label>
                  <input type="file" id="imageRace" name="imageRace" class="inputstyle"/>
                  <span class="echec">${erreurs['image']}</span>
                 </p>
             </fieldset>
               <input name="whatsend" value="newRace" type="hidden" > 
               <input type="submit" value="Valider"  class="buttonLeftFirst"/>
               <input type="reset" value="Remettre à zéro" class="buttonLeft" />              
        </form>     
 </body>
</html>