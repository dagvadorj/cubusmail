<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="com.cubusmail.core.ServletUtil"%>

<html>
	<head>
	
		<!--                                           -->
		<!-- Any title is fine                         -->
		<!--                                           -->
		<title>Cubusmail</title>

		<!--                                           -->
		<!-- The module reference below is the link    -->
		<!-- between html and your Web Toolkit module  -->		
		<!--                                           -->
		 <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
		<meta name='gwt:module' content='com.cubusmail.gwtui.Cubusmail'/>
		<meta name="gwt:property" content="locale=<%=ServletUtil.getDefaultLocale(request) %>">

		<link rel="stylesheet" type="text/css" href="css/cubusmail.css"/>

		<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="must-revalidate">

	</head>

	<!--                                           -->
	<!-- The body can have arbitrary html, or      -->
	<!-- we leave the body empty because we want   -->
	<!-- to create a completely dynamic ui         -->
	<!--                                           -->
	<body>

		<iframe id="__printingFrame" style="width:0;height:0;border:0"></iframe> 
		<!--                                            -->
		<!-- This script is required bootstrap stuff.   -->
		<!-- You can put it in the HEAD, but startup    -->
		<!-- is slightly faster if you include it here. -->
		<!--                                            -->

		<!--add loading indicator while the app is being loaded-->
		<div id="loading">
		    <div class="loading-indicator">
		        <img src="js/ext/resources/images/default/shared/large-loading.gif" width="32" height="32"
		             style="margin-right:8px;float:left;vertical-align:top;"/>Cubusmail<br/>
		        <span id="loading-msg">Loading styles and images...</span></div>
		</div>
		
		<!--include the Ext CSS, and use the gray theme-->
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css"/>
		<link id="theme" rel="stylesheet" type="text/css" href="<%=ServletUtil.getCSS()%>"/>
		<script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Loading Core API...';</script>
		
		<!--include the Ext Core API-->
		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		
		<!--include Ext -->
		<script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Loading UI Components...';</script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
		
		<!--include the application JS-->
		<script language="javascript" src="cubusmail/cubusmail.nocache.js"></script>
		
		<!--hide loading message-->
		<script type="text/javascript">Ext.get('loading').fadeOut({remove: true, duration:.25});</script>

	</body>
</html>
