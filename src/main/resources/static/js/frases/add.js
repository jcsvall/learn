var urlBase = '/learn';
$(document).ready(function() {
	init();
});
function init() {
	$("#contenido").load(urlBase + "/ajax/accion/add",
			function(response, status, xhr) {
				if (status == "error") {
					var msg = "Lo sentimos ocurrio un error: ";
					alert(msg + xhr.status + " " + xhr.statusText);
				}
				console.log(response);
			});
}
var addJs = (function() {
	return {
		agregar : function() {
			var valorTraduccion = $("#traId").val();
			$("#traducciones").append(
					"<li class='text-success'><span class='badge badge-secondary'>"
							+ valorTraduccion + "</spand></li>");
			$("#traId").val("");
		},
		guardar : function() {
			var frase = addJs.crearObjeto();
			// alert(JSON.stringify(frase));
			if (frase.traduccionesList.length == 0 || frase.frase.trim() == "") {
				$("#valId")
						.html(
								"Debe ingresar una frase y almenos	una traducci&oacute;n para poder guardar");
				$('#validacionModal').modal('show');
			} else if (frase.categoriaId == null) {
				$("#valId").html("Debe ingresar una categoria para poder guardar");
				$('#validacionModal').modal('show');
			} else {
				console.log(JSON.stringify(frase));
				$.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : urlBase + "/ajax/crear",
					data : JSON.stringify(frase),
					// dataType: "json",
					async : false,
					cache : false,
					success : function(data) {
						console.log("SATISFACTORIO: " + data);
						$('#contenido').html(data);
					},
					error : function(request, status, error) {
						console.log(request.responseText);
						alert("OCURRIO ERROR: " + request.responseText);
						// alert(jQuery.parseJSON(request.responseText).Message);
					}
				});
			}
		},
		crearObjeto : function() {
			var frase = {
				"frase" : $("#fraseId").val(),
				"pronunciacion" : $("#proId").val(),
				"categoriaId" : $("#catId").val(),
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