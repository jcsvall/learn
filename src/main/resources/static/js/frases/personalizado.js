var CONTENEDOR_FORM = "#contenedorForm";
var frasesJs = (function() {
	// var ctx = "${pageContext.request.contextPath}"
	var urlBase = '/personalizado';
	return {
		agregar : function() {
			$("#pagoAutomaticoModalForm").load(urlBase + "/ajax/agregar",
					function(response) {
						console.log(response);
					});
		},
		guardarNo : function(idForm) {
			$("#contenedorBtns").hide();
			$("#loading").show();
			$("#contenido").load(urlBase + "/ajax/guardar/no/" + idForm,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
							$("#contenedorBtns").show();
						}
						$("#loading").hide();
						console.log(response);
					});
		},
		guardar : function(idForm) {
			$("#contenedorBtns").hide();
			$("#loading").show();
			$("#contenido").load(urlBase + "/ajax/guardar/" + idForm,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
							$("#contenedorBtns").show();
						}
						$("#loading").hide();
						console.log(response);
					});

		},
		mostrarTraduccion : function() {
			$("#contenedorBtnMostrarTraduccion").hide();
			$("#pronunciacionT").show();
			$("#traduccionesList").show();
			$("#contenedorBtns").show();
			frasesJs.keyPressEscritura();
		},
		continuarPractica : function() {
			$("#loading2").show();
			$("#btnsPracticar").hide();
		},
		keyPressEscritura : function() {
			var frase = $("#f_0").text().trim().toLowerCase();
			var escritura = $("#escritura").val().trim().toLowerCase();

			if (escritura == frase) {
				$("#escritura").addClass("is-valid");
				$("#resultado").html('<span class="badge badge-success">(Correcto)</span>');
			} else {
				$("#escritura").removeClass("is-valid");
				$("#resultado").html("");
			}
		}
	};
}());