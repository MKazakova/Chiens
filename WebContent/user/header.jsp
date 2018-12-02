<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<div class="container">
           <a href="<c:url value="/user/page_d_accueil.jsp"/>" class="dom"><img src="${pageContext.request.contextPath}/style/dom.png"></a>
    <c:if test="${administrateur}">
       <a href="<c:url value="/administrateur/espaceAdministrateur.jsp"/>" class="liens">Espace Adminitrateur</a><br>
       <a href="<c:url value="/AccesAdminServlet?whatsend=deconnection"/>" class="liens">Déconnecter</a>
    </c:if>
    <c:if test="${!administrateur}"><span class="nonvalide">Espace Adminitrateur</span><br>
                                    <a href="<c:url value="/AccesAdminServlet?whatsend=connection"/>" class="liens">Se connecter en tant qu'administrateur</a>
    </c:if>

    
    <c:if test="${admin}">
      <div id = "sombre">
        <form action="/chiens/AccesAdminServlet" method="POST">
               <fieldset class="adAcces">
                    <p>Se connecter en tant qu'administrateur : </p>                
                    <label for="motpasse"> Mot de passe : <span class="requis">*</span></label>
                    <input type="password" id="motpasse" name="motpasse" value="" size="25" maxlength="30" />
                    <span class="error"> <c:out value="${message}" /></span>   <br>        
                    <input name="whatsend" value="autorisation" type="hidden" > 
                    <input type="submit" value="Valider" class="bouton"/>
                    <a href="<c:url value="/user/page_d_accueil.jsp"/>" ><input type="button" value="fermer" class="bouton"/> </a>
               </fieldset>  
            </form>
      </div>
    </c:if> 
    
 <div class = "title">Races de chiens</div>
 <ul class = "menu">
  <li><a href = "<c:url value="/user/articles.jsp"/>" >Articles sur les chiens</a></li>
  <li><a href = "<c:url value="/user/test.jsp"/>">Quelle race de chien choisir ?</a></li>
  <li><a href = "/chiens/ListRaceChienServlet?whatsend=racesParAlphabet">Toutes les races de chiens par alphabet</a></li>
  <li><a href = "/chiens/RechercheChienServlet?whatsend=rechercheUser">Recherche avancée de races de chiens</a></li>
  <li><a href = "<c:url value="/ElevageServlet?whatsend=elevagesDeRace&&code=0"/>">Voir les élévages</a></li>
 </ul>
</div>