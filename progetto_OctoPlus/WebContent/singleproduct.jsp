<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%
 	ProductBean bean = (ProductBean) request.getAttribute("product");
	
 	SizesBean sizes = (SizesBean) bean.getTaglie();
 	Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
 	Boolean isSomeoneLogged = (Boolean) request.getSession().getAttribute("isAdmin");
 	boolean nondisponibile = false;
%>
<!DOCTYPE html>
<html lang="it">
<%@ page import="java.util.*,it.model.ProductBean, it.model.SizesBean, it.unisa.DaoDataSource"%>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/product.css">
<title>Visualizzazione prodotto</title>
<script> 
	function getSizeValue(){
		var selectedValue = document.getElementById("size").value;
		console.log(selectedValue);	
		return selectedValue;
	}
	function getQuantityValue(){
		var selectedValue = document.getElementById("quantity").value;
		console.log(selectedValue);	
		return selectedValue;
	}
	
	function addValuesToLink(){
		var link = document.getElementById("link");	
		link.href=link.href.substring(0, 74);
		console.log(link.href+="&id=");
		console.log(link.href+=document.getElementById("id").textContent );
		console.log(link.href+="&qnt0=");
		console.log(link.href+=document.getElementById("quantity").value);
		console.log(link.href+="&sz0=");
		console.log(link.href+=document.getElementById("size").value);
	}
</script>
	<jsp:include page="./header.jsp" flush="true"/>
</head>
<body>
	<div class="flex-box">
		<div class="left">
			<div class="img">
				<img src="./getPicture?id=<%=bean.getCode()%>" onerror="this.src='./images/nophoto.png'" alt="Immagine prodotto">
			</div>
		</div>
		<div class="right">
			<div class="url"> 
				<a href="index.jsp"> Home </a> > 
				<a href="store.jsp"> Store </a> > 
				<a href="#"> Prodotto </a>
			</div>
			<div id="id" style="display:none;"><%=bean.getCode()%></div>
			<div class="pname"> <%=bean.getNome()%> </div>
			<br>
			<h3 style="padding-left: 20px;"> <%=bean.getCategoria()%> </h3>
			<br>
			<div class="price" style="padding-left: 20px;"> <%=bean.getPrice()%> &euro;</div> 
			<% if( (sizes.getQuantitaM() == 0 && sizes.getQuantitaM() == 0 && sizes.getQuantitaXL() == 0 && sizes.getQuantitaXXL() == 0) || sizes == null) {
				nondisponibile = true;%>
				<p style="color: red"> Prodotto momentaneamente non disponibile! </p>
			<% } else {%>
			<div style="padding-left: 20px;">
			Seleziona taglia:
			<select id="size" onChange="getSizeValue();">	
				<% if( sizes.getQuantitaM() > 0 ){ %>
					<option value="M"> M </option>
				<%} %>	
				<% if( sizes.getQuantitaL() >0 ){ %>
					<option value="L"> L </option>
				<%} %>	
				<% if( sizes.getQuantitaXL()>0 ){ %>
					<option value="XL"> XL </option>
				<%} %>	
				<% if( sizes.getQuantitaXXL()>0 ){ %>
					<option value="XXL"> XXL </option>
			</select>
			</div>	
				<%} }%>	
			<br>
			<br>
			<% if( nondisponibile ){ %>
				<p style="color: red; text-align: justify;"> Acquisto momentaneamente non disponibile! Il prodotto che stai
									   cercando attualmente non � disponibile per l'acquisto, riprova
									   pi� tardi!</p>
			<%} else { %>	
				<div class="quantiy" style="padding-left: 20px;">	
					<p> Quantita': 
					<input id="quantity" type="number" min="1" max="100" value="1" onChange="getQuantityValue()">
					</p>
				</div>
				<br>
				<div class="btn-box">
					<a href="cart.jsp?id=<%=bean.getCode()%>" class="cart-btn"> Aggiungi al Carrello </a>
					<a id="link" onClick="addValuesToLink();" href="OrderControl?action=read&fromStore=get2&id=<%=bean.getCode()%>" class="buy-btn"> Compra adesso </a>
				</div>
				<%	
					if( isAdmin == null );
					else if( isAdmin == true ){ %>
						<a href="product?action=read&fromStore=modify&id=<%=bean.getCode()%>" class="modify-btn" onClick="functionAlert()"> Modifica </a>
				<%	} 
				}	%>	
			<br>
			
			<h3 style="color: red;">
			<%
				List<String> errors = (List<String>) request.getAttribute("errors");
				if (errors != null){
					for (String error: errors){ %>
						<%=error %> <br>		
					<%
					}
				}
			%>
			</h3>
			<div style="padding: 20px;">
			<h3> Dettagli prodotto</h3>
			<p id="description" style="text-align: left;">
			<%=bean.getDescrizione()%>		
			</p>
			<h3> Statistiche </h3>
			<p id="stats" style="text-align: right;">
			<%=bean.getStats()%>
			</p>
			</div>
		</div>
	</div>
	<br>
	<br>
	<% if( errors != null)
			errors.clear();
	%>	
	<jsp:include page="footer.jsp" flush="true"/>
</body>
</html>