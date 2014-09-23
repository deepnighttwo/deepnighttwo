var app = angular.module("myApp", []);

app.run(function ($rootScope) {
    $rootScope.name = "Ari Lerner";
});

app.controller("mycontroller", function ($scope) {
    $scope.person = {
        name: "controllerName ari learner"
    }
});


app.controller("pc", function ($scope) {
    $scope.person = {prop: "prop from pc"};

    $scope.sayHello = function () {
        $scope.person = {prop: "prop from pc"};
    }
});

app.controller("cc", function ($scope) {
    $scope.person = {prop: "prop from cc"};

    $scope.sayHello = function () {
        $scope.person = {prop: "prop from cc"};
    }
});


app.controller("PlayController", ["$scope", function ($scope) {
    $scope.playing = false;
    $scope.audio = document.createElement('audio');
    $scope.audio.src = "/res/test.mp3";

    $scope.play = function () {
        $scope.audio.play();
        $scope.playing = true;
        console.log("playing");
    };

    $scope.stop = function () {
        $scope.audio.pause();
        $scope.playing = false;
        console.log("stopped");

    };

    $scope.audio.addEventListener('ended', function () {
        $scope.$apply(function () {
            console.log("stopped by ended");
            $scope.stop();
        });
    });

}]);

app.controller("RelatedController", ["$scope", function ($scope) {

}]);

app.controller("PersonList", ["$scope", "$http", function ($scope, $http) {
    $scope.load = function () {
        console.log('running http request');
        var url = 'http://127.0.0.1:8080/angularjsajax';

        // Hidden our previous section's content
        // construct our http request
        $http.get(url).success(function (data, status) {
            console.log(data);
            $scope.data = data;
        }).error(function (data, status) {
            // Some error occurred
        });
    };

}]);