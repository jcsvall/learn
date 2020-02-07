var CONTENEDOR_FORM = "#contenedorForm";
var frasesJs = (function() {
	// var ctx = "${pageContext.request.contextPath}"
	var urlBase = '/learn';
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
						}
						$("#loading").hide();
						console.log(response);
					});
			// var form="#contenedorForm"+idForm;
			// $(form).submit(function(e){
			// alert("idForm: "+idForm);
			// $(form)[0][0]=5;
			// alert($(form)[0][0].id);
			// e.preventDefault();
			// $.ajax({
			// type : "POST",
			// enctype : 'multipart/form-data',
			// url : urlBase + "/ajax/guardar",
			// data : new FormData($(form)[0]),
			// processData : false, // prevent jQuery from automatically
			// // transforming the data into a
			// // query string
			// contentType : false,
			// cache : false,
			// timeout : 600000,
			// success : function(data) {
			// console.log(data);
			// if (data != FRAGMENT_ERROR) {
			// $("#cargaContenido").html(data);
			// buildDataTable(idTable);
			// } else {
			// $("#errorModal").html(
			// autorizacionPagoAutoOirsa
			// .buildError(MSG_EXISTEN_ACTIVOS));
			// }
			// },
			// error : function(e) {
			// console.log("ERROR : ", e);
			// alert("Error al guardar");
			// }
			// });
			// });

		},
		mostrarTraduccion : function() {
			$("#contenedorBtnMostrarTraduccion").hide();
			$("#pronunciacionT").show();
			$("#traduccionesList").show();
			$("#contenedorBtns").show();
		},
		continuarPractica : function() {
			$("#loading2").show();
			$("#btnsPracticar").hide();
		},
		seleccionarTodosLosCheckbox : function(elemento) {
			if ($(elemento).is(':checked')) {
				alert("El checkbox con valor " + $(elemento).val()
						+ " ha sido seleccionado");
				$(".custom-control-input").prop("checked", true);
			} else {
				alert("El checkbox con valor " + $(elemento).val()
						+ " ha sido deseleccionado");
				$(".custom-control-input").prop("checked", false);
			}

		},
		realizarPractica : function() {
			$('.custom-control-input:checked').each(
					function() {
						alert("El checkbox con valor " + $(this).val()
								+ " está seleccionado");
					});
		}
	};
}());