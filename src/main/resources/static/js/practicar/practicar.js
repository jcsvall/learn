var urlBase = '/practicar';
$(document).ready(function() {
	init();
});
function init() {
	$('input[type="checkbox"]').change(function(event) {

		var valor = $(this).val();
		var CLASE_COMUN = ".custom-control-input-dl";
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
	// frasesJs.getFrasesCategoria();
}
var frasesJs = (function() {
	// var urlBase = '/practicar';
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
			$('.custom-control-input-dl:checked').each(
					function() {
						var id = $(this).val();
						var labelText = $("#lab_" + id).text();
						var classCss = "light";// success

						if (count == 1) {
							classCss = "info";// primary
						}
						if (count == 2) {
							classCss = "secondary";
						}

						if (id != "todos") {
							categoriasId += id + ",";
							var completeClass = "badge badge-" + classCss;
							categoriasHTML += '<span class="' + completeClass
									+ '" style="margin-left:2px">' + labelText
									+ '</span>';
							// categoriasHTML += '<li
							// class="list-group-item">'+labelText+'</li>';
						} else {
							count = -1;
						}

						if (count == 2) {
							count = -1;
						}

						count++;

					});
			if (categoriasId != "") {
				categoriasId = categoriasId.substring(0,
						categoriasId.length - 1);
			}
			ObjetoComunDto.valor = categoriasId;
			$("#catSelected").html(categoriasHTML);
			frasesJs.getFrasesCategoria();
		},
		getFrasesCategoria : function() {
			$('#contenido').html(
					$utilJS.ajax("POST", urlBase + "/ajax/frases",
							ObjetoComunDto));
		}
	};
}());

function mostrarTraduccionDialog(fid) {
	var data = '';
	var listaId = "ul_" + fid;
	$("#" + listaId + " li")
			.each(
					function() {
						data += '<a href="#" class="list-group-item list-group-item-action">'
								+ $(this).text() + '</a>';
					});
	$("#dataModal").html(data);
}

function comenzar() {
	$("#cate").hide();
	$utilJS.load("#contenido", urlBase + "/ajax/practica");
}

function mostrarTraduccion() {
	$("#contenedorBtnMostrarTraduccion").hide();
	$("#pronunciacionT").show();
	$("#traduccionesList").show();
	$("#contenedorBtns").show();
}

function guardar(idForm) {
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
}

function guardarNo(idForm) {
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
}

function keyPressEscritura() {
	var frase = $("#f_0").text().trim().toLowerCase();
	var punto = frase.substring(frase.length, frase.length - 1);
	var fraseSinPunto = frase;
	if (punto == ".") {
		fraseSinPunto = frase.substring(0, frase.length - 1);
	}

	frase = quitarCaracteres(frase);

	var escritura = quitarCaracteres($("#escritura").val().trim().toLowerCase());

	if (escritura == frase || escritura == fraseSinPunto) {
		$("#escritura").addClass("is-valid");
		$("#resultado").html(
				'<span class="badge badge-success">(Correcto)</span>');
		mostrarTraduccion();
	} else {
		$("#escritura").removeClass("is-valid");
		$("#resultado").html("");
	}
}

function quitarCaracteres(strToChange) {
	strToChange = strToChange.split('.').join('');
	strToChange = strToChange.split(',').join('');
	strToChange = strToChange.split('!').join('');
	strToChange = strToChange.split('¡').join('');
	strToChange = strToChange.split('’re').join('are');
	strToChange = strToChange.split("'re").join('are');
	strToChange = strToChange.split('’s').join('is');
	strToChange = strToChange.split("'s").join('is');
	strToChange = strToChange.split('’').join('');
	strToChange = strToChange.split('-').join('');
	strToChange = strToChange.split('_').join('');
	strToChange = strToChange.split("'").join('');
	strToChange = strToChange.split("don't").join('do not');
	strToChange = strToChange.split("okay").join('ok');
	strToChange = strToChange.split(' ').join('');
	return strToChange;
}
function clickCheckTable(checkSelected) {
	var CLASE_COMUN = ".checkTb";
	if (checkSelected.checked) {
		$(checkSelected).addClass("is-valid");
		$(CLASE_COMUN).prop("checked", true);
		$(CLASE_COMUN).addClass("is-valid");
	} else {
		$(checkSelected).removeClass("is-valid");
		$(CLASE_COMUN).prop("checked", false);
		$(CLASE_COMUN).removeClass("is-valid");
	}
}

function clickCheckOne(checkSelected) {
	var CLASE_COMUN = ".checkTb";
	if (checkSelected.checked) {
		$(checkSelected).addClass("is-valid");
	} else {
		$(checkSelected).removeClass("is-valid");
	}
}