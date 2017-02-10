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
package fr.paris.lutece.plugins.mylutece.modules.notification.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 *
 * INotificationDAO
 *
 */
public interface INotificationDAO
{
    /**
    * Generates a new primary key
    * @param plugin The Plugin
    * @return The new primary key
    */
    int newPrimaryKey( Plugin plugin );

    /**
     * Insert a new record in the table.
     * @param notification instance of the Notification object to insert
     * @param plugin the Plugin
     * @return the key of the newly created notification
     */
    int insert( Notification notification, Plugin plugin );

    /**
    * Update the record in the table
    * @param notification the reference of the Notification
    * @param plugin the Plugin
    */
    void store( Notification notification, Plugin plugin );

    /**
     * Delete a record from the table
     * @param nIdNotification int identifier of the notification to delete
     * @param plugin the Plugin
     */
    void delete( int nIdNotification, Plugin plugin );

    /**
     * Load the data from the table
     * @param nIdNotification The identifier of the notification
     * @param plugin the Plugin
     * @return The instance of the Notification
     */
    Notification load( int nIdNotification, Plugin plugin );

    /**
     * Find all notification
     * @param plugin {@link Plugin}
     * @return a list of {@link Notification}
     */
    List<Notification> selectAll( Plugin plugin );

    /**
     * Find by filter
     * @param nFilter the filter
     * @param plugin {@link Plugin}
     * @return a list of {@link Notification}
     */
    List<Notification> selectNotificationsByFilter( NotificationFilter nFilter, Plugin plugin );

    /**
     * Count the number of notificationsd by filter
     * @param nFilter the filter
     * @param plugin {@link Plugin}
     * @return the number of notifications
     */
    int countNotificationsByFilter( NotificationFilter nFilter, Plugin plugin );
}
