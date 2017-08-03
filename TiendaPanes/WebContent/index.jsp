<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/stylesheetcss.css">

<title>Insert title here</title>
</head>
<body>


   
<form name="formulario" method="post" action="Login">
<div class="table-users">
   <div class="header">Login</div>
	<table class="th" style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
	  <tbody>
	    <tr>
	      <td style="vertical-align: top; width: 236px;">Usuario:<br>
	      </td>
	      <td style="vertical-align: top; width: 467px;"><input name="tUsuario" id="tUsuario"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 236px;">Contraseña:<br>
	      </td>
	      <td style="vertical-align: top; width: 467px;"><input name="tContrasenya" id="tContrasenya" type="password"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 236px;"><input type="submit" name="action" value="logIn"><br>
	      </td>
	      <td style="vertical-align: top; width: 467px;"><input type="submit" name="action" value="registrar"><br>
	      </td>
	    </tr>
	  </tbody>
	</table>
	</div>
	
	  <br>
</form>
</body>
</html>