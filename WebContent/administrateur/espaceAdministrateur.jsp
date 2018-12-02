<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Espace Administrateur</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/page_accueil.css"/>" />
</head>
<body>
   <header>
    <nav class="dws-menu">
        <ul>
            <li><a href="#"><i class="fa fa-races"></i>Les Races de Chiens</a>
            <ul>
                  <li><a href="/chiens/ListRaceChienServlet?whatsend=toutesRaces">Voir toutes les races</a></li>
                  <li><a href="/chiens/RechercheChienServlet?whatsend=rechercheAdmin">Rechercher une race par parametres</a></li>
                  <li><a href="<c:url value="/RaceChienServlet?whatsend=form"/>">Ajouter une race de chiens</a></li>
            </ul>
            </li>
            <li><a href="#"><i class="fa fa-catalogues"></i>Les catalogues</a>
                <ul>
                  <li><a href="/chiens/CataloguesServlet?whatsend=Voir+la+liste+des+pays">Pays</a></li>
                  <li><a href="/chiens/CataloguesServlet?whatsend=Voir+la+liste+de+types+de+poils">Types de poils</a></li>
                  <li><a href="/chiens/CataloguesServlet?whatsend=Voir+la+liste+aptitudes">Aptitudes</a></li>
                  <li><a href="/chiens/CataloguesServlet?whatsend=Voir+la+liste+des+couleurs">Couleurs</a></li>
                </ul>
            </li>
            <li><a href="#"><i class="fa fa-elevage"></i>Les eleveurs</a>
                <ul>
                  <li><a href="/chiens/ElevageServlet?whatsend=ajouter">Ajouter une elevage</a></li>
                  <li><a href="/chiens/CataloguesServlet?whatsend=Voir+la+liste+elevages">Tous les elevages</a></li>
                </ul>
            </li>
            <li><a href="#"><i class="fa fa-espace"></i>Espace personnel</a>
                <ul>
                   <li><a href="/chiens/administrateur/changementMotPasseAdmin.jsp">Changer le mot de passe</a></li>
                </ul>
            </li>
            <li><a href="<c:url value="/user/page_d_accueil.jsp"/>" ><i class="fa fa-accueil"></i>La page principale</a></li>
        </ul>
    </nav>
</header>
</body>
</html>