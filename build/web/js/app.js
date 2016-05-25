var app=angular.module("demoapp",[]);
app.controller("democtrl",function($scope){
   
 $scope.karatoptions=[
     { quality:'18KT'},
     { quality:'14KT'},
     { quality:'10KT'},
     { quality:' 9KT'},
     
 ];   
 
 $scope.seletcedkt='18KT';
    
});

