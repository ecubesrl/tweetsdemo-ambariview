

var mapOptions = {
  center: new google.maps.LatLng(25, 0),
  zoom: 2,
  mapTypeId: google.maps.MapTypeId.ROADMAP
};


var map = new google.maps.Map(document.getElementById("map_canvas"),
	mapOptions);



var socket = new WebSocket("ws://"+document.location.host+document.location.pathname+"websocket/kafkaConsumer");
socket.onmessage = onMessage;

function cutString(stringToCut, size) {

    var newString = stringToCut;
    var maxChar = !size ? 20 : size;
    var length = stringToCut.length;

    if (length > maxChar) {
        newString = stringToCut.substring(0, maxChar - 1) + "...";
    }

    return newString;
}

function onMessage(event) {
	//console.log(event)
	var json = JSON.parse(event.data)
	var date = json.Unixtime;
	var countryCode = json.CountryCode;
	var tweet = json.Message;
	var user = json.Username;

	if(json.is_geo_tw == "true"){
	    //tweet geolocalizzato
        var coords = json.GPS_point.split(",")
        //var lang = event.data.Lingua;

        var latLng = new google.maps.LatLng(parseFloat(coords[0]),parseFloat(coords[1]));

        // Creating a marker and putting it on the map
        var marker = new google.maps.Marker({
            position: latLng,
            title: user,
            icon: 'img/marker.png'
        });
        marker.setMap(map);

        var infoText = "<h1 class='titleInfoWindow'>" + date + " - " + user + "</h1><div class='contentInfoWindow'>" + cutString(tweet, 100) + "</div>";

        /*arrGeoTweet.push({
            date: data.Data,
            countryCode: data.Paese,
            tweet: data.Tweet,
            user: data.User,
            lang: data.Lingua,
            latLng: latLng,
            marker: marker,
            infoText: infoText
        });*/

        var infowindow = new google.maps.InfoWindow({
            content: infoText
        });

        infowindow.open(map, marker);

        marker.addListener('click', function() {
            infowindow.open(map, marker);
        });

        setTimeout(function(){
            infowindow.close();
        }, 10000);

        setTimeout(function(){
            marker.setMap(null);
        }, 60000);

	}
}


