var frasesListJs = (function() {
	var urlBase = '/learn';
	return {
		perzonalizado : function(idForm,accion) {
			$("#contenido").load(urlBase + "/ajax/personalizar/" + idForm+"/" + accion,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
						}
						$("#loading").hide();
						console.log(response);
					});
		},

	};
}());