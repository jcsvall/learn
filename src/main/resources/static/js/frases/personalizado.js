var CONTENEDOR_FORM = "#contenedorForm";
var frasesJs = (function() {
	// var ctx = "${pageContext.request.contextPath}"
	var urlBase = '/personalizado';
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
							$("#contenedorBtns").show();
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
							$("#contenedorBtns").show();
						}
						$("#loading").hide();
						console.log(response);
					});

		},
		mostrarTraduccion : function() {
			$("#contenedorBtnMostrarTraduccion").hide();
			$("#pronunciacionT").show();
			$("#traduccionesList").show();
			$("#contenedorBtns").show();
			frasesJs.keyPressEscritura();
		},
		continuarPractica : function() {
			$("#loading2").show();
			$("#btnsPracticar").hide();
		},
		keyPressEscritura : function() {
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
				frasesJs.mostrarTraduccion();
			} else {
				$("#escritura").removeClass("is-valid");
				$("#resultado").html("");
			}
		},
		aprendido : function(idForm) {
			$("#contenedorBtns").hide();
			$("#loading").show();
			$("#contenido").load(urlBase + "/ajax/aprendida/" + idForm,
					function(response, status, xhr) {
						if (status == "error") {
							var msg = "Lo sentimos ocurrio un error: ";
							alert(msg + xhr.status + " " + xhr.statusText);
							$("#contenedorBtns").show();
						}
						$("#loading").hide();
						console.log(response);
					});

		}
	};
}());

function quitarCaracteres(strToChange){
	strToChange = strToChange.split('.').join('');
	strToChange = strToChange.split(',').join('');
	strToChange = strToChange.split('!').join('');
	strToChange = strToChange.split('¡').join('');
	strToChange = strToChange.split('’re').join('are');
	strToChange = strToChange.split("'re").join('are');
	strToChange = strToChange.split('’s').join('is');
	strToChange = strToChange.split("'s").join('is');
	strToChange = strToChange.split("'m").join('am');
	strToChange = strToChange.split("’m").join('am');
	strToChange = strToChange.split("don't").join('do not');
	strToChange = strToChange.split("don’t").join('do not');
	strToChange = strToChange.split("won't").join('will not');
	strToChange = strToChange.split("won’t").join('will not');
	strToChange = strToChange.split("didn't").join('did not');
	strToChange = strToChange.split("didn’t").join('did not');
	strToChange = strToChange.split('’').join('');
	strToChange = strToChange.split('-').join('');
	strToChange = strToChange.split('_').join('');
	strToChange = strToChange.split("'").join('');
	strToChange = strToChange.split("okay").join('ok');
	strToChange = strToChange.split(' ').join('');
	return strToChange;
}

function redirectLocation(language){
	var idCat=$("#catId").val();
	location.href = "/personalizado/personalizado/"+language+"/"+idCat;
}