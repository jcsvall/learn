var urlBase = '/learn';
var addJs = (function() {
	return {
		agregar : function() {
			var valorTraduccion = $("#traId").val();
			$("#traducciones").append(
					"<li class='text-success'>" + valorTraduccion + "</li>");
			$("#traId").val("");
		},
		guardar : function() {
			var frase = addJs.crearObjeto();
			alert(JSON.stringify(frase));
			console.log(JSON.stringify(frase));
			$.ajax({
	            type: "POST",
	            contentType: "application/json; charset=utf-8",
	            url: urlBase + "/ajax/crear",
	            data: JSON.stringify(frase),
	            dataType: "json",
	            async: false,
	            cache: false,
	            success: function (data) {
	            	alert(data);
	            	//console.log("LOG=====>"+data.responseText);
//	                if (textStatus == "success") {
//	                    retorno = data;                    
//	                }
	            },
	            error: function (request, status, error) {
	            	console.log(request.responseText);
	                //alert(jQuery.parseJSON(request.responseText).Message);
	            }
	        });
			console.log(frase);
		},
		crearObjeto : function() {
			var frase = {
				"frase":$("#fraseId").val(),
				"pronunciacion":$("#proId").val(),
				"traduccionesList" : []
			};

			$("#traducciones li").each(function() {
				var traduccion = new Object();
				traduccion.traduccion = $(this).text();
				frase.traduccionesList.push(traduccion);
			});
			return frase;
		}
	};
}());