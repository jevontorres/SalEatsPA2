<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <!-- from skeleton -->
<!--         <meta content="301645777112-2rlc9gth0f5d4reimjcm9bf0kj7ahec0.apps.googleusercontent.com"
              name="google-signin-client_id"> -->
        <title>Login / Register</title>
        <link href="https://fonts.googleapis.com" rel="preconnect">
        <link crossorigin href="https://fonts.gstatic.com" rel="preconnect">
        <link
                href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
                rel="stylesheet">
        <script crossorigin="anonymous"
                src="https://kit.fontawesome.com/3204349982.js"></script>
        <script async defer src="https://apis.google.com/js/platform.js"></script>
        <link href="css/auth.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto"
              rel="stylesheet" type="text/css">
        <script src="https://apis.google.com/js/api:client.js"></script>
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		<meta content="309791551900-imshjqgq2lbpmign6babev7qc9cmdlda.apps.googleusercontent.com"
              name="google-signin-client_id">
            <style>
            #invalid-login {
                background-color: #FFD5D6;
                text-align: center;
                padding: 15px 0;
                margin-top: 15px;
                
                <%if (!(request.getAttribute("wrongpass") != null || request.getAttribute("wrongemail") !=null)) {
                	out.println("display: none;");
                }
                %>
                
            }
            #container { 
                display: flex;
                justify-content: center;
                align-items: center;
            }
            #login, #register {
                width: 30%;
                margin: 20px;
            }
            #container input[type=text], #container input[type=password] { 
                border: 1px solid gainsboro;
                border-radius: 3px;
                padding: 5px;
                width: 100%;
                margin: 5px 0 20px;
            }
            #container input[type=checkbox] {
                margin: 0 10px 20px;
            }
            #container button {
                color: white;
                font-weight: 300;
                font-size: 13px;
                border-radius: 3px;
                border: none;
                height: 30px;
                width: 100%;
                margin: 0;
            }
            #my-signin2{
            	text-align: left;
            	width: 20%;
            }
            #container button i {
                padding-right: 5px;
            }
            #login-btn, #register-btn {
                background-color: #BB0000;
            }
            #login-google-btn {
                background-color: #469FFE;
            }
        </style>

		<%
		System.out.println("google = "+request.getAttribute("google"));
		if (request.getAttribute("google")!=null) {
			System.out.println("her");
			request.getRequestDispatcher("WebContent/RegisterDispatch").forward(request,response);
		}
		%>

    </head>
    <body>
		
        <div id="invalid-login">
            <p>Invalid email or password. Or, bad Google login. Please try again.</p>
        </div>
        
        <div id="nav-bar">
            <div id="title">
                <a href="index.jsp">SalEats!</a>
            </div>
            
            <div id="nav">
                <a href="index.jsp">Home</a>
                <a href="#">Login / Register</a>
            </div>
        </div>
        
        <div id="container">
            <div id="login">
                <h1>Login</h1>
                
                <form action="/PA2/LoginDispatch" method="GET" id="login-form">
                    <label for="email-login">Email</label><br>
                    <input type="text" name="email-login" id="email-login"><br>
                    
                    <label for="password-login">Password</label><br>
                    <input type="password" name="password-login" id="password-login">
                    
                    <div id="login-btn-div">
                        <button type="submit" name="login-btn" id="login-btn"><i class="fa-solid fa-right-to-bracket"></i>Sign In</button>
                    </div>
                                     
                    <!-- <div class="g-signin2" data-onsuccess="onSignIn" data-onfailure="onFailure" data-theme="dark"></div> -->
                    <!-- <script>
					function onSignIn(googleUser) {
					 	var profile = googleUser.getBasicProfile();
						console.log('Name: ' + profile.getName());
					 	console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
					 	var googleRed = "http://localhost:8080/PA2/RegisterDispatch?" + "google="+encodeURIComponent("yes")+"&name-register="+encodeURIComponent(profile.getName())+"&email-register="+encodeURIComponent(profile.getEmail())+"&password-register=+&confirm-password-register=+&terms-checkbox=on";
					 	window.location.replace(googleRed);
					}
					function onFailure(res) {
						console.log('Fail: ',res); // This is null if the 'email' scope is not present.
					}
                    </script> -->
                    
                    <div id="my-signin2">
	                <script>
	                function onSuccess(googleUser) {
	                      var profile = googleUser.getBasicProfile();
	                      // redirect to the google dispatcher
	                      var googleRed = "http://localhost:8080/PA2/RegisterDispatch?" + "google="+encodeURIComponent("yes")+
						"&name-register="+encodeURIComponent(profile.getName())+
						"&email-register="+encodeURIComponent(profile.getEmail())+
						"&password-register=goog&confirm-password-register=goog&terms-checkbox=on";
	                      window.location.replace(googleRed);
	                    }
	                function onFailure(res) {
	                    console.log('Fail: ',res); // This is null if the 'email' scope is not present.
	                }
	                function renderButton() {
	                    gapi.signin2.render('my-signin2', {
	                      'scope': 'profile email',
						  'width': 300,
	                      'height': 30,
	                      'longtitle': true,
	                      'theme': 'dark',
	                      'onsuccess': onSuccess,
	                      'onfailure': onFailure
	                    });
	                }
	                </script>
					</div>
                <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
                </form>
            </div>
            
            <div id="register">
                <h1>Register</h1>
                
                
                
                <form action="/PA2/RegisterDispatch" method="GET" id="register-form">
                    <label for="email-register">Email</label><br>
                    <input type="text" name="email-register" id="email-register"><br>
                    
                    <label for="name-register">Name</label><br>
                    <input type="text" name="name-register" id="name-register"><br>
                                    
                    <label for="password-register">Password</label><br>
                    <input type="password" name="password-register" id="password-register"><br>
                    
                    <label for="confirm-password-register">Confirm Password</label><br>
                    <input type="password" name="confirm-password-register" id="confirm-password-register">
                    
                    <label><input type="checkbox" name="terms-checkbox" id="terms-checkbox">I have read and agree to all terms and conditions of SalEats.</label>
                    
                    <%
                    if (request.getAttribute("blank")!=null) {
                		out.println("<p><strong>Some field(s) left blank.</strong></p>");
                	}
                    if (request.getAttribute("dupname")!=null && request.getAttribute("dupemail")!=null) {
                    	out.println("<script> var auth2 = gapi.auth2.getAuthInstance(); auth2.signOut().then(function () {});auth2.disconnect();</script>");
                		out.println("<p><strong>Account with name and email exists, try signing in!</strong></p>");
                	}
                    else if (request.getAttribute("dupemail")!=null) {
                    	out.println("<script> var auth2 = gapi.auth2.getAuthInstance(); auth2.signOut().then(function () {});auth2.disconnect();</script>");
                		out.println("<p><strong>Account with email already exists, try again!</strong></p>");
                	}
                    else if (request.getAttribute("dupname")!=null) {
                		out.println("<p><strong>Account with name already exists, try again!</strong></p>");
                	}
                    if (request.getAttribute("inval")!=null) {
                		out.println("<p><strong>Invalid format for email.</strong></p>");
                	}
                    if (request.getAttribute("passmatch")!=null) {
                		out.println("<p><strong>Passwords don't match, try again!</strong></p>");
                	}
                    if (request.getAttribute("terms")!=null) {
                		out.println("<p><strong>Please agree to Terms & Conditions.</strong></p>");
                	}
               		%>
                    
                    
                    <div id="register-btn-div">
                        <button type="submit" name="register-btn" id="register-btn"><i class="fa-solid fa-user-plus"></i>Create Account</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>