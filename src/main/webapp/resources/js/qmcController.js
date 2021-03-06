angular.module("qmcApp")
.controller("qmcController", function ($scope, $http, $resource, baseUrl, getCorrectAnswerUrl, sendAnswers, data) {

//	$scope.questionsResource = $resource(baseUrl + ":id", {id : "@id"});
//	$scope.questionsWithCorrectAnswerResource = $resource(getAnswerUrl + ":id", {id : "@id"});
	
	//pagination setting
	$scope.totalItems = 0;  //Total number of items in all pages.
	$scope.currentPage = 1;
	$scope.maxPaginationSize = 3;   //Limit number for pagination size.
	$scope.itemsPerPage = 1;
	$scope.currentQuestion = function(){ return $scope.questions[$scope.currentPage-1]};
	$scope.currentChoices = function(){ return $scope.currentQuestion().choices};
	
	$scope.findAll = function(){
//		$scope.questions = $scope.questionsResource.query();
		$scope.questions = data;
		$scope.totalItems = $scope.questions.length;
	}
	
	$scope.sendChoices = function(){
		$http({
			url: sendAnswers,
			method: "post",
			data: $scope.getUserSelections()
		})
		.success(
			function(data) {
				$scope.getCorrectChoices(); //retrive correct answers if user's answers are sent correctly.
			}
		)
		.error(
			function(data){
				alert("error when sending answers");
			}
		);
	}
	
	$scope.getUserSelections = function(){
		var selections = [];
		var questions = $scope.questions;
		for(i=0; i<questions.length; i++){
			for(j=0; j<questions[i].choices.length; j++){
				var choice = questions[i].choices[j];
				if(choice.selected==true)
					selections[selections.length]=choice.id;
			}
		}
		return selections;
	}
	
	$scope.getCorrectChoices = function(){
		$http({
			url: getCorrectAnswerUrl,
			method: "get",
			data: $scope.questions
		})
		.success(
			function(data){
				console.log(data);
				$scope.questions=data;
			}
		)
		.error(
			function(data){
				alert("error when retrieving answers");
			}
		);
	}
	
//	$scope.getCorrectChoices = function(){
//		$scope.questionsWithCorrectAnswerAccessor = $scope.questionsWithCorrectAnswerResource.query(); //the result of this query() is an empty array, which is populated later.
//		$scope.questionsWithCorrectAnswerAccessor.$promise.then(  //when the ajax query return and it's success, the argument is callback function that will be called.
//				function(data){
//					$scope.questionsWithCorrectAnswer = data;
//					$scope.updateWithCorrectChoices();
//				}
//		);
//	}
		
//	$scope.updateWithCorrectChoices = function(){
//		$scope.goodChoiceNum = 0;
//		$scope.badChoiceNum = 0;
//		$scope.wrongAnswer = 0;
//		
//		var questions = $scope.questions;
//		for(i=0; i<questions.length; i++){
//			var wrongAnswer = 0;
//			
//			for(j=0; j<questions[i].choices.length; j++){
//				var currentChoice = questions[i].choices[j];
//				var answerChoice = $scope.questionsWithCorrectAnswer[i].choices[j];
//				currentChoice.correctness = answerChoice.correctness;
//				if(((currentChoice.selected==true) && (currentChoice.correctness==true))
//					|| 	((currentChoice.selected==false) && (currentChoice.correctness==false)) ){
//					$scope.goodChoiceNum += 1;
//				} else {
//					$scope.badChoiceNum += 1;
//					wrongAnswer += 1;
//				}
//			}
//			if(wrongAnswer > 0){
//				$scope.wrongAnswer += 1;
//			}
//		}
//	}
	
	$scope.findAll();
});