<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="test.gcm.model.*" %>
<%@ page language="java" contentType="text/html; charset=windows-31j"
    pageEncoding="windows-31j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-31j">
<title>Graph</title>
    <style type="text/css">
      #graph {
		width: 600px;
		height: 400px;
      }
    </style>
    <script src="js/flotr2.min.js"></script>
</head>
<body>
    <div id="graph"></div>
<%
//角度と圧力のデータを取得する
DatastoreService datastore =  DatastoreServiceFactory.getDatastoreService();

Query query = new Query("pressure");
query.addSort("time");
Iterable<Entity> entities = datastore.prepare(query).asIterable();
List<Pressure> pressureList = new ArrayList<Pressure>();

for (Entity entity : entities) {
	  Pressure pressure = new Pressure();
	  pressure.setPressure1(Integer.parseInt(String.valueOf((Long)entity.getProperty("pressure1"))));
	  pressure.setPressure2(Integer.parseInt(String.valueOf((Long)entity.getProperty("pressure2"))));
	  pressure.setPressure3(Integer.parseInt(String.valueOf((Long)entity.getProperty("pressure3"))));
	  pressure.setPressure4(Integer.parseInt(String.valueOf((Long)entity.getProperty("pressure4"))));
	  Date time = new Date();
	  time.setTime((Long)entity.getProperty("time"));
	  pressure.setTime(time);
	  pressureList.add(pressure);
}

int size = pressureList.size();

%>
    <script type="text/javascript">
    (function basic_time(container) {
    	var d1 = [
<%
for(int i = 0; i < size; i++ ){
Pressure pressure = pressureList.get(i);
String time = String.valueOf(pressure.getTime().getTime());
String value = String.valueOf(pressure.getPressure1());
boolean isLast = (i == (size - 1));
if(!isLast){
%>
    	          [<%= time %>, <%= value %>],
<%
}else{
%>
    	          [<%= time %>, <%= value %>]
<%
}
}
%>
    	          ],
    	      d2 = [
<%
for(int i = 0; i < size; i++ ){
	Pressure pressure = pressureList.get(i);
	String time = String.valueOf(pressure.getTime().getTime());
	String value = String.valueOf(pressure.getPressure2());
	boolean isLast = (i == (size - 1));
	if(!isLast){
%>
	    	          [<%= time %>, <%= value %>],
<%
	}else{
%>
	    	          [<%= time %>, <%= value %>]
<%
	}
	}
%>
    	       ],
     	      d3 = [
<%
for(int i = 0; i < size; i++ ){
	Pressure pressure = pressureList.get(i);
	String time = String.valueOf(pressure.getTime().getTime());
	String value = String.valueOf(pressure.getPressure3());
	boolean isLast = (i == (size - 1));
	if(!isLast){
%>
	    	          [<%= time %>, <%= value %>],
<%
	}else{
%>
	    	          [<%= time %>, <%= value %>]
<%
	}
	}
%>
    	       ],
      	      d4 = [
<%
for(int i = 0; i < size; i++ ){
	Pressure pressure = pressureList.get(i);
	String time = String.valueOf(pressure.getTime().getTime());
	String value = String.valueOf(pressure.getPressure4());
	boolean isLast = (i == (size - 1));
	if(!isLast){
%>
	    	          [<%= time %>, <%= value %>],
<%
	}else{
%>
	    	          [<%= time %>, <%= value %>]
<%
	}
	}
%>
    	       ],
			   a1 = [
<%
 datastore =  DatastoreServiceFactory.getDatastoreService();

 query = new Query("angle");
 query.addSort("time");
entities = datastore.prepare(query).asIterable();
List<Angle> angleList = new ArrayList<Angle>();

for (Entity entity : entities) {
	  Angle angle = new Angle();

	  String tmp = (String)entity.getProperty("angle");
	  int data = 0;
	  if(tmp != null){
	  	data = Integer.parseInt(tmp);
	  }
	  angle.setAngle(data);

	  String piston = (String)entity.getProperty("piston");
	  if(piston != null){
	  	if(piston.equals("1")){
	  		angle.setPiston(true);
	  	}else{
	  		angle.setPiston(false);
	  	}
	  }

	  Date time = new Date();
	  time.setTime((Long)entity.getProperty("time"));
	  angle.setTime(time);
	  angleList.add(angle);
}

size = angleList.size();

for(int i = 0; i < size; i++ ){
Angle angle = angleList.get(i);
String time = String.valueOf(angle.getTime().getTime());
int angleValue = angle.getAngle();
if(angleValue == 4){angleValue = 0;}
String value = String.valueOf(angleValue);
boolean piston = angle.isPiston();

boolean isLast = (i == (size - 1));
if(!isLast){
%>
    	          [<%= time %>, <%= value %>],
<%
}else{
%>
    	          [<%= time %>, <%= value %>]
<%
}
}
%>
    	          ],
  			   a2 = [
<%
size = angleList.size();

for(int i = 0; i < size; i++ ){
Angle angle = angleList.get(i);
String time = String.valueOf(angle.getTime().getTime());
String value = null;
boolean piston = angle.isPiston();
if(piston){
	value = "5";
}else{
	value = "0";
}
boolean isLast = (i == (size - 1));
if(!isLast){
%>
    	          [<%= time %>, <%= value %>],
<%
}else{
%>
    	          [<%= time %>, <%= value %>]
<%
}
}
%>
    	          ],

    	       data = [{
    	    	   data: d1,
    	    	   label: "pressure1"
    	    	   },
    	    	   {
    	    		   data: d2,
    	    		   label: "pressure2"
    	    		},
     	    	   {
     	    		   data: d3,
     	    		   label: "pressure3"
     	    		},
     	    	   {
     	    		   data: d4,
     	    		   label: "pressure4"
     	    		},
     	    		{
         	    	   data: a1,
         	    	   label: "angle", yaxis : 2
         	    	},
         	    	{
         	    	   data: a2,
         	    	   label: "piston", yaxis : 2
         	    	}
    	    	 ];
    	  options = {
    			    xaxis : {
    			      mode : 'time',
    			      labelsAngle : 45,
    			      tickFormatter : function(x){
    		                if(isNaN(x))
    		                {
    		                    return "";
    		                }
    		                else
    		                {
    		                     var x = parseInt(x);
    		                    var myDate = new Date(x);
    		                    var string = myDate.getFullYear();
    		                    string = string + "-" + (myDate.getMonth() + 1)+ "-" + myDate.getDate() + " " + myDate.getHours() + ":" + myDate.getMinutes() + ":" + myDate.getSeconds();
    		                    result = string;
    		                    return string;
    		                }
    		            }
    			    },
    			    yaxis : {
    			    	title : 'pressure'
    			    },
    			    y2axis : {
    			    	title : 'angle'
    			    },
    			    selection : {
    			      mode : 'x'
    			    },
    			    HtmlText : false,
    			    title : 'Time'
    			  };
    		function labelFn(label) {
    			return label;
    		}

		  function drawGraph (opts) {

    		o = Flotr._.extend(Flotr._.clone(options), opts || {});

    		return Flotr.draw(container, data, o);
  		}

  			graph = drawGraph();

    		}
    )
    (document.getElementById("graph")); </script>
</body>
</html>