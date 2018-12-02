<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit de race</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>

  <header>
    <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="homePage">Espace Adminitrateur</a>
  </header>
  
  <form  method="post" action="<c:url value="/EditRaceChienServlet"/>"  enctype = "multipart/form-data">
   <fieldset class="ficheRace">
    <table>
             <tr>
                <td colspan="2"><input type="text" id="nomRace" name="nomRace" value="<c:out value="${race.nom_race}"/>" 
                                                                          size="40" maxlength="50"/>
                </td>
                <td rowspan="5"><img src="${pageContext.request.contextPath}/images/${race.image}" class="image" />
                 <input type="hidden" id="imageRace" name="imageRace" value="${race.image}"/>
                 <input type="file" id="imageRace" name="imageRace" class="inputstyle" value="${race.image}"/>
                </td>
             </tr>
             <tr>
               <td>Région d'origine</td>
               <td>
                <select name="paysProvenance" id="paysProvenance" tabindex="30">
                 <option value="${race.pays_provenance}"><c:out value="${pays}" /></option>  
                      <c:forEach var="payslist" items="${payslist}">
                      <option value="${payslist.code_pays}">
                        <c:out value="${payslist.nom_pays}"/> 
                      </option>
                      </c:forEach>
                 </select>
                </td>
              </tr>
              <tr>
                <td>Activité</td>
                <td>
                 <select name="activite" id="activite" tabindex="10">
                  <c:forEach var="i" begin="1" end="10">
                      <option value="${i}" <c:if test="${i == race.activite}"> selected="selected" </c:if>>
                      
                      <c:out value="${i}"/>
                      </option>
                  </c:forEach>
                 </select>  
                </td>
               </tr>
               <tr>
                <td>Facilite d'education</td>
                <td>
                  <select name="facilite_education" id="facilite_education" tabindex="10">
                    <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"<c:if test="${i == race.facilite_education}"> selected="selected" </c:if>><c:out value="${i}"/></option>
                    </c:forEach>
                  </select>
                </td>
               </tr>              
               <tr>
                <td>Aptitude à la garde</td>
                <td><select name="apte_garde" id="apte_garde" tabindex="10">
                 <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"<c:if test="${i == race.apte_garde}"> selected="selected" </c:if>><c:out value="${i}"/></option>
                 </c:forEach>
                 </select>  
                </td>
               </tr>
               <tr><td>Entente avec les autres animaux</td>
                <td>
                    <select name="entente_animaux" id="entente_animaux" tabindex="10">
                    <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"<c:if test="${i == race.entente_autres_animaux}"> selected="selected" </c:if>><c:out value="${i}"/></option>
                    </c:forEach>
                 </select>
               </td>
                <td rowspan="2" maxwidth="50"> <textarea name="description" id="description" cols="50" rows=5 maxlength="500"><c:out value="${race.description}"/></textarea>
                </td>
               </tr>
               <tr><td>Entente avec les enfants</td><td>
                    <select name="entente_enfants" id="entente_enfants" tabindex="10">
                     <c:forEach var="i" begin="1" end="10">
                      <option value="${i}"<c:if test="${i == race.entente_enfants}"> selected="selected" </c:if>><c:out value="${i}"/></option>
                     </c:forEach>
                    </select>
                   </td>
                </tr>
                <tr>
                  <td>Couleurs possibles</td>
                  <td>
                   <select name="couleurChien" id="couleurChien" multiple size="5" tabindex="30">
                     <c:forEach var="couleurs" items="${couleurspresents}">
                       <option value="${couleurs.code_couleur}" selected="selected"><c:out value="${couleurs.nom_couleur}"/></option>
                     </c:forEach>
                     <c:forEach var="couleursab" items="${couleursabsents}">
                       <option value="${couleursab.code_couleur}"><c:out value="${couleursab.nom_couleur}"/></option>
                     </c:forEach>
                   </select> 
                 </td>
                 <td>Poids <input type="text" id="poidsMin" name="poidsMin" value="<c:out value="${race.poids_min}"/>" 
                                                                          size="6" maxlength="6"/>
                                                                          -
                          <input type="text" id="poidsMax" name="poidsMax" value="<c:out value="${race.poids_max}"/>" 
                                                                          size="6" maxlength="6"/> kg
                 </td>
               </tr>
               <tr>
                <td>Type de poils : </td>
                <td >
                 <select name="typePoils" id="typePoils" multiple size="5" tabindex="30">
                  <c:forEach var="type_poils" items="${type_poilspresent}">
                     <option value="${type_poils.code_type_poils}" selected="selected"><c:out value="${type_poils.description_type_poils}"/></option>
                  </c:forEach>
                  <c:forEach var="type_poilsabs" items="${type_poilsabsent}">
                     <option value="${type_poilsabs.code_type_poils}"><c:out value="${type_poilsabs.description_type_poils}"/></option>
                  </c:forEach>
                 </select>  
                </td>
                <td>Taille <input type="text" id="tailleMin" name="tailleMin" value="<c:out value="${race.taille_min}"/>" 
                                                                          size="6" maxlength="6"/>
                                                                          -
                           <input type="text" id="tailleMax" name="tailleMax" value="<c:out value="${race.taille_max}"/>" 
                                                                          size="6" maxlength="6"/>cm</td>
               </tr>
               <tr>
                 <td>Aptitudes : </td>
                 <td colspan="2">
                  <select name="Aptitude" id="Aptitude" multiple size="5" tabindex="30">
                  <c:forEach var="aptitudes" items="${aptitudespresents}">
                     <option value="${aptitudes.code_aptitude}" selected="selected"><c:out value="${aptitudes.nom_aptitude}"/></option>
                  </c:forEach>
                  <c:forEach var="aptitudesabs" items="${aptitudesabsents}">
                     <option value="${aptitudesabs.code_aptitude}"><c:out value="${aptitudesabs.nom_aptitude}"/></option>
                  </c:forEach>
                 </select> 
                 </td>
                </tr>
          </table>  
      </fieldset> 
    <input name="element" value="${race.code_race}" type="hidden" >
    <input type="submit" value="Appliquer les changements"  class="buttonLeftFirst"/>       
   </form>                       
    <a href="<c:url value="/ListRaceChienServlet"/>" ><input type="button" value="Annuler" class="buttonLeft"/> </a>
</body>
</html>