$(document).ready(function() {
	init();
});
function init() {
	$('input[type="checkbox"]').change(function(event) {

		var valor = $(this).val();
		var CLASE_COMUN = ".custom-control-input";
		if (this.checked) {
			$(this).addClass("is-valid");
			if (valor == "todos") {
				$(CLASE_COMUN).prop("checked", true);
				$(CLASE_COMUN).addClass("is-valid");
			}
		} else {
			$(this).removeClass("is-valid");
			if (valor == "todos") {
				$(CLASE_COMUN).prop("checked", false);
				$(CLASE_COMUN).removeClass("is-valid");
			}
		}

	});
}
var frasesJs = (function() {
	var urlBase = '/practicar';
	var ObjetoComunDto = {};
	return {
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
			var categoriasId = "";
			var categoriasHTML = "";
			var count = 0;
			$('.custom-control-input:checked').each(function() {
				var id = $(this).val();
				var labelText = $("#lab_" + id).text();				
				var classCss="light";//success
					
				if(count==1){
					classCss="info";//primary
				}
                if(count==2){
                	classCss="secondary";
				}
				
				if (id != "todos") {
					categoriasId += id + ",";
					var completeClass = "badge badge-"+classCss;
					categoriasHTML += '<span class="'+completeClass+'" style="margin-left:2px">'+labelText+'</span>';
					//categoriasHTML += '<li class="list-group-item">'+labelText+'</li>';
				}else{
					count = -1;
				}
				
				if(count==2){
					count=-1;
				}
				
				count++;
				
			});
			if (categoriasId != "") {
				categoriasId = categoriasId.substring(0,
						categoriasId.length - 1);
			}
			ObjetoComunDto.valor = categoriasId;
			$("#catSelected").html(categoriasHTML);
		},
		getFrasesCategoria:function(){
			$('#contenido').html(
					$utilJS.ajax("POST", urlBase + "/ajax/frases", ObjetoComunDto));
		}
	};
}());