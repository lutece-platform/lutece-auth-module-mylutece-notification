<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="myluteceNotificationPortlet" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.notification.web.portlet.NotificationListPortletJspBean" />

<%
	myluteceNotificationPortlet.init( request, myluteceNotificationPortlet.RIGHT_MANAGE_ADMIN_SITE );
    response.sendRedirect( myluteceNotificationPortlet.doModify( request ) );
%>
