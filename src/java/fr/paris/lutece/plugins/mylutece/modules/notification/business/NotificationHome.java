/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.mylutece.modules.notification.business;

import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 *
 * NotificationHome
 *
 */
public final class NotificationHome
{
    private static final String BEAN_MYLUTECENOTIFICATION_NOTIFICATIONDAO = "mylutece-notification.notificationDAO";
    private static Plugin _plugin = PluginService.getPlugin( NotificationPlugin.PLUGIN_NAME );
    private static INotificationDAO _dao = (INotificationDAO) SpringContextService.getPluginBean( NotificationPlugin.PLUGIN_NAME,
            BEAN_MYLUTECENOTIFICATION_NOTIFICATIONDAO );

    /**
     * Private constructor - this class need not be instantiated
     */
    private NotificationHome(  )
    {
    }

    /**
     * Generates a new primary key
     * @return The new primary key
     */
    public static int newPrimaryKey(  )
    {
        return _dao.newPrimaryKey( _plugin );
    }

    /**
     * Insert a new record in the table.
     * @param notification instance of the Notification object to insert
     * @return the key of the newly created notification
     */
    public static int create( Notification notification )
    {
        return _dao.insert( notification, _plugin );
    }

    /**
     * Update the record in the table
     * @param notification the reference of the Notification
     */
    public static void update( Notification notification )
    {
        _dao.store( notification, _plugin );
    }

    /**
     * Delete a record from the table
     * @param nIdNotification int identifier of the notification to delete
     */
    public static void remove( int nIdNotification )
    {
        _dao.delete( nIdNotification, _plugin );
    }

    /**
     * Load the data from the table
     * @param nIdNotification The identifier of the notification
     * @return The instance of the Notification
     */
    public static Notification findByPrimaryKey( int nIdNotification )
    {
        return _dao.load( nIdNotification, _plugin );
    }

    /**
     * Find all notifications
     * @return a list of {@link Notification}
     */
    public static List<Notification> findAll(  )
    {
        return _dao.selectAll( _plugin );
    }

    /**
     * Find all notification by filter
     * @param nFilter the filter
     * @return a list of {@link Notification}
     */
    public static List<Notification> findByFilter( NotificationFilter nFilter )
    {
        return _dao.selectNotificationsByFilter( nFilter, _plugin );
    }

    /**
     * Count the number of notificationsd by filter
     * @param nFilter the filter
     * @return the number of notifications
     */
    public static int countNotificationsByFilter( NotificationFilter nFilter )
    {
        return _dao.countNotificationsByFilter( nFilter, _plugin );
    }
}
