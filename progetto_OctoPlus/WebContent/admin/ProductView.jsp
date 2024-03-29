 <%@page import="it.model.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Boolean isSomeoneLogged = (Boolean) request.getSession().getAttribute("isAdmin");
	if( (isSomeoneLogged == null) || !isSomeoneLogged ){
		response.sendRedirect(request.getContextPath()+"/login.jsp");	
		return;
	}
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	request.getSession().setAttribute("fromStore", Boolean.FALSE);
	if(products == null) {
		response.sendRedirect(request.getContextPath()+"/product?fromStore=false");	
		return;
	}
	ProductBean product = (ProductBean) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.model.ProductBean"%>

<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/productviewstyle.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script type="text/javascript">
		function confirmDelete(){
			alert("Cancellazione del prodotto in corso...");
		}
	</script>
	<title> Lista Prodotti </title>
</head>

<body>
	<jsp:include page="../header.jsp" flush="true"/>
	

	<br>	
	<br> <br>
	<table border="1" title="Tabella prodotti">
	<caption>Tabella prodotti</caption>
		<tr>
			<th>IdProdotto</th>
			<th>Categoria<a href="product?fromStore=false&sort=categoria" class="no-border-link">Ordina</a></th>
			<th>Nome <a href="product?fromStore=false&sort=nome" class="no-border-link">Ordina</a></th>
			<th>Prezzo</th>
			<th>Descrizione</th>
			<th>Foto</th>
			<th>Statistiche</th>
			<th>Taglie Disponibili</th>
			<th>Action</th>
		</tr>
		<%
			if (products != null && products.size() != 0) {
				Iterator<?> it = products.iterator();
				while (it.hasNext()) {
					ProductBean bean = (ProductBean) it.next();
		%>
		<tr>
			<td><%=bean.getCode()%></td>
			<td><%=bean.getCategoria()%></td>
			<td><%=bean.getNome()%></td>
			<td><%=bean.getPrice()%></td>
			<td><%=bean.getDescrizione()%></td>
			<td> <img src="./getPicture?id=<%=bean.getCode()%>" onerror="this.src='./images/nophoto.png'" alt="Immagine del prodotto" style="width:100px;height:100px"> </td>
			<td><%=bean.getStats()%></td>
			<td><p> <%=bean.getTaglie()%> </p> </td>
			<td>
				<a id="link" href="product?fromStore=false&action=delete&id=<%=bean.getCode()%>" onClick="confirmDelete();" class="delete-link">Cancella</a>
					<br>	<br>
				<a href="product?action=read&fromStore=modify&id=<%=bean.getCode()%>" class="edit-link">Modifica</a>
					<br>
		</td>
		</tr>
		<%
				}
			} else {
		%>
		<tr>
			<td colspan="6">No products available</td>
		</tr>
		<%
			}
		%>
	</table>
	
	<%
		if (product != null) {
	%>
	<table border="1" title="Tabella prodotti">
	<caption>Tabella prodotti</caption>
		<tr>
			<th>Code</th>
			<th>Name</th>
			<th>Description</th>
			<th>Price</th>
		</tr>
		<tr>
			<td><%=product.getCode()%></td>
			<td><%=product.getNome()%></td>
			<td><%=product.getDescrizione()%></td>
			<td><%=product.getPrice()%></td>
		</tr>
	</table>
	<%
		}
	%>

<br>

<br>
<h1>Upload photo:</h1>
	<div class="UploadPhoto">
<form action="UpdatePhoto" enctype="multipart/form-data" method="post">
	Nome file caricato:
	<select name="id">
<%
	if(products != null && products.size() > 0) {
		Iterator<?> it = products.iterator(); 
		while(it.hasNext()) {
			ProductBean item = (ProductBean)it.next();
%>	
		<option value="<%=item.getCode()%>"> cod: <%=item.getCode()%> nome: <%=item.getNome()%></option>
<%
		}
	}	
%>		
	</select>
	<br>
	<input class="file" type="file" name="talkPhoto" value="" maxlength="255">	
	<br>		
	<input type="submit" class="submit" value="Upload">      <input type="reset">
	<br>
</form>
	</div>
	<br>
	<br>
	<a href="./admin/insertProduct.jsp" class="no-border-link" > Inserisci un nuovo prodotto </a>
	<br>
	<a href="admin/UserView.jsp" class="no-border-link"> Pagina visualizzazione utenti </a>
	<br>
	<a href="admin/OrderView.jsp" class="no-border-link"> Pagina Ordini </a>
	<br>
	<br>
</body>
</html>