var url = "http://192.168.0.14:8080/CEL_LOGUIN";
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();
$(document).ready(function(){
    $("#error").css("display","none");
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
    var auto=localStorage.getItem("automatico");
    if(auto==="true"){
        sessionStorage.setItem("usr", localStorage.getItem("usr"));
        sessionStorage.setItem("pass", localStorage.getItem("pass"));
        document.location.href="pages/ingreso.html";
    }
});
function ingresar() {
    var usr = $("input[name=usuario]").val();
    var pass = $("input[name=pass]").val();
    $.post(url,{usr:usr,pass:pass})
    .done(function(json){
        sessionStorage.setItem("usr", usr);
        sessionStorage.setItem("pass", pass);
        if(json.ingreso) {
            if($("input[name=cb_recordar]").prop("checked")){
                localStorage.setItem("automatico", true);
                localStorage.setItem("usr", usr);
                localStorage.setItem("pass", pass);
            }
            document.location.href="pages/ingreso.html";
        }
        else{             
            $("#error").text("ERROR EN LOS DATOS!");
            $("#error").css("display","");
        }
    })
    .fail(function(){
        $("#error").text("NO HAY COMUNICACION CON EL SERVIDOR!");
        $("#error").css("display","");
    });    
}