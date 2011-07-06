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
package fr.paris.lutece.plugins.mylutece.modules.notification.business.portlet;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.Notification;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.NotificationFilter;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.IFolder;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationService;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.folder.FolderService;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * NotificationListPortlet
 *
 */
public class NotificationListPortlet extends Portlet
{
    // CONSTANTS
    private static final String SPACE = " ";
    private static final int TRUE = 1;
    private static final int FALSE = 0;

    // PROPERTIES
    private static final String PROPERTY_LABEL_FROM = "module.mylutece.notification.manage_notifications.labelFrom";
    private static final String PROPERTY_LABEL_TO = "module.mylutece.notification.manage_notifications.labelTo";
    private static final String PROPERTY_LABEL_OBJECT = "module.mylutece.notification.manage_notifications.labelObject";
    private static final String PROPERTY_LABEL_DATE_RECEIVED = "module.mylutece.notification.manage_notifications.labelDateReceived";
    private static final String PROPERTY_LABEL_SELECT_FOLDER = "module.mylutece.notification.manage_notifications.labelSelectFolder";
    private static final String PROPERTY_NOTIFICATION_ITEMS_PER_PAGE = "mylutece-notification.itemsPerPage";

    // PARAMETERS
    private static final String PARAMETER_ID_FOLDER = "id_folder";

    // MESSAGES
    private static final String MESSAGE_USER_NOT_SIGNED = "module.mylutece.notification.message.userNotSigned";

    // TAGS
    private static final String TAG_MYLUTECE_NOTIFICATION_LIST_PORTLET = "mylutece-notification-list-portlet";
    private static final String TAG_ERROR_MESSAGE = "error-message";
    private static final String TAG_FOLDERS_LIST = "folders-list";
    private static final String TAG_FOLDER = "folder";
    private static final String TAG_ID_FOLDER = "id-folder";
    private static final String TAG_PAGE_INDEX = "page-index";
    private static final String TAG_IS_LAS_PAGE_INDEX = "is-last-page-index";
    private static final String TAG_NB_ITEMS_PER_PAGE = "nb-items-per-page";
    private static final String TAG_FOLDER_LABEL = "folder-label";
    private static final String TAG_NOTIFICATIONS_LIST = "notifications-list";
    private static final String TAG_NOTIFICATION = "notification";
    private static final String TAG_ID_NOTIFICATION = "id-notification";
    private static final String TAG_NOTIFICATION_OBJECT = "object";
    private static final String TAG_NOTIFICATION_DATE_CREATION = "date-creation";
    private static final String TAG_NOTIFICATION_SENDER = "sender";
    private static final String TAG_NOTIFICATION_IS_READ = "is-read";
    private static final String TAG_LABEL_FROM = "label-from";
    private static final String TAG_LABEL_TO = "label-to";
    private static final String TAG_LABEL_OBJECT = "label-object";
    private static final String TAG_LABEL_DATE_RECEIVED = "label-date-received";
    private static final String TAG_LABEL_SELECT_FOLDER = "label-select-folder";

    // VARIABLES
    private boolean _bShowDateCreation;
    private boolean _bShowSender;

    /**
     * True if the portlet must show the date creation
     * @param bShowDateCreation True if the portlet must show the date creation
     */
    public void setShowDateCreation( boolean bShowDateCreation )
    {
        _bShowDateCreation = bShowDateCreation;
    }

    /**
     * True if the portlet must show the date creation
     * @return True if the portlet must show the date creation
     */
    public boolean getShowDateCreation(  )
    {
        return _bShowDateCreation;
    }

    /**
     * True if the portlet must show the sender
     * @param bShowSender True if the portlet must show the sender
     */
    public void setShowSender( boolean bShowSender )
    {
        _bShowSender = bShowSender;
    }

    /**
     * True if the portlet must show the sender
     * @return True if the portlet must show the sender
     */
    public boolean getShowSender(  )
    {
        return _bShowSender;
    }

