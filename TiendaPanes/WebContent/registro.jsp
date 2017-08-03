<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/stylesheetcss.css">
</head>
<body>
<form name="formulario" method="post" action="Login">
<div class="table-users">
   <div class="header">Registrar</div>
	<table style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
	  <tbody>
	    <tr>
	      <td style="vertical-align: top; width: 181px;">Nombre:<br>
	      </td>
	      <td style="vertical-align: top; width: 522px;"><input name="tNombre"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 181px;">Localidad:<br>
	      </td>
	      <td style="vertical-align: top; width: 522px;"><input name="tLocalidad"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 181px;">Fecha Nacimiento(YYYY-MM-DD):<br>
	      </td>
	      <td style="vertical-align: top; width: 522px;"><input name="tFecha"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 181px;">Usuario:<br>
	      </td>
	      <td style="vertical-align: top; width: 522px;"><input name="tUsuario"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 181px;">Contraseña:<br>
	      </td>
	      <td style="vertical-align: top; width: 522px;"><input name="tContrasenya"><br>
	      </td>
	    </tr>
	    <tr>
	      <td style="vertical-align: top; width: 181px;"><input name="action" type="submit" value="registrarUsuario"><br>
	      </td>
	      <td style="vertical-align: top; width: 522px;"><input name="action" type="submit" value="cancelarRegistro"><br>
	      </td>
	    </tr>
	  </tbody>
	</table>
</div>
</form>

  <br>

</body>
</html>