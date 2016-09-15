<!DOCTYPE html>
<html>
	<head>
        <meta charset="ISO-8859-1">
	    <title>Insert title here</title>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=${google_key}" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script>
            $(function() {
                function _getResourceUriPrefix() {
                    var parts = window.location.pathname.match(/\/[^\/]*/g);
                    var view = parts[1];
                    var version = '/versions' + parts[2];
                    var instance = parts[3];
                    return '/api/v1/views' + view + version + '/instances' + instance+'/resources/';
                }
                $.ajax({
                    url: _getResourceUriPrefix()+"metrics/users/topTenActive",
                    dataType: "json",
                    success: function(data) {
                        console.log(data)
                        $('#metrics').html(data);
                        $.ajax({
                            url: _getResourceUriPrefix()+"metrics/tweets/lastTen",
                            dataType: "json",
                            success: function(data) {
                                console.log(data)
                                $('#metrics').html(data);
                            }
                        });
                    }
                });

            });
        </script>
        <style>
            body .googleMap{
                display: block;
                float: left;
                width: 100%;
                height: 680px;
            }
            body .googleMap .titleInfoWindow{
                display: block;
                float: left;
                width: 100%;
                margin-bottom: 5px;
                font-weight: bold;
                color: #08487a;
                font-size: 13px;
            }
            body .googleMap .contentInfoWindow{
                font-size: 13px;
                color: rgba(0,0,0,0.8);
            }
            body .googleMap .gm-style-iw{
                height: 50px;
            }
            body .googleMap .gm-style-iw div{
                height: 50px;
            }
        </style>
    </head>
	<body>
		<div class="googleMap">
			<div id="map_canvas" style="width:100%; height:100%"></div>
		</div>
		<div id="metrics">

		</div>
	</body>
 <script type="text/javascript" src="js/websocket.js"></script>
</html>