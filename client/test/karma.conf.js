// Karma configuration

module.exports = function(config) {
  config.set({

    basePath: '..',

    frameworks: ['jasmine'],

    files: [
      'app/scripts/*.js',
      'bower_components/angular-mocks/angular-mocks.js',
      'test/unittests/*.js',
    ],

    autoWatch: true,

    browsers: ['Firefox'],

    singleRun: false
  });
};
