<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un type de poils</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/style/formstyle.css"/>" />
</head>
<body>
      <form  method="post" action="<c:url value="/TypePoilsServlet"/>">
        <h3> Ajouter un type de poils dans la base : </h3>
       
         <fieldset>     
                  
                 <p>
                 <label for="typePoils">Type de poils : <span class="requis">*</span></label>
                 <input type="text" id="typePoils" name="typePoils" value="" 
                                                                          size="30" maxlength="30"/>
                 <span class="echec">${message}</span>
                 </p>
                   
         </fieldset>
         
                <input name="whatsend" value="newPoils" type="hidden" > 
                <input type="submit" value="Valider"  class="buttonLeftFirst"/>
                <input type="reset" value="Remettre à zéro" class="buttonLeft" />
     </form>    
</body>
</html>