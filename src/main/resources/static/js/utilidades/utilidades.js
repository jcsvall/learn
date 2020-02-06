var $utilJS = (function() {
	return {
		ajax : function(type, url, objectJS) {
			var valToReturn = "";
			$.ajax({
				type : type,
				contentType : "application/json; charset=utf-8",
				url : url,
				data : JSON.stringify(objectJS),
				async : false,
				cache : false,
				success : function(data) {
					console.log("SATISFACTORIO: " + data);
					valToReturn = data;
				},
				error : function(request, status, error) {
					alert("OCURRIO ERROR: " + request.responseText);
				}
			});
			return valToReturn;
		},
		clearModalTrash : function() {
			$('body').removeClass("modal-open");
			$('.modal-backdrop').remove();
		}
	};
}());