    /**
     * {@inheritDoc}
     */
    public String getXml( HttpServletRequest request )
        throws SiteMessageException
    {
        StringBuffer sbXml = new StringBuffer(  );
        XmlUtil.beginElement( sbXml, TAG_MYLUTECE_NOTIFICATION_LIST_PORTLET );

        if ( request != null )
        {
            if ( SecurityService.isAuthenticationEnable(  ) )
            {
                LuteceUser user = null;

                try
                {
                    user = SecurityService.getInstance(  ).getRemoteUser( request );
                }
                catch ( UserNotSignedException e )
                {
                    String strErrorMessage = I18nService.getLocalizedString( MESSAGE_USER_NOT_SIGNED,
                            request.getLocale(  ) );
                    XmlUtil.addElement( sbXml, TAG_ERROR_MESSAGE, strErrorMessage );
                }

                if ( user != null )
                {
                    int nIdFolder = 1;
                    String strIdFolder = request.getParameter( PARAMETER_ID_FOLDER );

                    if ( StringUtils.isNotBlank( strIdFolder ) && StringUtils.isNumeric( strIdFolder ) )
                    {
                        nIdFolder = Integer.parseInt( strIdFolder );
                    }

                    int nPageIndex = 1;
                    String strPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, "1" );

                    if ( StringUtils.isNotBlank( strPageIndex ) && StringUtils.isNumeric( strPageIndex ) )
                    {
                        nPageIndex = Integer.parseInt( strPageIndex );
                    }

                    int nItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_NOTIFICATION_ITEMS_PER_PAGE, 10 );
                    boolean bIsLastPageIndex = true;

                    if ( ( nItemsPerPage * nPageIndex ) <= NotificationService.getService(  )
                                                                                  .getNumberNotifications( nIdFolder,
                                user.getName(  ) ) )
                    {
                        bIsLastPageIndex = false;
                    }

                    XmlUtil.addElement( sbXml, TAG_ERROR_MESSAGE, StringUtils.EMPTY );
                    XmlUtil.addElement( sbXml, TAG_ID_FOLDER, nIdFolder );
                    XmlUtil.addElement( sbXml, TAG_PAGE_INDEX, nPageIndex );
                    XmlUtil.addElement( sbXml, TAG_NB_ITEMS_PER_PAGE, nItemsPerPage );
                    XmlUtil.addElement( sbXml, TAG_IS_LAS_PAGE_INDEX, bIsLastPageIndex ? TRUE : FALSE );
                    XmlUtil.addElement( sbXml, TAG_ID_FOLDER, nIdFolder );
                    XmlUtil.addElement( sbXml, TAG_LABEL_FROM,
                        I18nService.getLocalizedString( PROPERTY_LABEL_FROM, request.getLocale(  ) ) );
                    XmlUtil.addElement( sbXml, TAG_LABEL_TO,
                        I18nService.getLocalizedString( PROPERTY_LABEL_TO, request.getLocale(  ) ) );
                    XmlUtil.addElement( sbXml, TAG_LABEL_OBJECT,
                        I18nService.getLocalizedString( PROPERTY_LABEL_OBJECT, request.getLocale(  ) ) );
                    XmlUtil.addElement( sbXml, TAG_LABEL_DATE_RECEIVED,
                        I18nService.getLocalizedString( PROPERTY_LABEL_DATE_RECEIVED, request.getLocale(  ) ) );
                    XmlUtil.addElement( sbXml, TAG_LABEL_SELECT_FOLDER,
                        I18nService.getLocalizedString( PROPERTY_LABEL_SELECT_FOLDER, request.getLocale(  ) ) );

                    getFoldersListXml( sbXml, user, request.getLocale(  ) );
                    getNotificationsListXml( sbXml, request, nIdFolder, nPageIndex );
                }
                else
                {
                    String strErrorMessage = I18nService.getLocalizedString( MESSAGE_USER_NOT_SIGNED,
                            request.getLocale(  ) );
                    XmlUtil.addElement( sbXml, TAG_ERROR_MESSAGE, strErrorMessage );
                }
            }
        }

        XmlUtil.endElement( sbXml, TAG_MYLUTECE_NOTIFICATION_LIST_PORTLET );

        return addPortletTags( sbXml );
    }

    /**
     * {@inheritDoc}
     */
    public String getXmlDocument( HttpServletRequest request )
        throws SiteMessageException
    {
        return XmlUtil.getXmlHeader(  ) + getXml( request );
    }

    /**
     * Get the list of folders in XML
     * @param sbXml the XML
     * @param user the user
     * @param locale {@link Locale}
     */
    private void getFoldersListXml( StringBuffer sbXml, LuteceUser user, Locale locale )
    {
        XmlUtil.beginElement( sbXml, TAG_FOLDERS_LIST );

        for ( IFolder folder : FolderService.getService(  ).findByUserGuid( user.getName(  ), locale ) )
        {
            XmlUtil.beginElement( sbXml, TAG_FOLDER );
            XmlUtil.addElement( sbXml, TAG_ID_FOLDER, folder.getIdFolder(  ) );
            XmlUtil.addElement( sbXml, TAG_FOLDER_LABEL, folder.getLabel(  ) );
            XmlUtil.endElement( sbXml, TAG_FOLDER );
        }

        XmlUtil.endElement( sbXml, TAG_FOLDERS_LIST );
    }

    /**
     * Get the list of notifications in XML
     * @param sbXml the XML
     * @param request {@link HttpServletRequest}
     * @param nIdFolder the id folder
     * @param nPageIndex the page index
     */
    private void getNotificationsListXml( StringBuffer sbXml, HttpServletRequest request, int nIdFolder, int nPageIndex )
    {
        XmlUtil.beginElement( sbXml, TAG_NOTIFICATIONS_LIST );

        NotificationFilter nFilter = new NotificationFilter(  );
        nFilter.setIdFolder( nIdFolder );
        nFilter.setLimitIndex( nPageIndex );
        nFilter.setLimitRange( AppPropertiesService.getPropertyInt( PROPERTY_NOTIFICATION_ITEMS_PER_PAGE, 10 ) );

        for ( Notification notification : NotificationService.getService(  ).findByFilter( nFilter ) )
        {
            XmlUtil.beginElement( sbXml, TAG_NOTIFICATION );
            XmlUtil.addElement( sbXml, TAG_ID_NOTIFICATION, notification.getIdNotification(  ) );
            XmlUtil.addElement( sbXml, TAG_NOTIFICATION_OBJECT, notification.getObject(  ) );

            if ( _bShowDateCreation )
            {
                XmlUtil.addElement( sbXml, TAG_NOTIFICATION_DATE_CREATION,
                    DateUtil.getDateString( notification.getDateCreation(  ), request.getLocale(  ) ) );
            }

            if ( _bShowSender )
            {
                LuteceUser sender = SecurityService.getInstance(  ).getUser( notification.getUserGuidSender(  ) );

                if ( sender != null )
                {
                    XmlUtil.addElement( sbXml, TAG_NOTIFICATION_SENDER,
                        sender.getUserInfo( LuteceUser.NAME_GIVEN ) + SPACE +
                        sender.getUserInfo( LuteceUser.NAME_FAMILY ) );
                }
            }

            XmlUtil.addElement( sbXml, TAG_NOTIFICATION_IS_READ, notification.isRead(  ) ? TRUE : FALSE );
            XmlUtil.endElement( sbXml, TAG_NOTIFICATION );
        }

        XmlUtil.endElement( sbXml, TAG_NOTIFICATIONS_LIST );
    }
}
