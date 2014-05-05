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
						'*.html',
						'**/*.css',
						'**/*.js'
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
		},

		connect: {
			dist: {
				options: {
					port: 9000,
					base: 'dist',
					hostname: 'localhost',
                	livereload: 35729,
					open: true
				}
			}
		},

		watch: {
			dist: {
				files: [
					'app/**/*.html',
					'app/**/*.js',
					'app/**/*.css'
					],
				tasks: ['default'],
				options: {
					livereload: true
				}
			}
		}

	});

	// Starts webserver and reloads browser when something changes
    grunt.registerTask('serve', [
            'copy',
            'connect',
            'watch'
        ]);



	// Defines the default grunt tasks as a list of other (pre-defined) tasks
	grunt.registerTask('default', [
		'clean',
		'copy',
		'compress'
		]);
	
};