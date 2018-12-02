<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Page d'accueil</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/newstyle.css"/>" />
</head>
<body> 

<header>
   <c:import url="/user/header.jsp" />
</header> 

<div id="container2"> 
	<div class="tabs"> 
		<input id="tab1" type="radio" name="tabs" checked> 
		<label for="tab1">La santé du chien</label> 
		<input id="tab2" type="radio" name="tabs"> 
		<label for="tab2">Entretenir son chien</label> 
		<input id="tab3" type="radio" name="tabs"> 
		<label for="tab3">Education</label> 
		<input id="tab4" type="radio" name="tabs"> 
 	 	<label for="tab4">Alimentation</label> 
		<section id="content1"> 
    		Les articles sur la santé des chiens
		</section>  
		<section id="content2"> 
    		Les articles sur entretien des chiens
		</section>  
		<section id="content3"> 
   			Les articles sur education
		</section>  
		<section id="content4">
    		Les articles sur alimentation
		</section>  
	</div> 
</div>
</body>
</html>