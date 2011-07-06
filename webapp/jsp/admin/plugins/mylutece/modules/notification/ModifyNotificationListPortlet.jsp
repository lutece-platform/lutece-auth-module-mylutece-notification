<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../../../PortletAdminHeader.jsp" />

<jsp:useBean id="myluteceNotificationPortlet" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.notification.web.portlet.NotificationListPortletJspBean" />

<% myluteceNotificationPortlet.init( request, myluteceNotificationPortlet.RIGHT_MANAGE_ADMIN_SITE); %>
<%= myluteceNotificationPortlet.getModify( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>
