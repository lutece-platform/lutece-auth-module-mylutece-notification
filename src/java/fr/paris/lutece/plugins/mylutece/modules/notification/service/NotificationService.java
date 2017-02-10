/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
package fr.paris.lutece.plugins.mylutece.modules.notification.service;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.Notification;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.NotificationFilter;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.NotificationHome;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderOutbox;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;


/**
 *
 * NotificationService
 *
 */
public final class NotificationService
{
    private static final String BEAN_MYLUTECENOTIFICATION_NOTIFICATIONSERVICE = "mylutece-notification.notificationService";
    private static final int NOTIFICATION_OBJECT_MAX_SIZE = 100;
    private static final String SPACE = " ";

    // PROPERTIES
    private static final String PROPERTY_LABEL_NO_OBJECT = "mylutece-notification.labelNoObject";
    private static final String PROPERTY_WEBMASTER_EMAIL = "email.webmaster";

    // MESSAGES
    private static final String MESSAGE_INVALID_USER_GUID = "MyLutece Notification - Invalid Notification.";
    private static final String MESSAGE_USER_NOT_FOUND = "MyLutece Notification - User not found.";
    private static final String MESSAGE_OBJECT_SIZE_TOO_BIG = "MyLutece Notification - Object size too big (max : 100 characters).";

    // private static final String MESSAGE_ILLEGAL_CHARACTERS = "MyLutece Notification - Object/Message contain illegal characters.";

    /**
     * Private constructor
     */
    private NotificationService(  )
    {
    }

    /**
     * Get the instance of {@link NotificationService}
     * @return the instance of {@link NotificationService}
     */
    public static NotificationService getService(  )
    {
        return (NotificationService) SpringContextService.getPluginBean( NotificationPlugin.PLUGIN_NAME,
            BEAN_MYLUTECENOTIFICATION_NOTIFICATIONSERVICE );
    }

    /**
     * Find the notification by its primary key
     * @param nIdNotification the id notification
     * @return a {@link Notification}
     */
    public Notification findByPrimaryKey( int nIdNotification )
    {
        return NotificationHome.findByPrimaryKey( nIdNotification );
    }

    /**
     * Create a notification
     * @param notification the notification
     * @return the newly created notification id
     */
    public int create( Notification notification )
    {
        int nNewPrimaryKey = -1;

        if ( notification != null )
        {
            notification.setDateCreation( new Timestamp( new Date(  ).getTime(  ) ) );
            nNewPrimaryKey = NotificationHome.create( notification );
        }

        return nNewPrimaryKey;
    }

    /**
     * Update the notification
     * @param notification the notification
     */
    public void update( Notification notification )
    {
        if ( notification != null )
        {
            NotificationHome.update( notification );
        }
    }

    /**
     * Remove the notification
     * @param nIdNotification the id notification
     */
    public void remove( int nIdNotification )
    {
        NotificationHome.remove( nIdNotification );
    }

    /**
     * Find all notifications
     * @return a list of {@link Notification}
     */
    public List<Notification> findAll(  )
    {
        return NotificationHome.findAll(  );
    }

    /**
     * Find notifications by a filter
     * @param nFilter the filter
     * @return a list of {@link Notification}
     */
    public List<Notification> findByFilter( NotificationFilter nFilter )
    {
        return NotificationHome.findByFilter( nFilter );
    }

    /**
     * Find the notifications from a given user guid
     * @param strUserGuidReceiver the user guid receiver
     * @param nIdFolder the id folder
     * @return a list of {@link Notification}
     */
    public List<Notification> findNotificationsByUserGuid( String strUserGuidReceiver, int nIdFolder )
    {
        NotificationFilter nFilter = new NotificationFilter(  );
        nFilter.setUserGuidReceiver( strUserGuidReceiver );
        nFilter.setIdFolder( nIdFolder );

        return findByFilter( nFilter );
    }

    /**
     * Get the number of notifications of a demand
     * @param nIdFolder the id folder
     * @param strUserGuidReceiver the user guid receiver
     * @return the number of notifications
     */
    public int getNumberNotifications( int nIdFolder, String strUserGuidReceiver )
    {
        NotificationFilter nFilter = new NotificationFilter(  );
        nFilter.setIdFolder( nIdFolder );
        nFilter.setUserGuidReceiver( strUserGuidReceiver );

        return NotificationHome.countNotificationsByFilter( nFilter );
    }

    /**
     * Get the number of unread notifications of a demand
     * @param nIdFolder the id folder
     * @param strUserGuidReceiver the user guid receiver
     * @return the number of unread notifications
     */
    public int getNumberUnreadNotifications( int nIdFolder, String strUserGuidReceiver )
    {
        NotificationFilter nFilter = new NotificationFilter(  );
        nFilter.setIsRead( false );
        nFilter.setIdFolder( nIdFolder );
        nFilter.setUserGuidReceiver( strUserGuidReceiver );

        return NotificationHome.countNotificationsByFilter( nFilter );
    }

