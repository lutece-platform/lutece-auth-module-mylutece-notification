/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mylutece.modules.notification.web;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.Notification;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderArchive;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderInbox;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationPlugin;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationService;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.folder.FolderService;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.parameter.NotificationParameterService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.page.PageNotFoundException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.string.StringUtil;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * NotificationApp
 *
 */
public class NotificationApp implements XPageApplication
{
    // CONSTANTS
    private static final String COMMA = ",";

    // PROPERTIES
    private static final String PROPERTY_MANAGE_NOTIFICATIONS_PAGE_PATH = "module.mylutece.notification.manage_notifications.pagePathLabel";
    private static final String PROPERTY_MANAGE_NOTIFICATIONS_PAGE_TITLE = "module.mylutece.notification.manage_notifications.pageTitle";
    private static final String PROPERTY_LABEL_NO_OBJECT = "mylutece-notification.labelNoObject";
    private static final String PROPERTY_NOTIFICATION_ITEMS_PER_PAGE = "mylutece-notification.itemsPerPage";

    // PARAMETERS
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_ID_NOTIFICATION = "id_notification";
    private static final String PARAMETER_ID_NOTIFICATIONS = "id_notifications";
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_DO_ARCHIVE = "do_archive";
    private static final String PARAMETER_DELETE = "delete";
    private static final String PARAMETER_DO_RESTORE = "do_restore";
    private static final String PARAMETER_DO_CREATE = "do_create";
    private static final String PARAMETER_USER_GUID_RECEIVER = "user_guid_receiver";
    private static final String PARAMETER_OBJECT = "object";
    private static final String PARAMETER_MESSAGE = "message";
    private static final String PARAMETER_ID_FOLDER = "id_folder";
    private static final String PARAMETER_DO_DELETE = "do_delete";

    // MARKS
    private static final String MARK_NOTIFICATIONS_LIST = "notifications_list";
    private static final String MARK_NOTIFICATION = "notification";
    private static final String MARK_MYLUTECE_USER = "mylutece_user";
    private static final String MARK_MYLUTECE_USERS_LIST = "mylutece_users_list";
    private static final String MARK_FOLDERS_LIST = "folders_list";
    private static final String MARK_ID_FOLDER = "id_folder";
    private static final String MARK_NOTIFICATION_PAGE_CONTENT = "notification_page_content";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_IS_NOTIFICATIONS_SENDING_ENABLE = "is_notifications_sending_enable";
    private static final String MARK_CAN_BE_REPLIED = "can_be_replied";

    // ACTIONS
    private static final String ACTION_VIEW_NOTIFICATION = "view_notification";
    private static final String ACTION_DO_PROCESS = "do_process";
    private static final String ACTION_CREATE_NOTIFICATION = "create_notification";

    // TEMPLATES
    private static final String TEMPLATE_MANAGE_NOTIFICATIONS = "skin/plugins/mylutece/modules/notification/manage_notifications.html";
    private static final String TEMPLATE_VIEW_NOTIFICATION = "skin/plugins/mylutece/modules/notification/view_notification.html";
    private static final String TEMPLATE_CREATE_NOTIFICATION = "skin/plugins/mylutece/modules/notification/create_notification.html";
    private static final String TEMPLATE_FOLDERS_LIST = "skin/plugins/mylutece/modules/notification/folders_list.html";
    private static final String TEMPLATE_NOTIFICATION_PAGE_FRAMESET = "skin/plugins/mylutece/modules/notification/notification_page_frameset.html";

    // MESSAGES
    private static final String MESSAGE_CONFIRM_REMOVE_NOTIFICATIONS = "module.mylutece.notification.message.confirmRemoveNotifications";
    private static final String MESSAGE_INVALID_USER_GUID = "module.mylutece.notification.message.invalidUserGuid";
    private static final String MESSAGE_USER_NOT_FOUND = "module.mylutece.notification.message.userNotFound";
    private static final String MESSAGE_ILLEGAL_CHARACTERS = "module.mylutece.notification.message.illegalCharacters";
    private NotificationService _notificationService = NotificationService.getService(  );
    private FolderService _folderService = FolderService.getService(  );
    private NotificationParameterService _parameterService = NotificationParameterService.getService(  );

