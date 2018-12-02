<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <form  method="post" action="<c:url value="/RechercheChienServlet"/>">
      <h3>Recherche avancée d'une race de chien</h3>
       <fieldset>  
                 <p>      
                 <label for="paysProvenance">Pays d'origine : <span class="requis">*</span></label>
                 <select name="paysProvenance" id="paysProvenance" tabindex="30">
                  <option value="rien" selected="selected">Non precisé</option>  
                  <c:forEach var="payslist" items="${payslist}">
                     <option value="${payslist.code_pays}"><c:out value="${payslist.nom_pays}"/></option>
                  </c:forEach>
                 </select>  
                 </p>
                 
                 <fieldset class="field">
                   <legend>Les aptitudes :</legend>
                    <c:forEach var="aptitude" items="${aptitudelist}">   
                      <p>               
                      <label for="garde" class="check"><input type="checkbox" id="${aptitude.nom_aptitude}" name="aptitude" value="${aptitude.code_aptitude}" class="imputcheck">
                      <c:out value="${aptitude.nom_aptitude}"/></label>
                      </p>
                    </c:forEach>
                 </fieldset>
                 
                 <p>      
                 <label for="activite">Activité :</label> 
                   <select name="activite">
                    <option value="rien" selected="selected">Non precisé</option>  
                    <option value="beaucoup">Très actif</option>
                    <option value="peu">Peu actif</option>
                   </select>  
                 </p>
                 
                 <fieldset class="field">
                   <legend>Autres caracteristiques :</legend>  
                   <p>               
                   <label for="garde" class="check"><input type="checkbox" name="caracterisiques" value="garde" class="imputcheck">
                      Apte à la garde 
                   </label>
                   <label for="education" class="check"><input type="checkbox" name="caracterisiques" value="education" class="imputcheck">
                      Facile à éduquer 
                   </label>
                   <label for="enfants" class="check"><input type="checkbox" name="caracterisiques" value="enfants" class="imputcheck">
                      S'entend bien avec les enfants 
                   </label>
                   <label for="animaux" class="check"><input type="checkbox" name="caracterisiques" value="animaux" class="imputcheck">
                      S'entend bien avec les autres animaux 
                   </label>
                   </p>
                 </fieldset> 
             
                 <p>
                 <label>Type de poils : </label>
                 <select name="typePoils" id="typePoils" tabindex="30">
                  <option value="rien" selected="selected">Non precisé</option>  
                  <c:forEach var="typePoilslist" items="${tplist}">
                     <option value="${typePoilslist.code_type_poils}"><c:out value="${typePoilslist.description_type_poils}"/></option>
                  </c:forEach>
                 </select>  
                 </p>
                 
                 <p>
                 <label for="couleurChien">Couleur : </label>
                 <select name="couleurChien" id="couleurChien" tabindex="30">
                  <option value="rien" selected="selected">Non precisé</option>  
                  <c:forEach var="couleurlist" items="${cclist}">
                     <option value="${couleurlist.code_couleur}"><c:out value="${couleurlist.nom_couleur}"/></option>
                  </c:forEach>
                 </select>  
                 </p>
                                  
                 <label for="taille">Taille : <span class="requis">*</span></label>
                 <select name="taille">
                   <option value="rien" selected="selected">Non precisé</option>  
                   <option value="grand">Grande</option>
                   <option value="moyen">Moyenne</option>
                   <option value="petit">Petite</option>
                 </select>
         </fieldset>
           <input name="whatsend" value="newRace" type="hidden" > 
           <input type="submit" value="Valider"  class="buttonLeftFirst"/>
           <input type="reset" value="Remettre à zéro" class="buttonLeft" />              
   </form>