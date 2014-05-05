'use strict';

/* Controllers */

var spcApp = angular.module('spcApp', []);

spcApp.controller('MsgCtrl', function($scope) {
  $scope.msg = "Hi there!";
});
