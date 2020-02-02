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
			var idModal = "#confirmacionModal_" + idForm;
			$(idModal).modal('hide');
			$('body').removeClass("modal-open");
			$('.modal-backdrop').remove();
			$("#contenido").load(urlBase + "/ajax/eliminar/" + idForm,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
						}
						$("#loading").hide();
						console.log(response);
					});
		},
		agregar : function(id) {
			var traId = "#traId"+id;
			var valorTraduccion = $(traId).val();
			$("#traducciones"+id).append(
					"<li class='text-success'><span class='badge badge-secondary'>"
							+ valorTraduccion + "</spand></li>");
			$(traId).val("");
		}

	};
}());