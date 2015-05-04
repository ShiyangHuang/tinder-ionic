angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope, User) {
        $scope.userInfo = User.getUserInfo();
        $scope.volume = $scope.userInfo.USER.Volume;
})

.controller('VolumeDetailCtrl', function($scope, $stateParams, User, $http) {
        $scope.volume = "/volume/" + $stateParams.volumeID;
        $scope.folder = $scope.volume;
        $scope.fileList = {};
        $scope.currentFolder = "";

        $scope.priviousFolder = function() {
            if ($scope.folder.lastIndexOf("/") > 10) {
                $scope.folder = $scope.folder.substr(0,$scope.folder.lastIndexOf("/"));
                User.getVolume($scope.folder).then(function(data) {
                    $scope.fileList = data;
                });
            }
        };

        $scope.goFolder = function(link) {
            $scope.currentFolder = link;
            $scope.folder = $scope.folder + '/' + link;
            User.getVolume($scope.folder).then(function(data) {
                $scope.fileList = data;
            });
        };

        $scope.openFolder = function() {
            User.getVolume($scope.folder).then(function(data) {
                $scope.fileList = data;
            });
        };

        $scope.openFile = function(link) {
            return "https://sercle.net/crossbow/" + $scope.folder + "/" + link;
        }

})

.controller('ChatsCtrl', function($scope, Chats) {
  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  }
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope, User) {

        $scope.user = {"email":"huangshiyang@dataplug.info","password":"huang90514"};
        //$scope.user = {"email":"","password":""};
        $scope.submit = function() {
            User.signin($scope.user).then(function(data){
                $scope.response = data;
                $scope.header = User.getHeader();
                if ($scope.response.USER.Profile.user_activated == true) {
                    window.location.href="#/tab/dash";
                }
            }).error(function(data) {
                $scope.response = 'error';
            });

        };


});
