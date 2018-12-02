<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <h3>Fiche de la race</h3>
  <fieldset class="ficheRace">
    <table class="fiche">
          <tr>
          <td colspan="2"><h2><c:out value="${race.nom_race }" /></h2></td>
          <td rowspan="5"><img src="${pageContext.request.contextPath}/images/${race.image}" class="image" /></td>
          </tr>
          
          <tr>
          <td>Région d’origine</td>
          <td><c:out value="${pays}" /></td>
          </tr>
          
          <tr>
          <td>Activité</td>
          <td>
             <div class="prog${race.activite}">
                  <div>
                  </div>
             </div>
          </td>
          </tr>
          
          <tr>
          <td>Facilite d'education</td>
          <td>
             <div class="prog${race.facilite_education}">
                  <div>
                  </div>
             </div>
          </td>
          </tr>
                        
          <tr>
          <td>Aptitude à la garde</td>
          <td>
             <div class="prog${race.apte_garde}">
                  <div>
                  </div>
             </div>
          </td>
          </tr>
          
          <tr>
          <td>Entente avec les autres animaux</td>
          <td>
             <div class="prog${race.entente_autres_animaux}">
                  <div>
                  </div>
             </div>
          </td>
          <td rowspan="2" width="50"> <c:out value="${race.description }"/></td>
          </tr>
          <tr>
          <td>Entente avec les enfants</td>
          <td>
             <div class="prog${race.entente_enfants}">
                  <div>
                  </div>
             </div>
          </td>
          </tr>
          
          <tr>
          <td>Couleurs possibles</td>
          <td>
              <c:forEach var="couleurs" items="${couleurs}">
                     <c:if test="${count}">
                         <c:out value=", "/>
                     </c:if>
                     <c:out value="${couleurs}"/>
                     <c:set var="count" value="true" scope="page" />
              </c:forEach>
          </td>
          <td>Poids <c:out value="${race.poids_min}" />-<c:out value="${race.poids_max}" /> kg</td>
          </tr>
          
          <tr>
          <td>Type de poils : </td>
          <td >
               <c:forEach var="type_poils" items="${type_poils}" begin="0">
                     <c:if test="${count_tp}"><c:out value=", "/>
                     </c:if>
                     <c:out value="${type_poils}"/>
                     <c:set var="count_tp" value="true" scope="page" />
               </c:forEach>
          </td>
          <td>Taille <c:out value="${race.taille_min}" />-<c:out value="${race.taille_max}" /> cm</td>
          </tr>
          
          <tr>
          <td>Aptitudes : </td>
          <td colspan="2"> 
              <c:forEach var="aptitudes" items="${aptitudes}" begin="0">
                     <c:if test="${count_ap}"><c:out value=", "/>
                     </c:if>
                     <c:out value="${aptitudes}"/>
                     <c:set var="count_ap" value="true" scope="page" />
              </c:forEach>
          </td>
          </tr>
          
     </table>  
   </fieldset>                