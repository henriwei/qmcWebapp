//create new qmcApp module and import ngResource module from angular-resource.js for the $resource service
angular.module("qmcApp", ["ngResource", "ui.bootstrap", "ngRoute", "restangular"])
.constant("viewBaseUrl", "resources/template/")
.constant("baseUrl", "http://localhost:8081/qmc/questmultichoice/questions/")  //create a constant for this module
.constant("getAnswerUrl", "http://localhost:8081/qmc/questmultichoice/questions/correctanswers/")
.config(
	function (viewBaseUrl, $routeProvider, $locationProvider ) { /*service is Restangular, module is restangular*/
		$locationProvider.html5Mode({
			enabled: true,
//			requireBase: false  //see https://docs.angularjs.org/error/$location/nobase
		});
		$routeProvider.when("/questions", {
	        templateUrl: viewBaseUrl + "questions.html",  //if "/..." is used, the path will be absolute
	        controller: "qmcController",
	        resolve: {
	            data: function (Restangular) {
	            	var questions = Restangular.all("qmc/questmultichoice/questions");
	                return questions.getList();
	            }
	        }
	    });
		$routeProvider.when("/createquestion", {
			templateUrl: viewBaseUrl + "createquestion.html",
			controller: "createQuestionController"
		});
	}
);