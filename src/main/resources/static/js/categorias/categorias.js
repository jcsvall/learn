var urlBase = '/categorias';
$(document).ready(function() {
	init();
});
function init() {
}
var categoriasJs = (function() {
	var objetoSelected = {};
	return {
		ver : function(idTr, idObject, elemento) {
			objetoSelected.id = idObject;
			objetoSelected.categoria = $(elemento).text();
			$('#select').modal('show');
		},
		verEditar : function() {
			$('#select').modal('hide');
			$('#edicionDialog').modal('show');
			$("#idCateEdicion").val(objetoSelected.categoria);
		},
		crear : function(accion) {
			var id = -1;
			var categoria = $("#idCate").val();
			if (accion == "EDITAR") {
				id = objetoSelected.id;
				categoria = $("#idCateEdicion").val();
			}
			var categoria = {
				"id" : id,
				"categoria" : categoria,
				"accion" : accion
			};
			$('#contenido').html(
					$utilJS.ajax("POST", urlBase + "/ajax/crear", categoria));

			if (accion == "EDITAR") {
				$utilJS.clearModalTrash();
			}
		},
		crearObjeto : function() {

		}
	};
}());