var urlBase = '/categorias';
$(document).ready(function() {
	init();
});
function init() {
}
var categoriasJs = (function() {
	return {
		ver : function(idTr) {
			// $("#"+idTr).hide();
			//$("#" + idTr).css("background-color", "red");
			alert("idTr");
			//$("#" + idTr).removeAttr("style");
			// alert("idTr: "+idTr);
		},
		crear : function(tipo) {
			var id = -1;
			if(tipo != "nuevo"){
				id=1;
			}
			var valor = $("#idCate").val();
			$("#contenido").load(urlBase + "/ajax/crear/"+id+"/"+valor,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
						}
						console.log(response);
					});
		},
		crearObjeto : function() {

		}
	};
}());