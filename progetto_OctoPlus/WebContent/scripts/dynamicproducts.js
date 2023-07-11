function dynamicOrderView(url){
	$.ajax({
		url : url,
		type: 'GET',
		contentType : 'application/json; charset=utf-8'
	}).done((response) => {
		response = JSON.parse(response);
		var contenutoHTML = "";
		
		contenutoHTML += "<h1>Lista Acquisti</h1>";
		contenutoHTML += "<ul class=\"purchase-list\" id=\"purchase-list\">";
		contenutoHTML += "</ul>";

			 for(const bean of response){
				 contenutoHTML += "<li>";
				 contenutoHTML +=   "<div class=\"purchase\">";
				 contenutoHTML += "		<div class=\"purchase-info\">";
				 contenutoHTML += "			<h3>"+bean.name+"</h3>"
				 contenutoHTML += "			<p>Categoria:"+bean.categoria+"</p>";
				 contenutoHTML += "		</div>";
				 contenutoHTML += "		<div class=\"purchase-price\">";
				 contenutoHTML += "			<p>Qnt acquistate: "+bean.qnt+"</p>";
				 contenutoHTML += "			<p>Prezzo: &euro;"+bean.price.toFixed(2)+"</p>";
				 contenutoHTML += 		"</div>";
				 contenutoHTML += 	"</div>";
				 contenutoHTML += "</li>";
		      }
		      
		$("#container_products").empty();
		$("#container_products").append(contenutoHTML);
	});
} 