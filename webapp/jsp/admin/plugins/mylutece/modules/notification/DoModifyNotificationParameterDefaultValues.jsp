<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="myluteceNotification" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.notification.web.NotificationJspBean" />

<% 
	myluteceNotification.init( request, myluteceNotification.RIGHT_MANAGE_ADMIN_SITE );
    response.sendRedirect( myluteceNotification.doModifyNotificationParameterDefaultValues( request ));
%>
