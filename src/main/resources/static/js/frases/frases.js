var CONTENEDOR_FORM = "#contenedorForm";
var frasesJs = (function(){
	//var ctx = "${pageContext.request.contextPath}"
	var urlBase = '/learn';
	return {
		agregar: function () {			
			$("#pagoAutomaticoModalForm").load(urlBase + "/ajax/agregar",
				function (response) {
				console.log(response);
			});			
		},
		guardarNo: function (idForm) {
			$("#contenedorBtns").hide();
			$("#loading").show();
			$("#contenido").load(urlBase + "/ajax/guardar/no/"+idForm,
				function (response) {
				$("#contenedorBtns").show();
				$("#loading").hide();
				console.log(response);
			});			
		},
		guardar : function(idForm) {
			$("#contenedorBtns").hide();
		    $("#loading").show();
			$("#contenido").load(urlBase + "/ajax/guardar/"+idForm,
					function (response) {	
				    $("#contenedorBtns").show();
			        $("#loading").hide();
					console.log(response);
				});
//			var form="#contenedorForm"+idForm;
//			  $(form).submit(function(e){
//				  alert("idForm: "+idForm);
//				  $(form)[0][0]=5;
//				  alert($(form)[0][0].id);
//			    e.preventDefault();
//				$.ajax({
//					type : "POST",
//					enctype : 'multipart/form-data',
//					url : urlBase + "/ajax/guardar",
//					data : new FormData($(form)[0]),
//					processData : false, // prevent jQuery from automatically
//											// transforming the data into a
//											// query string
//					contentType : false,
//					cache : false,
//					timeout : 600000,
//					success : function(data) {
//						console.log(data);
//						if (data != FRAGMENT_ERROR) {
//							$("#cargaContenido").html(data);
//							buildDataTable(idTable);
//						} else {
//							$("#errorModal").html(
//									autorizacionPagoAutoOirsa
//											.buildError(MSG_EXISTEN_ACTIVOS));
//						}
//					},
//					error : function(e) {
//						console.log("ERROR : ", e);
//						alert("Error al guardar");
//					}
//				});
//		      });
			
		},
		mostrarTraduccion : function() {
			$("#contenedorBtnMostrarTraduccion").hide();
			$("#pronunciacionT").show();
			$("#traduccionesList").show();
			$("#contenedorBtns").show();
		},
		buildError : function(errorMessage) {
			var msg = '<div class="alert alert-danger" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button><p class="alert-heading">'+errorMessage+'</p></div>';
			return msg;
		},
		validarForm : function() {
			var form = $(CONTENEDOR_FORM)[0];
			if (form.checkValidity() === false) {
				return false;
			} else {
				return true;
			}
		} 
	};
}());