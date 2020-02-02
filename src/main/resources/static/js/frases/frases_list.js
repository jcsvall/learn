var frasesListJs = (function() {
	var urlBase = '/learn';
	return {
		perzonalizado : function(idForm, accion) {
			$("#contenido").load(
					urlBase + "/ajax/personalizar/" + idForm + "/" + accion,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
						}
						$("#loading").hide();
						console.log(response);
					});
		},
		eliminar : function(idForm) {
			var confirmacion = confirm("¿Se eliminará, desea continuar?");
			if (confirmacion == true) {
				$("#contenido").load(urlBase + "/ajax/eliminar/" + idForm,
						function(response, status, xhr) {
							if (status == "error") {
								var msg = "Lo sentimos ocurrio un error: ";
								alert(msg + xhr.status + " " + xhr.statusText);
							}
							$("#loading").hide();
							console.log(response);
						});
			}
		}

	};
}());