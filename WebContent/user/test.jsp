<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rechercher une race de chien</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/test.css"/>" />
</head>
<body>
 <header>
    <c:import url="/user/header.jsp" />
 </header>  
     <h3>Ce test vous permettra de découvrir quelle race de chien vous convient le mieux ! </h3> 
     <form  method="post" action="<c:url value="/RechercheChienServlet"/>">
        <fieldset> 
              <img src="${pageContext.request.contextPath}/images/chien.png" class="dog"/>
               <c:if test ="${echec}">
                 <h2 class="echec">Veillez remplir tous les champs pour voir le résultat</h2>
               </c:if>
               
               <p>      
                 <label for="habitation">Dans quel type d'habitation résidez-vous ?</label>
               </p>
               
               <p>
                <select name="habitation" tabindex="30">
                   <option value="appartement">Un appartement en ville</option>  
                   <option value="maison">Une maison</option> 
                </select>  
               </p>
                
               <p>      
                 <label for="activite" <c:if test ="${erreur['activite_erreur']}"> class="echec"</c:if>> Aimez vous les longues promenades?</label>
               </p>
               
               <p>
                 <input type="radio" name="activite" value="oui">oui<br> 
                 <input type="radio" name="activite" value="non">non<br> 
               </p>
               
               <p>      
                 <label for="sport"<c:if test ="${erreur['sport_erreur']}"> class="echec"</c:if>>Êtes-vous en bonne condition physique ?(pratiquez une activité physique régulière?)</label>
               </p>
               
               <p>
                 <input type="radio" name="sport" value="oui">oui<br> 
                 <input type="radio" name="sport" value="non">non<br>  
               </p>
               
               <p>      
                 <label for="enfants"<c:if test ="${erreur['enfants_erreur']}"> class="echec"</c:if>>Avez vous des enfants moins de 14 ans?</label>
               </p>
               
               <p>
                 <input type="radio" name="enfants" value="oui">oui<br> 
                 <input type="radio" name="enfants" value="non">non<br>    
               </p>
                 
               <p>
                 <label for="animaux">Avez vous déjà des animaux de compagnie ?</label>
               </p>
               
               <p>
                 <select name="animaux" tabindex="30">
                   <option value="non">Non</option>  
                   <option value="chien">Oui, chien ou chat</option> 
                   <option value="rongeurs">Oui, les rongeurs</option> 
                   <option value="poissons">Oui, les poissons ou les tortues</option> 
                 </select>
               </p>
               
               <p>
                <label for="garde"<c:if test ="${erreur['garde_erreur']}"> class="echec"</c:if>>Est-ce necessaire qu'un chien soit capable de garder votre maison ?</label>
               </p>
               
               <p>
                 <input type="radio" name="garde" value="oui">oui<br> 
                 <input type="radio" name="garde" value="non">non<br>  
               </p>
               
               <p>
                <label for="voyages">Envisagez vous de voyager avec votre chien ?</label>
               </p>
               
               <p>
                 <select name="voyages" tabindex="30">
                   <option value="non">Non</option>  
                   <option value="voiture">Oui, en voiture</option> 
                   <option value="avion">Oui, en train ou en avion</option> 
                 </select>  
               </p>
               
           </fieldset>
                <input name="whatsend" value="test" type="hidden" > 
                <input type="submit" value="Valider"  class="buttonLeftFirst"/>
                <input type="reset" value="Remettre à zéro" class="buttonLeft" />              
        </form>
    </body>     
 </body>
</html>