    /**
     * Get the list of LuteceUser
     * @return a {@link ReferenceList}
     */
    public ReferenceList getUsers(  )
    {
        ReferenceList listUsers = new ReferenceList(  );

        for ( LuteceUser user : SecurityService.getInstance(  ).getUsers(  ) )
        {
            listUsers.addItem( user.getName(  ),
                user.getUserInfo( LuteceUser.NAME_FAMILY ) + SPACE + user.getUserInfo( LuteceUser.NAME_GIVEN ) );
        }

        return listUsers;
    }

    /**
     * Notify an user
     * @param strUserGuidReceiver the receiver
     * @param strObject the Object of the message
     * @param strMessage the message
     */
    public void notify( String strUserGuidReceiver, String strObject, String strMessage )
    {
        notify( StringUtils.EMPTY, strUserGuidReceiver, strObject, strMessage );
    }

    /**
     * Notify an user
     * @param strSender the sender
     * @param strUserGuidReceiver the receiver
     * @param strObject the Object of the message
     * @param strMessage the message
     */
    public void notify( String strSender, String strUserGuidReceiver, String strObject, String strMessage )
    {
        // Do several check before notifying
        if ( StringUtils.isNotBlank( strUserGuidReceiver ) )
        {
            // Check if the receiver exists
            LuteceUser receiver = SecurityService.getInstance(  ).getUser( strUserGuidReceiver );

            if ( receiver != null )
            {
                // Init the object of the message
                String strNotificationObject = StringUtils.EMPTY;

                if ( StringUtils.isBlank( strObject ) )
                {
                    // If the object is not filled, then "[No object]" is put instead
                    strNotificationObject = AppPropertiesService.getProperty( PROPERTY_LABEL_NO_OBJECT );
                }
                else
                {
                    strNotificationObject = strObject;
                }

                // Init the sender
                String strSenderTmp = StringUtils.EMPTY;

                if ( StringUtils.isBlank( strSender ) )
                {
                    // If the sender is not filled, the email from webmaster.properties is filled instead
                    strSenderTmp = AppPropertiesService.getProperty( PROPERTY_WEBMASTER_EMAIL );
                }
                else
                {
                    strSenderTmp = strSender;
                }

                // Init the messages
                String strNotificationMessage = StringUtils.isNotEmpty( strMessage ) ? strMessage : StringUtils.EMPTY;

                // Check if the object is not too big
                if ( strNotificationObject.length(  ) <= NOTIFICATION_OBJECT_MAX_SIZE )
                {
                    // Check if the object and the message do not contain illegal characters
                    // TODO : The notifications should not accept illegal characters for security issues
                    /*
                    if ( !StringUtil.containsXssCharacters( strObject ) &&
                        !StringUtil.containsXssCharacters( strNotificationMessage ) )
                    {
                    Notification notification = new Notification(  );
                    notification.setSender( strSenderTmp );
                    notification.setUserGuidReceiver( strUserGuidReceiver );
                    notification.setObject( strNotificationObject );
                    notification.setMessage( strNotificationMessage );
                    create( notification );
                    
                    // Copy the notification to the outbox
                    copyNotificationToOutbox( notification );
                    }
                    else
                    {
                    throw new AppException( MESSAGE_ILLEGAL_CHARACTERS );
                    }
                    */
                    Notification notification = new Notification(  );
                    notification.setSender( strSenderTmp );
                    notification.setUserGuidReceiver( strUserGuidReceiver );
                    notification.setObject( strNotificationObject );
                    notification.setMessage( strNotificationMessage );
                    create( notification );

                    // Copy the notification to the outbox
                    copyNotificationToOutbox( notification );
                }
                else
                {
                    throw new AppException( MESSAGE_OBJECT_SIZE_TOO_BIG );
                }
            }
            else
            {
                throw new AppException( MESSAGE_USER_NOT_FOUND );
            }
        }
        else
        {
            throw new AppException( MESSAGE_INVALID_USER_GUID );
        }
    }

    /**
     * Notify all LuteceUser
     * @param strUserGuidSender the sender
     * @param strObject the object
     * @param strMessage the message
     */
    public void notifyAll( String strUserGuidSender, String strObject, String strMessage )
    {
        for ( LuteceUser user : SecurityService.getInstance(  ).getUsers(  ) )
        {
            notify( strUserGuidSender, user.getName(  ), strObject, strMessage );
        }
    }

    /**
     * Copy the notification to the outbox of the sender if the sender is a {@link LuteceUser}
     * @param notification the {@link Notification}
     */
    private void copyNotificationToOutbox( Notification notification )
    {
        // Check if the sender exists
        LuteceUser sender = SecurityService.getInstance(  ).getUser( notification.getSender(  ) );

        if ( sender != null )
        {
            // Invert the sender and the receiver so that the sender can see the message in his/her outbox
            String strSender = notification.getSender(  );
            String strReceiver = notification.getUserGuidReceiver(  );
            notification.setSender( strReceiver );
            notification.setUserGuidReceiver( strSender );
            notification.setIsRead( true );

            // Create a notification for the sender in his/her outbox to keep a track of his/her messages
            notification.setIdFolder( FolderOutbox.getId(  ) );
            create( notification );
        }
    }
}
