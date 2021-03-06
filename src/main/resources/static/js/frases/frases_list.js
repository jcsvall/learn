$(document).ready(function() {
	$('input[type="checkbox"]').change(function(event) {
		if (this.checked) {
			$(this).addClass("is-valid");
		} else {
			$(this).removeClass("is-valid");
		}
	});
});
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
			var traId = "#traId" + id;
			var valorTraduccion = $(traId).val();

			var valorConteoLista = 0;
			$("#traducciones" + id + " li").each(function() {
				valorConteoLista++;
			});

			$("#traducciones" + id)
					.append(
							"<li class='text-success' id='liE"
									+ valorConteoLista
									+ id
									+ "'><span class='badge badge-secondary' id='span"
									+ valorConteoLista
									+ id
									+ "'>"
									+ valorTraduccion
									+ "</span> <span class='badge badge-danger' onclick='frasesListJs.quitarDeLista(\"liE"
									+ valorConteoLista + id
									+ "\")'>X</span></li>");
			$(traId).val("");
		},
		guardar : function(id) {
			var frase = frasesListJs.crearObjeto(id);
			// alert(JSON.stringify(frase));
			if (frase.traduccionesList.length == 0 || frase.frase.trim() == "") {
				$('#validacionModal').modal('show');// it's not present in the
				// view.
			} else {
				console.log(JSON.stringify(frase));
				$.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : urlBase + "/ajax/update",
					data : JSON.stringify(frase),
					async : false,
					cache : false,
					success : function(data) {
						console.log("SATISFACTORIO: " + data);
						$('#contenido').html(data);
						$('body').removeClass("modal-open");
						$('.modal-backdrop').remove();
					},
					error : function(request, status, error) {
						console.log(request.responseText);
						alert("OCURRIO ERROR: " + request.responseText);
					}
				});
			}
		},
		crearObjeto : function(id) {
			var frase = {
				"id" : $("#fraseId_" + id).val(),
				"frase" : $("#fraseId" + id).val(),
				"pronunciacion" : $("#proId" + id).val(),
				"categoriaId" : $("#catId" + id).val(),
				"traduccionesList" : []
			};

			var count = 0;
			$("#traducciones" + id + " li").each(function() {
				var traduccion = new Object();
				// traduccion.traduccion = $(this).text();
				traduccion.traduccion = $("#span" + count + id).text();
				frase.traduccionesList.push(traduccion);
				count++;
			});
			return frase;
		},
		quitarDeLista : function(elemento) {
			$("#" + elemento).remove();
		},
		buscar : function() {
			var ObjetoComunDto = {};
			ObjetoComunDto.valor = $("#buscarTxt").val();

			if ($("#perCheck").is(':checked')) {
				ObjetoComunDto.valor1 = "true";
			} else {
				ObjetoComunDto.valor1 = "false";
			}

			var categoriasId = "";
			$('.custom-control-input:checked').each(function() {
				if ($(this).val() != "PERSONALIZADO") {
					categoriasId += $(this).val() + ",";
				}
			});
			if (categoriasId != "") {
				categoriasId = categoriasId.substring(0,
						categoriasId.length - 1);
			}
			ObjetoComunDto.valor2 = categoriasId;

			$('#contenido').html(
					$utilJS.ajax("POST", urlBase + "/ajax/buscar",
							ObjetoComunDto));
		}

	};
}());