'use strict';

var myApp = angular.module('jobSearch', []);

myApp.controller('searchController', [ '$scope', '$http', '$q', function($scope, $http, $q) {
	$scope.busca = '';
	$scope.tipoBusca = 'DESCRIPTION';

	$scope.buscar = function() {
		$http.get('/api/v1/job/find?qType=' + $scope.tipoBusca + '&q=' + $scope.busca +  '&hash=0&size=5&sortType=SALARY&orderType=DESC')
        .then(res => {
          $scope.jobs = res.data.jobs;
          return;
        })
        .catch(err => {
        	$scope.jobs = {};
          return $q.reject(err.data);
        });
	};
	
	$scope.buscar();
} ]);