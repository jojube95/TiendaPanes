<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/stylesheetcss.css">
</head>
<div class="info">
	Compra con id = <label>${sessionScope.compra.id}</label>
	Tienda: <label>${sessionScope.compra.tienda.nombre}</label>
</div>

<div class="table-users">
   <div class="header">Panes</div>
<table style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="vertical-align: top;">Nombre<br>
      </td>
      <td style="vertical-align: top;">Tipo<br>
      </td>
      <td style="vertical-align: top;">Cantidad<br>
      </td>
      <td style="vertical-align: top;">Precio<br>
      </td>
    </tr>
    
    <c:forEach var ="tempPan" items= "${sessionScope.compra.panes}">
    	<tr>
	      <td style="vertical-align: top;">${tempPan.pan.nombre}<br>
	      </td>
	      <td style="vertical-align: top;">${tempPan.pan.tipo}<br>
	      </td>
	      <td style="vertical-align: top;">${tempPan.cant}<br>
	      </td>
	      <td style="vertical-align: top;">${tempPan.precio}<br>
	      </td>
    	</tr>
    </c:forEach>
    
  </tbody>
</table>
<input type="button" value="Volver" onclick="window.location.href='compras.jsp'"/>
</div>
<div class="table-users">
<table style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
	<tr>
		<td>
		Precio total:
		</td>
		<td>
		<label>${sessionScope.compra.precio}</label>
		</td>
	</tr>
</table>
</div>
</body>
</html>