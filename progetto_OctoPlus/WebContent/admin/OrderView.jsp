<!DOCTYPE html>
<%
 	Boolean isSomeoneLogged = (Boolean) request.getSession().getAttribute("isAdmin");
	if( isSomeoneLogged == null ){
		response.sendRedirect("../login.jsp");	
		return;
	}
%>
<html>
<head>
  <title>Elenco Ordini</title>
  <link rel="stylesheet" type="text/css" href="./orderview.css">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="../scripts/orders.js"></script>
  <script src="../scripts/filteredsearch.js"></script>
  <script>
		$(document).ready(function(){
			dynamicOrdersView("<%=request.getContextPath()%>/OrderServlet");
		});	
  </script> 
  <jsp:include page="../header.jsp" flush="true"/>
</head>

<body>
	<nav class="topnav">
		<div class="dropdown">
		  <label for="categoria-select">Data inizio:</label>
		  <input type="date" id="dateinit" onChange="searchAndFilterOrders()">
		  <label for="categoria-select"> Data fine:</label>
		  <input type="date" id="dateend" onChange="searchAndFilterOrders()">
		</div>
	  
	  <form action="#" method="get" class="search-form">
    			<input type="text" id="search-input" onkeyup="searchAndFilterOrders()" placeholder="Inserisci l'email di un cliente per cercarlo..." class="search-input">
     			<button type="submit" onClick="searchAndFilterOrders()" class="search-button"><i class='bx bx-search'></i></button>
  	  </form>
	</nav>
	
  <h1>Elenco Ordini</h1>
  <div class="orders-container" id="orders">   
  </div>
  <div id="bottom">
  </div>
</body>
</html>