    /**
     * {@inheritDoc}
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws UserNotSignedException, SiteMessageException
    {
        LuteceUser user = getUser( request );
        XPage page = new XPage(  );

        String strIdFolder = request.getParameter( PARAMETER_ID_FOLDER );
        int nIdFolder = 1;

        if ( StringUtils.isNotBlank( strIdFolder ) )
        {
            nIdFolder = Integer.parseInt( strIdFolder );
        }

        String strMainHtml = StringUtils.EMPTY;

        String strAction = request.getParameter( PARAMETER_ACTION );

        if ( StringUtils.isNotBlank( strAction ) )
        {
            if ( ACTION_VIEW_NOTIFICATION.equals( strAction ) )
            {
                strMainHtml = getHtmlViewNotification( request, nIdFolder, user );
            }
            else if ( ACTION_CREATE_NOTIFICATION.equals( strAction ) )
            {
                strMainHtml = getHtmlCreateNotification( request, nIdFolder, user );
            }
            else if ( ACTION_DO_PROCESS.equals( strAction ) )
            {
                String strDoArchive = request.getParameter( PARAMETER_DO_ARCHIVE );
                String strDoRestore = request.getParameter( PARAMETER_DO_RESTORE );
                String strDoCreate = request.getParameter( PARAMETER_DO_CREATE );
                String strDelete = request.getParameter( PARAMETER_DELETE );
                String strDoDelete = request.getParameter( PARAMETER_DO_DELETE );

                if ( StringUtils.isNotBlank( strDoCreate ) )
                {
                    doCreateNotification( request, user );
                }
                else if ( StringUtils.isNotBlank( strDoArchive ) )
                {
                    doArchiveNotifications( request, user );
                }
                else if ( StringUtils.isNotBlank( strDoRestore ) )
                {
                    doRestoreNotifications( request, user );
                }
                else if ( StringUtils.isNotBlank( strDelete ) )
                {
                    getDeleteNotifications( request, nIdFolder );
                }
                else if ( StringUtils.isNotBlank( strDoDelete ) )
                {
                    doDeleteNotifications( request, user );
                }
            }
        }

        if ( StringUtils.isBlank( strMainHtml ) )
        {
            strMainHtml = getHtmlManageNotifications( request, nIdFolder, user );
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_FOLDERS_LIST, getHtmlListFolders( nIdFolder, user, request.getLocale(  ) ) );
        model.put( MARK_MYLUTECE_USER, user );
        model.put( MARK_NOTIFICATION_PAGE_CONTENT, strMainHtml );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_NOTIFICATION_PAGE_FRAMESET,
                request.getLocale(  ), model );

        page.setTitle( I18nService.getLocalizedString( PROPERTY_MANAGE_NOTIFICATIONS_PAGE_TITLE, request.getLocale(  ) ) );
        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_MANAGE_NOTIFICATIONS_PAGE_PATH,
                request.getLocale(  ) ) );
        page.setContent( template.getHtml(  ) );

        return page;
    }

    /**
     * Get the html code for the list of folders
     * @param nIdFolder the current id folder
     * @param user the {@link LuteceUser}
     * @param locale the {@link Locale}
     * @return the html code
     */
    private String getHtmlListFolders( int nIdFolder, LuteceUser user, Locale locale )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_FOLDERS_LIST, _folderService.findByUserGuid( user.getName(  ), locale ) );
        model.put( MARK_ID_FOLDER, nIdFolder );
        model.put( MARK_MYLUTECE_USER, user );
        model.put( MARK_IS_NOTIFICATIONS_SENDING_ENABLE, _parameterService.isNotificationSendingEnable(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_FOLDERS_LIST, locale, model );

        return template.getHtml(  );
    }

    /**
     * Get the html code for managing the notifications
     * @param request {@link HttpServletRequest}
     * @param nIdFolder the current id folder
     * @param user the {@link LuteceUser}
     * @return the html code
     */
    private String getHtmlManageNotifications( HttpServletRequest request, int nIdFolder, LuteceUser user )
    {
        int nItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_NOTIFICATION_ITEMS_PER_PAGE, 10 );
        UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
        url.addParameter( PARAMETER_PAGE, NotificationPlugin.PLUGIN_NAME );
        url.addParameter( PARAMETER_ID_FOLDER, nIdFolder );

        String strPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, "1" );

        List<Notification> listNotifications = _notificationService.findNotificationsByUserGuid( user.getName(  ),
                nIdFolder );
        Paginator<Notification> paginator = new Paginator<Notification>( listNotifications, nItemsPerPage,
                url.getUrl(  ), Paginator.PARAMETER_PAGE_INDEX, strPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_NOTIFICATIONS_LIST, paginator.getPageItems(  ) );
        model.put( MARK_MYLUTECE_USER, user );
        model.put( MARK_ID_FOLDER, nIdFolder );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_IS_NOTIFICATIONS_SENDING_ENABLE, _parameterService.isNotificationSendingEnable(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_NOTIFICATIONS, request.getLocale(  ),
                model );
        template.getHtml(  );

        return template.getHtml(  );
    }

    /**
     * Get the html code for viewing the notification
     * @param request {@link HttpServletRequest}
     * @param nIdFolder the current id folder
     * @param user the {@link LuteceUser}
     * @return the html code
     */
    private String getHtmlViewNotification( HttpServletRequest request, int nIdFolder, LuteceUser user )
    {
        String strHtml = StringUtils.EMPTY;
        String strIdNotification = request.getParameter( PARAMETER_ID_NOTIFICATION );

        if ( StringUtils.isNotBlank( strIdNotification ) && StringUtils.isNumeric( strIdNotification ) )
        {
            int nIdNotification = Integer.parseInt( strIdNotification );
            Notification notification = _notificationService.findByPrimaryKey( nIdNotification );

            if ( ( notification != null ) && notification.getUserGuidReceiver(  ).equals( user.getName(  ) ) )
            {
                // Check if the notification is indeed sent to the current user
                if ( !notification.isRead(  ) )
                {
                    // Mark the notification as READ
                    notification.setIsRead( true );
                    _notificationService.update( notification );
                }

                boolean bCanBeReplied = false;
                LuteceUser sender = SecurityService.getInstance(  ).getUser( notification.getUserGuidSender(  ) );

                if ( sender != null )
                {
                    bCanBeReplied = true;
                }

                Map<String, Object> model = new HashMap<String, Object>(  );
                model.put( MARK_NOTIFICATION, notification );
                model.put( MARK_MYLUTECE_USER, user );
                model.put( MARK_ID_FOLDER, nIdFolder );
                model.put( MARK_IS_NOTIFICATIONS_SENDING_ENABLE, _parameterService.isNotificationSendingEnable(  ) );
                model.put( MARK_CAN_BE_REPLIED, bCanBeReplied );

                HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_VIEW_NOTIFICATION,
                        request.getLocale(  ), model );
                strHtml = template.getHtml(  );
            }
        }

        return strHtml;
    }

    /**
     * Get the html code for creating a notification
     * @param request {@link HttpServletRequest}
     * @param nIdFolder the current id folder
     * @param user the {@link LuteceUser}
     * @return the html code
     */
    private String getHtmlCreateNotification( HttpServletRequest request, int nIdFolder, LuteceUser user )
    {
        String strHtml = StringUtils.EMPTY;

        if ( _parameterService.isNotificationSendingEnable(  ) )
        {
            // Check if the notifications sending is enable
            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( MARK_MYLUTECE_USERS_LIST, _notificationService.getUsers(  ) );
            model.put( MARK_MYLUTECE_USER, user );
            model.put( MARK_ID_FOLDER, nIdFolder );

            String strIdNotification = request.getParameter( PARAMETER_ID_NOTIFICATION );

            if ( StringUtils.isNotBlank( strIdNotification ) && StringUtils.isNumeric( strIdNotification ) )
            {
                int nIdNotification = Integer.parseInt( strIdNotification );
                Notification notification = _notificationService.findByPrimaryKey( nIdNotification );

                if ( notification != null )
                {
                    model.put( MARK_NOTIFICATION, notification );
                }
            }

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_NOTIFICATION,
                    request.getLocale(  ), model );

            strHtml = template.getHtml(  );
        }

        return strHtml;
    }

    /**
     * Do archive notifications
     * @param request {@link HttpServletRequest}
     * @param user the {@link LuteceUser}
     */
    private void doArchiveNotifications( HttpServletRequest request, LuteceUser user )
    {
        String[] listIdNotifications = request.getParameterValues( PARAMETER_ID_NOTIFICATIONS );

        if ( ( listIdNotifications != null ) && ( listIdNotifications.length > 0 ) )
        {
            for ( String strIdNotification : listIdNotifications )
            {
                if ( StringUtils.isNotBlank( strIdNotification ) && StringUtils.isNumeric( strIdNotification ) )
                {
                    int nIdNotification = Integer.parseInt( strIdNotification );
                    Notification notification = _notificationService.findByPrimaryKey( nIdNotification );

                    if ( ( notification != null ) && ( notification.getIdFolder(  ) != FolderArchive.getId(  ) ) &&
                            notification.getUserGuidReceiver(  ).equals( user.getName(  ) ) )
                    {
                        // Only the notification that are not in status archived can be archived
                        // Check the ownership of the notification
                        notification.setIdFolder( FolderArchive.getId(  ) );
                        _notificationService.update( notification );
                    }
                }
            }
        }
    }

    /**
     * Get the confirmation message for removing the notification
     * @param request {@link HttpServletRequest}
     * @param nIdFolder the current id folder
     * @throws SiteMessageException the confirmation message
     */
    private void getDeleteNotifications( HttpServletRequest request, int nIdFolder )
        throws SiteMessageException
    {
        String[] listIdNotifications = request.getParameterValues( PARAMETER_ID_NOTIFICATIONS );

        if ( ( listIdNotifications != null ) && ( listIdNotifications.length > 0 ) )
        {
            StringBuilder sbIdNotifications = new StringBuilder( ( listIdNotifications.length * 2 ) - 1 );

            for ( String strIdNotification : listIdNotifications )
            {
                sbIdNotifications.append( strIdNotification + COMMA );
            }

            UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
            url.addParameter( PARAMETER_PAGE, NotificationPlugin.PLUGIN_NAME );
            url.addParameter( PARAMETER_ACTION, ACTION_DO_PROCESS );
            url.addParameter( PARAMETER_DO_DELETE, PARAMETER_DO_DELETE );
            url.addParameter( PARAMETER_ID_NOTIFICATIONS, sbIdNotifications.toString(  ) );
            url.addParameter( PARAMETER_ID_FOLDER, nIdFolder );
            SiteMessageService.setMessage( request, MESSAGE_CONFIRM_REMOVE_NOTIFICATIONS,
                SiteMessage.TYPE_CONFIRMATION, url.getUrl(  ) );
        }
    }

    /**
     * Do delete the notifications
     * @param request {@link HttpServletRequest}
     * @param user the {@link LuteceUser}
     */
    private void doDeleteNotifications( HttpServletRequest request, LuteceUser user )
    {
        String strIdNotifications = request.getParameter( PARAMETER_ID_NOTIFICATIONS );

        if ( StringUtils.isNotBlank( strIdNotifications ) )
        {
            String[] listIdNotifications = strIdNotifications.split( COMMA );

            for ( String strIdNotification : listIdNotifications )
            {
                if ( StringUtils.isNotBlank( strIdNotification ) && StringUtils.isNumeric( strIdNotification ) )
                {
                    int nIdNotification = Integer.parseInt( strIdNotification );
                    Notification notification = _notificationService.findByPrimaryKey( nIdNotification );

                    if ( ( notification != null ) && ( notification.getIdFolder(  ) == FolderArchive.getId(  ) ) &&
                            notification.getUserGuidReceiver(  ).equals( user.getName(  ) ) )
                    {
                        // Check if the notifications are indeed in the archive box and their ownership
                        _notificationService.remove( nIdNotification );
                    }
                }
            }
        }
    }

    /**
     * Do restore the notifications
     * @param request {@link HttpServletRequest}
     * @param user the {@link LuteceUser}
     */
    private void doRestoreNotifications( HttpServletRequest request, LuteceUser user )
    {
        String[] listIdNotifications = request.getParameterValues( PARAMETER_ID_NOTIFICATIONS );

        if ( ( listIdNotifications != null ) && ( listIdNotifications.length > 0 ) )
        {
            for ( String strIdNotification : listIdNotifications )
            {
                if ( StringUtils.isNotBlank( strIdNotification ) && StringUtils.isNumeric( strIdNotification ) )
                {
                    int nIdNotification = Integer.parseInt( strIdNotification );
                    Notification notification = _notificationService.findByPrimaryKey( nIdNotification );

                    if ( ( notification != null ) && ( notification.getIdFolder(  ) == FolderArchive.getId(  ) ) &&
                            notification.getUserGuidReceiver(  ).equals( user.getName(  ) ) )
                    {
                        // Check if the notifications are indeed in the archive box and their ownership
                        notification.setIdFolder( FolderInbox.getId(  ) );
                        _notificationService.update( notification );
                    }
                }
            }
        }
    }

    /**
     * Do craete a notification
     * @param request {@link HttpServletRequest}
     * @param user the {@link LuteceUser}
     * @throws SiteMessageException a message if the notification is wrongly filled
     */
    private void doCreateNotification( HttpServletRequest request, LuteceUser user )
        throws SiteMessageException
    {
        if ( _parameterService.isNotificationSendingEnable(  ) )
        {
            String strUserGuid = request.getParameter( PARAMETER_USER_GUID_RECEIVER );

            if ( StringUtils.isNotBlank( strUserGuid ) )
            {
                LuteceUser userReceiver = SecurityService.getInstance(  ).getUser( strUserGuid );

                if ( userReceiver != null )
                {
                    String strObject = request.getParameter( PARAMETER_OBJECT );
                    String strNotificationObject = StringUtils.EMPTY;

                    if ( StringUtils.isBlank( strObject ) )
                    {
                        strNotificationObject = AppPropertiesService.getProperty( PROPERTY_LABEL_NO_OBJECT );
                    }
                    else
                    {
                        strNotificationObject = strObject;
                    }

                    String strMessage = request.getParameter( PARAMETER_MESSAGE );
                    strMessage = StringUtils.isNotEmpty( strMessage ) ? strMessage : StringUtils.EMPTY;

                    if ( !StringUtil.containsXssCharacters( strNotificationObject ) &&
                            !StringUtil.containsXssCharacters( strMessage ) )
                    {
                        _notificationService.notify( user.getName(  ), strUserGuid, strObject, strMessage );
                    }
                    else
                    {
                        SiteMessageService.setMessage( request, MESSAGE_ILLEGAL_CHARACTERS, SiteMessage.TYPE_STOP );
                    }
                }
                else
                {
                    SiteMessageService.setMessage( request, MESSAGE_USER_NOT_FOUND, SiteMessage.TYPE_STOP );
                }
            }
            else
            {
                SiteMessageService.setMessage( request, MESSAGE_INVALID_USER_GUID, SiteMessage.TYPE_STOP );
            }
        }
    }

    /**
     * Gets the user from the request
     * @param request The HTTP user
     * @return The Lutece User
     * @throws UserNotSignedException exception if user not connected
     */
    public LuteceUser getUser( HttpServletRequest request )
        throws UserNotSignedException
    {
        if ( SecurityService.isAuthenticationEnable(  ) )
        {
            LuteceUser user = SecurityService.getInstance(  ).getRemoteUser( request );

            if ( user == null )
            {
                throw new UserNotSignedException(  );
            }

            return user;
        }
        else
        {
            throw new PageNotFoundException(  );
        }
    }
}
