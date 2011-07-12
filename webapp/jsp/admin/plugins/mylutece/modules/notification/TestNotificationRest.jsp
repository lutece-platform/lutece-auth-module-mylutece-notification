<%@page import="fr.paris.lutece.portal.service.util.AppPathService"%>

<html>
    <head>
        <title>MyLutece Notification - REST webservices test page</title>
        <base href="<%= AppPathService.getBaseUrl( request ) %>" />
        <link rel="stylesheet" type="text/css" href="css/portal_admin.css" title="lutece_admin" />
    </head>
    <body>
        <div id="content" >
            <h1>MyLutece Notification - REST webservices test page </h1>
            <div class="content-box">
	            <div class="highlight-box">
	                <h2>View WADL</h2>
	                <form action="rest/mylutece-notification/wadl">
	                    <br/>
	                    <input class="button" type="submit" value="View WADL" />
	                </form>
	            </div>
	            
	            <div class="highlight-box">
	                <h2>Notify</h2>
	                <form action="rest/mylutece-notification/notify" method="post">
	                    <label for="notification_object">Objet * : </label>
	                    <input type="text" name="notification_object" size="50" maxlength="255" />
	                    <br />
	                    <label for="notification_message">Message : </label>
	                    <textarea name="notification_message"></textarea>
	                    <br />
	                    <label for="notification_sender">Sender : </label>
	                    <input type="text" name="notification_sender" size="50" maxlength="255" />
	                    <em>If the sender is void, then it will fetch the email from webmaster.properties.</em>
	                    <br />
	                    <label for="notification_sender">Receiver : </label>
	                    <input type="text" name="notification_receiver" size="50" maxlength="255" />
	                    <em>Put an empty string if you want to notify all users.</em>
	                    <br />
	                    <input class="button" type="submit" value="Send" />
	                </form>
	            </div>
        	</div>
        </div>
    </body>
</html>
