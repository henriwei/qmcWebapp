angular.module("qmcApp") //look for 'qmcApp' module, then called its controller method to create one new controller

.controller("defaultController", 
	function ($scope, $http, $resource, baseUrl, $location){
		$scope.findAllQuestions = function() {
			$location.path("questions");
		}
	}
);