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
						'public/*',
						'.tmp'
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
			},
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
		},

		bowercopy: {
			libs: {
				options: {
                	destPrefix: 'app/scripts'
            	},
				files: {
					'jquery.js': 'jquery/dist/jquery.js'
				}
			}
		},

		useminPrepare: {
 			html: 'app/index.html'
		},

		usemin: {
 			html: 'dist/index.html'
		}

	});

	// Starts webserver and reloads browser when something changes
    grunt.registerTask('serve', [
            'build',
            'connect',
            'watch'
        ]);

	// Builds the distribution
    grunt.registerTask('build', [
            'copy',
			'useminPrepare',
			'concat',
			'uglify',
			'cssmin',
			'usemin',
        ]);


	// Defines the default grunt tasks as a list of other (pre-defined) tasks
	grunt.registerTask('default', [
		'clean',
		'build',
		'compress'
		]);
	
};