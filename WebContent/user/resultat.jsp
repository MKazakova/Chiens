<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

   <c:if test="${trouve}"> <h3>Les races de chiens correspondantes à votre recherche</h3></c:if>
 
 <div class="resultatGlobal">
   <c:forEach var="races" items="${races}">  
     <div onclick="location.href='#zatemnenie${races.code_race}';" class="resultatReceherche"> 
       <img src="/chiens/images/${races.image}" class="imageRecherche" />
       <h4><c:out value="${races.nom_race}"/></h4>
     </div>
     <div id="zatemnenie${races.code_race}" class="zatemnenie">
       <div id="okno" class="okno">
          <table>
               <tr>
                <td colspan="2"><h2><c:out value="${races.nom_race }" /></h2></td>
                <td rowspan="5"><img src="${pageContext.request.contextPath}/images/${races.image}" class="imageDedans" /></td>
               </tr>
               
                <tr>
                <td>Région d’origine</td>
                <td><c:out value="${pays[races.pays_provenance]}" /></td>
                </tr>
                
                <tr>
                <td>Activité</td>
                <td>
                 <div class="prog${races.activite}">
                    <div>
                    </div>
                
                 </div>
                </td>
                </tr>
                
                <tr>
                <td>Facilite d'education</td>
                <td>
                    <div class="prog${races.facilite_education}">
                       <div>
                       </div>
                    </div>
                </td>
                </tr> 
                             
                <tr>
                <td>Aptitude à la garde</td>
                <td>
                    <div class="prog${races.apte_garde}">
                       <div>
                       </div>
                    </div>
                </td>
                </tr>
                
                <tr>
                <td>Entente avec les autres animaux</td>
                <td>
                   <div class="prog${races.entente_autres_animaux}">
                      <div>
                      </div>
                   </div>
               </td>
               <td rowspan="2" width="50"> <c:out value="${races.description }"/></td>
               </tr>
               
               <tr>
               <td>Entente avec les enfants</td>
               <td>
                  <div class="prog${races.entente_enfants}">
                     <div>
                     </div>
                  </div>
               </td>
               </tr>
               
               <tr>
               <td>Couleurs possibles</td>
               <td>
                     <c:out value="${couleurs[races.code_race]}"/>
               </td>
               <td>Poids <c:out value="${races.poids_min}" />-<c:out value="${races.poids_max}" /> kg</td>
               </tr>
               
               <tr>
               <td>Type de poils : </td>
               <td >
                <c:out value="${type_poils[races.code_race]}"/>
               </td>
               <td>Taille <c:out value="${races.taille_min}" />-<c:out value="${races.taille_max}" /> cm</td>
               </tr>
               
               <tr>
               <td>Aptitudes : </td>
               <td colspan="2"> 
                     <c:out value="${aptitudes[races.code_race]}"/>
                </td>
                </tr>
                
            </table>                      
         <a href="#" class="close">Fermer</a>
        </div>
      </div>
    </c:forEach> 
  </div>