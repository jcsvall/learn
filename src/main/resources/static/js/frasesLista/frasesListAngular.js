var app = angular.module('frasesApp', []);
app.controller('frasesController', function($scope, $http, $window) {
	$http.get("/listRest/ajax/frases").then(function(response) {
		$scope.frases = response.data;
	});

	$scope.getSelecteds = function() {
		var ids = "";
		for (var i = 0; i < $scope.frases.length; i++) {
			if ($scope.frases[i].Selected) {
				var id = $scope.frases[i].id;
				var value = $scope.frases[i].frase;
				ids += id + ",";
			}
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	}

	$scope.cambio = function(check) {
		var fraseObject = check.f;
		var elementoById = angular.element('#' + fraseObject.id);
		if (fraseObject.Selected) {
			elementoById.addClass("is-valid");
		} else {
			elementoById.removeClass("is-valid");
		}
		var selecteds = $scope.getSelecteds();
		if (selecteds != "") {
			$scope.disable = false;
		} else {
			$scope.disable = true;
		}
		// $window.alert(selecteds);
	}

	$scope.addPersonalizado = function() {
		var selecteds = $scope.getSelecteds();
		$http({
			method : "POST",
			url : "/listRest/ajax/personalizar/"+selecteds
		}).then(function mySuccess(response) {
			$scope.myWelcome = response.data;
		}, function myError(response) {
			$scope.myWelcome = response.statusText;
		});
	}

});