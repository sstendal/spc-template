'use strict';

describe('MsgCtrl', function() {

	beforeEach(module('spcApp'));

	it('should create correct message', inject(function($controller) {
		var scope = {};
		var ctrl = $controller('MsgCtrl', {$scope:scope});
		
	  	expect(scope.msg).toBe('Hi there!');
	}));

});
