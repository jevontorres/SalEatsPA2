<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Home</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"rel="stylesheet">
        <link rel="stylesheet" href="css/index.css" type="text/css">
        <script src="https://kit.fontawesome.com/3204349982.js" crossorigin="anonymous"></script>
        <% //TODO iterate the cookie and check if user registered
        Boolean registered = false;
        Boolean google = false;
        
        if ((String)request.getAttribute("out") != null){
        	registered = false;
        }
        if ((String)request.getAttribute("google") != null){
        	google = true;
        }
        int userId = 0;
        System.out.println(request);
        Cookie ck[]=request.getCookies();
	 	if (ck!=null && !ck[0].getValue().equals("") ){
		 	for(int i=0;i<ck.length;i++){
		 		System.out.println(ck[i].getValue());
		 		String newName = ((String) ck[i].getName()).replace('+',' ');
				if ( newName.compareTo("username")==0){
					registered = true;
					userId = i;
				}
			}
	 	} 

        %>
        
<!--         <style>
	    	#navbar {
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
        </style> -->
    </head>

    <body>
    
    	<div id="navbar">
    		
    		<div id="logo">
    			<a href="index.jsp">SalEats!</a>
    			 <%

    			 	if (registered){
    			 		String usernameFormatted = ck[userId].getValue().replace('+',' ');
	        			out.println("Hello, "+usernameFormatted+"!");
    			 	} 
    			 
    			 %>
    			 
    		</div>
    	
    		<div id="buttons">
    			<a href="index.jsp">Home</a>
    			<% if (!registered){
    				out.println("<a href='/PA2/WebContent/auth.jsp'>Login / Register</a>");}
    			%>
    			<% if (registered){
    				out.println("<a href='/PA2/LogoutDispatch'>Logout</a>");}
    			%>
    			
    		</div>
    	</div>
		
		<div id="container">
		
			<div id="banner"></div>
			<div id="uInput">
			
			<form action="/PA2/Search" method = "get">
				<select id="picker" name="picker">
					<option> Category</option>
					<option> Restaurant</option>
				</select>
				<input type="text" id="searchBox" name ="input">
				<button type="submit" name="search" id="search"><i class = 'fa fa-search' id="sButton"></i></button>
				<input type="radio" class="radio" name="sort" value="price" >
				Price
				<input type="radio" class="radio" name="sort" value="Rating">
				Rating
				<input type="radio" class="radio" name="sort" value="Review Count">
				Review Count
			</form>
				
				
			</div>

		</div>
    </body>

    </html>