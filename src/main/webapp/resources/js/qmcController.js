angular.module("qmcApp")
.controller("qmcController", function ($scope, $http, $resource, baseUrl, getAnswerUrl, data) {

	$scope.questionsResource = $resource(baseUrl + ":id", {id : "@id"});
	$scope.questionsWithCorrectAnswerResource = $resource(getAnswerUrl + ":id", {id : "@id"});
	
	$scope.findAll = function(){
//		$scope.questions = $scope.questionsResource.query();
		$scope.questions = data;
	}
	
	$scope.sendChoices = function(){
		$http({
			url: baseUrl + "sendanswer",
			method: "post",
			data: $scope.questions
		})
		.success(
			function(data) {
				$scope.getCorrectChoices();
			}
		)
		.error(
			function(data){
				alert("error when sending answers");
			}
		);
	}
	
	$scope.getCorrectChoices = function(){
		$scope.questionsWithCorrectAnswerAccessor = $scope.questionsWithCorrectAnswerResource.query(); //the result of this query() is an empty array, which is populated later.
		$scope.questionsWithCorrectAnswerAccessor.$promise.then(  //when the ajax query return and it's success, the argument is callback function that will be called.
				function(data){
					$scope.questionsWithCorrectAnswer = data;
					$scope.updateWithCorrectChoices();
				}
		);
	}
		
	$scope.updateWithCorrectChoices = function(){
		$scope.goodChoiceNum = 0;
		$scope.badChoiceNum = 0;
		$scope.wrongAnswer = 0;
		
		var questions = $scope.questions;
		for(i=0; i<questions.length; i++){
			var wrongAnswer = 0;
			
			for(j=0; j<questions[i].choices.length; j++){
				var currentChoice = questions[i].choices[j];
				var answerChoice = $scope.questionsWithCorrectAnswer[i].choices[j];
				currentChoice.correctness = answerChoice.correctness;
				if(((currentChoice.selected==true) && (currentChoice.correctness==true))
					|| 	((currentChoice.selected==false) && (currentChoice.correctness==false)) ){
					$scope.goodChoiceNum += 1;
				} else {
					$scope.badChoiceNum += 1;
					wrongAnswer += 1;
				}
			}
			if(wrongAnswer > 0){
				$scope.wrongAnswer += 1;
			}
		}
	}
	
	$scope.findAll();
});