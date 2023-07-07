function dynamicStore(url){
	$.ajax({
		url : url,
		type: 'GET',
		contentType : 'application/json; charset=utf-8'
	}).done((response) => {
		response = JSON.parse(response);
		let contenutoHTML = "";
		
		for(const bean of response){
			contenutoHTML += "<div class=\"box1\">";
			contenutoHTML += "</a> <h3>"+bean.name+"</h3>";
			contenutoHTML += "<a href=\"product?action=read&fromStore=get&id="+bean.code+"\">";
 			contenutoHTML += "<img src='./getPicture?id="+bean.code+"\" onerror=\"this.src='img/nophoto.png'\">";
 			contenutoHTML += "</a> <h4> Categoria:"+bean.categoria+"</h4>";
 			contenutoHTML += "<h5> Costo:"+bean.price+"&euro;</h5>";
 			if( bean.taglie.quantitaM==0 && bean.taglie.quantitaL==0 && bean.taglie.quantitaXL==0 && bean.taglie.quantitaXXL==0)
					contenutoHTML += "<h3 style=\"color: red\"> Prodotto momentaneamente non disponibile! <h3>";
			else{
			contenutoHTML += "<div class=\"cart\">";
 			contenutoHTML += "<a href=\"#\"><i class='bx bx-cart-add'></i></a> </div> </div>";
 			}
		}
		$("#prodotti").append(contenutoHTML);
	});
} 