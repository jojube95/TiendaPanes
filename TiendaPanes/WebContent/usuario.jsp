<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/stylesheetcss.css">
</head>
<body>
	
	<div class="table-users">
   		<div class="header"><h4>Usuario: <label>${sessionScope.usuario.usuario}</label></h4></div>
	
	<table class="th" style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
		<tr>
	      <td style="vertical-align: top; width: 236px;"><form name="formulario1" method="post" action="Usuario">
	  	<button name="bComprar">Comprar</button><br>
   	  </form><br>
	      </td>
	     </tr>
	    
	    <tr>
	      <td style="vertical-align: top; width: 236px;"><form name="formulario2" method="get" action="Usuario">
	      <button name="bVerCompras">Ver compras</button><br>
      </form><br>
	      </td>
	     </tr>
	    
	    <tr>
	      <td style="vertical-align: top; width: 236px;"><form name="logOut" method="post" action="LogOut">
	      <button name="bLogOut">LogOut</button><br>
      </form><br>
	      </td>
	     </tr>
	  
	      
	   
      
      
	</table>
	</div>
	


  

</body>
</html>