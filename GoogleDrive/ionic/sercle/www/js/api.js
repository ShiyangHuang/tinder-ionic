'use strict';

angular.module('starter.controllers').factory('Api', function($location, $http) {

  var Api = {

    prefix: function() {
      return 'https://sercle.net/crossbow';
    },

    whole: function(url) {
      return this.prefix() + url;
    },

    // http methods
    get: function(url,header) {
      return $http.get(this.whole(url),{headers:{'cookie':header}});
    },

    post: function(url, data) {
      return $http.post(this.whole(url), data);
    },

    delete: function(url) {
      return $http.delete(this.whole(url));
    }
  };

  return Api;
});
