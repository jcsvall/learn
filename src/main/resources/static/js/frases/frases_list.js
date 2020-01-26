var frasesListJs = (function() {
	var urlBase = '/learn';
	return {
		perzonalizado : function(idForm) {
			$("#contenido").load(urlBase + "/ajax/personalizar/" + idForm,
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