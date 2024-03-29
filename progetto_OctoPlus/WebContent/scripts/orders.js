function dynamicOrdersView(url){
	$.ajax({
		url : url,
		type: 'GET',
		contentType : 'application/json; charset=utf-8'
	}).done((response) => {
		response = JSON.parse(response);
		let contenutoHTML = "";
		let contenutoHTML2 = "";

		if( response.length == 0 ){
			contenutoHTML +=  "<span>Nessun ordine disponibile</span>";
		} else {
			 for(const bean of response){
				 	contenutoHTML += "<div class=\"order-card\">";
					contenutoHTML +=     "<h2 id=\"idOrdine\"> Ordine:#"+bean.idOrdine+"</h2>";
					contenutoHTML +=     "<p>Cliente:<div class=\"email\">"+bean.idUtente+"</div></p>";
					contenutoHTML +=     "<p>Data:<div class=\"data\">"+bean.data+"</div></p>";
					contenutoHTML +=     "<p>Stato:"+bean.stato+"</p>";
					contenutoHTML +=     "<p>Stato:"+bean.indirizzo+"</p>";
					contenutoHTML +=     "<p>Totale:"+bean.prezzototale.toFixed(2)+"&euro;</p>";
					contenutoHTML +=     "<p>Metodo di pagamento: Carta di credito</p>";
					contenutoHTML += "	<p> <button id='" + bean.idOrdine + "' onclick=eliminaRiga(this)> Per rimuovere l'ordine clicca qui!</button>";
					contenutoHTML += "</div>";
		      }
		} 
		$("#orders").empty();
		$("#orders").append(contenutoHTML);
		$("#bottom").empty();
		$("#bottom").append(contenutoHTML2);
		visualizeFirstXX();
	});
} 

function dynamicOrdersUser(url){
	$.ajax({
		url : url,
		type: 'GET',
		contentType : 'application/json; charset=utf-8'
	}).done((response) => {
		response = JSON.parse(response);
		let contenutoHTML = "";
		
		contenutoHTML += "<h2 class=\"title\">I tuoi ordini</h2>";
        contenutoHTML += "<table>";
            		
		if( response.length == 0 ){
			contenutoHTML +=  "<span>Nessun ordine disponibile</span>";
		} else {
				for(const bean of response){
                	contenutoHTML += "<tr>";
                    contenutoHTML += "<th>Numero ordine</th>";
                    contenutoHTML += "<th>Data</th>";
                    contenutoHTML += "<th>Stato</th>";
                    contenutoHTML += "<th>Totale</th>";
                    contenutoHTML += "<th>Visualizza dettagli prodotti</th>";
               		contenutoHTML += "</tr>";
                	contenutoHTML += "<tr>";
                    contenutoHTML += "<td>[#"+bean.idOrdine+"]</td>";
                    contenutoHTML += "<td>["+bean.data+"]</td>";
                    contenutoHTML += "<td>["+bean.stato+"]</td>";
                    contenutoHTML += "<td>["+bean.prezzototale.toFixed(2)+"&euro;]</td>";
                    contenutoHTML += "<td> <a href=\"./productlist.jsp?id="+bean.idOrdine+"\"> Dettagli prodotti </a> </td>";
                	contenutoHTML += "</tr>";
		      }			
		      contenutoHTML += "</table>";
		} 
		$("#orders").empty();
		$("#orders").append(contenutoHTML);
	});
} 

function eliminaRiga(button) {
  let text = "Stai per cancellare l'ordine, procedere con l'operazione?";
  let result = confirm(text);
  if ( result ) {
    let row = button.parentNode.parentNode;
  	let idOrdine = button.getAttribute("id");
  	let pathArray = window.location.pathname.split('/');
  	let contextPath = '/' + pathArray[1];
  	let url = contextPath + "/RemoveOrderServlet";
  	console.log(idOrdine);
  	$.ajax({
    	url: url,
    	type: 'POST',
    	data:  { idOrdine: idOrdine },
   	 	success: function(response) {
     	 row.parentNode.removeChild(row);
	 	 dynamicOrdersView(contextPath+"/OrderServlet");
    },
    	error: function(xhr, status, error) {
    	  console.error(error);
    	}
 	 });
  	} else {
    	alert("Hai annullato l'operazione!");
  }	
}

function visualizeFirstXX(){
	let schede = document.getElementById("orders");
	console.log(schede);
	let product = schede.querySelectorAll(".order-card");
	let cont = 0;
	
	for (const bean of product) {
		cont++;
		if( cont > 16)
			bean.style.display = "none";		
	}	
}
