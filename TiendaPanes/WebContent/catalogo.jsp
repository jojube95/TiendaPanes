<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/JavaScript" src="javascripts/jquery-3.2.1.js"></script>
<link rel="stylesheet" type="text/css" href="css/stylesheetcss.css">
 

 
</head>
<body>
  

<div class="table-users">
   <div class="header">Catálogo</div>
<table style="text-align: left; width: 732px;" border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="vertical-align: top; width: 288px;">Nombre<br>
      </td>
      <td style="vertical-align: top; width: 201px;">Tipo<br>
      </td>
      <td style="vertical-align: top; width: 93px;">Precio<br>
      </td>
      <td style="vertical-align: top; width: 50px">Cantidad<br>
      </td>
      <td style="vertical-align: top; width: 56px;">Añadir<br>
      </td>
    </tr>
    
    <c:forEach var ="tempPan" items= "${sessionScope.CATALOGO}" varStatus="loop">
    	<!-- Link para cada compra con su campo clave -->
		<c:url var="linkAnyadir" value="VerCompras">
			<c:param name="id" value="${tempPan.idPanesTienda}"></c:param>
		</c:url>
			<input type="hidden" id="idPan${loop.index}" name="idPan${loop.index}" value="${tempPan.idPanesTienda}">
			<tr>
		      <td id="nombre${loop.index}" style="vertical-align: top; width: 288px;">${tempPan.pan.nombre}
		      </td>
		      <td id="tipo${loop.index}" style="vertical-align: top; width: 201px;">${tempPan.pan.tipo}
		      </td>
		      <td id="precio${loop.index}" style="vertical-align: top; width: 93px;">${tempPan.pan.precio}
		      </td>
		      <td style="vertical-align: top; width: 50px"><input id="cantidad${loop.index}" name="tCantidad">
		      </td>
		      <td style="vertical-align: top; width: 56px;"><button type="button" id="add${loop.index}">Agregar</button>
		      </td>
		    </tr>
		 <script type="text/JavaScript">
		 	var i = 0;
		 	var precioCompra = 0;
			 $("#add"+'${loop.index}').on("click", function(){
				
				var cant = parseFloat($("#cantidad"+'${loop.index}').val().replace(",", "."));
				var precio = parseFloat($("#precio"+'${loop.index}').html().replace(",", "."));
			 	var preciototal = cant*precio;
			 	i++;
			 	
			 	$('<input>').attr({
			 	    type: 'hidden',
			 	    name: "p"+i,
			 	    value: $("#idPan"+'${loop.index}').val()
			 	}).appendTo('form');
			 	
			 	$('<input>').attr({
			 	    type: 'hidden',
			 	    name: "c"+i,
			 	    value: $("#cantidad"+'${loop.index}').val()
			 	}).appendTo('form');
			 	
			 	precioCompra+=preciototal;
			 	$("#label").text(precioCompra);
			 	
				 $('#test > tbody:last-child').append('<tr><td>'+$("#nombre"+'${loop.index}').html()+'</td><td>'+$("#tipo"+'${loop.index}').html()+'</td><td>'+$("#cantidad"+'${loop.index}').val()+'</td><td>'+preciototal+'</td></tr>');
				});
 </script>
	</c:forEach>
  </tbody>
</table>
</div>
<br>

<form name="formulario" method="post" action="Comprar">

<div class="table-users">
   <div class="header">Carrito</div>
<table id="test" style="text-align: left; width: 471px;" border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="vertical-align: top; width: 103px;">Nombre<br>
      </td>
      <td style="vertical-align: top; width: 57px;">Tipo<br>
      </td>
      <td style="vertical-align: top; width: 163px;">Cantidad<br>
      </td>
      <td style="vertical-align: top; width: 114px;">Precio<br>
      </td>
    </tr>
  </tbody>
</table>
</div>
<br>

<div class="table-users">
<table id="precioTotal" style="text-align: left; width: 471px;" border="1" cellpadding="2" cellspacing="2">
	<tr>
		<td>
			Precio total:
		</td>
		<td>
			<label style="align-center" id="label"></label><br>
		</td>
	</tr>
</table>
</div>


<br>
<div class="table-users">
<table style="text-align: left; width: 122px;" border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="vertical-align: top; width: 42px;"><input type="submit" name="boton" value="aceptar" /><br>
      </td>
      <td style="vertical-align: top; width: 54px;"><input type="submit" name="boton" value="cancelar" /><br>
      </td>
    </tr>
  </tbody>
</table>
</div>
</form>
<br>


</body>
</html>