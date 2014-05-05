'use strict';

module.exports = function(grunt) {

	// Helper function to load pre-defined grunt tasks 
	require('load-grunt-tasks')(grunt);

	// Configures grunt tasks
	grunt.initConfig({

		copy: {
			dist: {
				files: [{
					expand: true,
					cwd: 'app/',
					dest: 'dist/',
					src: [
						'*.html'
					]
				}]
			}
		},

		compress: {
		  	main: {
			    options: {
			      	mode: 'tgz',
			      	archive: 'public/spc-installation.tgz'
			    },
			    expand: true,
			    cwd: 'dist/',
			    src: ['**/*']			    
		  }
		},

		clean: {
			dist: {
				files: [{
					dot: true,
					src: [
						'dist/*',
						'public/*'
					]
				}]
			}
		}

	});

	// Defines the default grunt tasks as a list of other (pre-defined) tasks
	grunt.registerTask('default', [
		'clean',
		'copy',
		'compress'
		]);
	
};