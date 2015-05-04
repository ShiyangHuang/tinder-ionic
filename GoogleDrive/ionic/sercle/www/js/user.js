'use strict';

angular.module('starter.controllers').factory('User', function($location, Api) {

  var current;
  var currentVolume;
    var userInfo;
    var header;
  var User = {
    getUserInfo: function() {
        return userInfo;
    },
    getHeader: function() {
        return header;
    },

    signin: function(user) {
      return Api.post('/signin', user).then(function(response) {
          userInfo = response.data;
          header = response.headers('set-cookie');
        return response.data;
      });
    },

    signout: function() {
      return Api.get('/signout').then(function() {
        current = null;
        currentVolume = null;
      });
    },

    getVolume: function(link) {
        return Api.get(link, header).then(function(response) {
            return response.data;
        });
    }


  };

  return User;
});
