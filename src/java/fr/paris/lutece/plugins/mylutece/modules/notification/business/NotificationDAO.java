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
package fr.paris.lutece.plugins.mylutece.modules.notification.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * NotificationDAO
 *
 */
public class NotificationDAO implements INotificationDAO
{
    // SQL QUERIES
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_notification ) FROM mylutece_notification ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO mylutece_notification (id_notification, id_folder, is_read, user_guid_sender, user_guid_receiver, object, message, date_creation) VALUES (?,?,?,?,?,?,?,?) ";
    private static final String SQL_QUERY_SELECT = " SELECT id_notification, id_folder, is_read, user_guid_sender, user_guid_receiver, object, message, date_creation FROM mylutece_notification WHERE id_notification = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE mylutece_notification SET id_folder = ?, is_read = ?, object = ?, message = ? WHERE id_notification = ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM mylutece_notification WHERE id_notification = ? ";
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id_notification, id_folder, is_read, user_guid_sender, user_guid_receiver, object, message, date_creation FROM mylutece_notification ";
    private static final String SQL_QUERY_COUNT = " SELECT count(id_folder) FROM mylutece_notification ";

    // FILTERS
    private static final String SQL_ORDER_BY = " ORDER BY ";
    private static final String SQL_DESC = " DESC ";
    private static final String SQL_OR = " OR ";
    private static final String SQL_AND = " AND ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_DATE_CREATION = " date_creation ";
    private static final String SQL_FILTER_ID_FOLDER = " id_folder = ? ";
    private static final String SQL_FILTER_IS_READ = " is_read = ? ";
    private static final String SQL_FILTER_USER_GUID_SENDER = " user_guid_sender = ? ";
    private static final String SQL_FILTER_USER_GUID_RECEIVER = " user_guid_receiver = ? ";

    /**
     * {@inheritDoc}
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey = 1;

        if ( daoUtil.next(  ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    public int insert( Notification notification, Plugin plugin )
    {
        int nKey = -1;

        if ( notification != null )
        {
            DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

            int nIndex = 1;
            notification.setIdNotification( newPrimaryKey( plugin ) );

            daoUtil.setInt( nIndex++, notification.getIdNotification(  ) );
            daoUtil.setInt( nIndex++, notification.getIdFolder(  ) );
            daoUtil.setBoolean( nIndex++, notification.isRead(  ) );
            daoUtil.setString( nIndex++, notification.getUserGuidSender(  ) );
            daoUtil.setString( nIndex++, notification.getUserGuidReceiver(  ) );
            daoUtil.setString( nIndex++, notification.getObject(  ) );
            daoUtil.setString( nIndex++, notification.getMessage(  ) );
            daoUtil.setTimestamp( nIndex++, notification.getDateCreation(  ) );

            daoUtil.executeUpdate(  );
            daoUtil.free(  );

            nKey = notification.getIdNotification(  );
        }

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    public Notification load( int nIdNotification, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nIdNotification );
        daoUtil.executeQuery(  );

        Notification notification = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            notification = new Notification(  );
            notification.setIdNotification( daoUtil.getInt( nIndex++ ) );
            notification.setIdFolder( daoUtil.getInt( nIndex++ ) );
            notification.setIsRead( daoUtil.getBoolean( nIndex++ ) );
            notification.setUserGuidSender( daoUtil.getString( nIndex++ ) );
            notification.setUserGuidReceiver( daoUtil.getString( nIndex++ ) );
            notification.setObject( daoUtil.getString( nIndex++ ) );
            notification.setMessage( daoUtil.getString( nIndex++ ) );
            notification.setDateCreation( daoUtil.getTimestamp( nIndex++ ) );
        }

        daoUtil.free(  );

        return notification;
    }

    /**
     * {@inheritDoc}
     */
    public void store( Notification notification, Plugin plugin )
    {
        if ( notification != null )
        {
            int nIndex = 1;

            DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

            daoUtil.setInt( nIndex++, notification.getIdFolder(  ) );
            daoUtil.setBoolean( nIndex++, notification.isRead(  ) );
            daoUtil.setString( nIndex++, notification.getObject(  ) );
            daoUtil.setString( nIndex++, notification.getMessage(  ) );

            daoUtil.setInt( nIndex++, notification.getIdNotification(  ) );
            daoUtil.executeUpdate(  );
            daoUtil.free(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nIdNotification, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nIdNotification );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
    * {@inheritDoc}
    */
    public List<Notification> selectAll( Plugin plugin )
    {
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECT_ALL );
        sbSQL.append( SQL_ORDER_BY );
        sbSQL.append( SQL_DATE_CREATION );
        sbSQL.append( SQL_DESC );

        List<Notification> listNotifications = new ArrayList<Notification>(  );
        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ), plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            Notification notification = new Notification(  );
            notification.setIdNotification( daoUtil.getInt( nIndex++ ) );
            notification.setIdFolder( daoUtil.getInt( nIndex++ ) );
            notification.setIsRead( daoUtil.getBoolean( nIndex++ ) );
            notification.setUserGuidSender( daoUtil.getString( nIndex++ ) );
            notification.setUserGuidReceiver( daoUtil.getString( nIndex++ ) );
            notification.setObject( daoUtil.getString( nIndex++ ) );
            notification.setMessage( daoUtil.getString( nIndex++ ) );
            notification.setDateCreation( daoUtil.getTimestamp( nIndex++ ) );
            listNotifications.add( notification );
        }

        daoUtil.free(  );

        return listNotifications;
    }

    /**
     * {@inheritDoc}
     */
    public List<Notification> selectNotificationsByFilter( NotificationFilter nFilter, Plugin plugin )
    {
        List<Notification> listNotifications = new ArrayList<Notification>(  );
        StringBuilder sbSQL = new StringBuilder( buildSQLQuery( nFilter, SQL_QUERY_SELECT_ALL ) );
        sbSQL.append( SQL_ORDER_BY );
        sbSQL.append( SQL_DATE_CREATION );
        sbSQL.append( SQL_DESC );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ), plugin );
        setFilterValues( nFilter, daoUtil );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            Notification notification = new Notification(  );
            notification.setIdNotification( daoUtil.getInt( nIndex++ ) );
            notification.setIdFolder( daoUtil.getInt( nIndex++ ) );
            notification.setIsRead( daoUtil.getBoolean( nIndex++ ) );
            notification.setUserGuidSender( daoUtil.getString( nIndex++ ) );
            notification.setUserGuidReceiver( daoUtil.getString( nIndex++ ) );
            notification.setObject( daoUtil.getString( nIndex++ ) );
            notification.setMessage( daoUtil.getString( nIndex++ ) );
            notification.setDateCreation( daoUtil.getTimestamp( nIndex++ ) );
            listNotifications.add( notification );
        }

        daoUtil.free(  );

        return listNotifications;
    }

    /**
     * {@inheritDoc}
     */
    public int countNotificationsByFilter( NotificationFilter nFilter, Plugin plugin )
    {
        int nNbNotifications = 0;
        StringBuilder sbSQL = new StringBuilder( buildSQLQuery( nFilter, SQL_QUERY_COUNT ) );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ), plugin );
        setFilterValues( nFilter, daoUtil );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nNbNotifications = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        return nNbNotifications;
    }

    /**
     * Build the SQL query with filter
     * @param nFilter the filter
     * @param strSQL the query
     * @return a SQL query
     */
    private String buildSQLQuery( NotificationFilter nFilter, String strSQL )
    {
        StringBuilder sbSQL = new StringBuilder( strSQL );
        int nIndex = 1;

        if ( nFilter.containsIdFolder(  ) )
        {
            nIndex = addSQLWhereOr( nFilter.getIsWideSearch(  ), sbSQL, nIndex );
            sbSQL.append( SQL_FILTER_ID_FOLDER );
        }

        if ( nFilter.containsIsRead(  ) )
        {
            nIndex = addSQLWhereOr( nFilter.getIsWideSearch(  ), sbSQL, nIndex );
            sbSQL.append( SQL_FILTER_IS_READ );
        }

        if ( nFilter.containsUserGuidSender(  ) )
        {
            nIndex = addSQLWhereOr( nFilter.getIsWideSearch(  ), sbSQL, nIndex );
            sbSQL.append( SQL_FILTER_USER_GUID_SENDER );
        }

        if ( nFilter.containsUserGuidReceiver(  ) )
        {
            addSQLWhereOr( nFilter.getIsWideSearch(  ), sbSQL, nIndex );
            sbSQL.append( SQL_FILTER_USER_GUID_RECEIVER );
        }

        return sbSQL.toString(  );
    }

    /**
     * Add a <b>WHERE</b> or a <b>OR</b> depending of the index.
     * <br/>
     * <ul>
     * <li>if <code>nIndex</code> == 1, then we add a <b>WHERE</b></li>
     * <li>if <code>nIndex</code> != 1, then we add a <b>OR</b></li>
     * </ul>
     * @param bIsWideSearch true if it is a wide search, false otherwise
     * @param sbSQL the SQL query
     * @param nIndex the index
     * @return the new index
     */
    private int addSQLWhereOr( boolean bIsWideSearch, StringBuilder sbSQL, int nIndex )
    {
        if ( nIndex == 1 )
        {
            sbSQL.append( SQL_WHERE );
        }
        else
        {
            sbSQL.append( bIsWideSearch ? SQL_OR : SQL_AND );
        }

        return nIndex + 1;
    }

    /**
     * Set the filter values on the DAOUtil
     * @param nFilter the filter
     * @param daoUtil the DAOUtil
     */
    private void setFilterValues( NotificationFilter nFilter, DAOUtil daoUtil )
    {
        int nIndex = 1;

        if ( nFilter.containsIdFolder(  ) )
        {
            daoUtil.setInt( nIndex++, nFilter.getIdFolder(  ) );
        }

        if ( nFilter.containsIsRead(  ) )
        {
            daoUtil.setBoolean( nIndex++, nFilter.isRead(  ) );
        }

        if ( nFilter.containsUserGuidSender(  ) )
        {
            daoUtil.setString( nIndex++, nFilter.getUserGuidSender(  ) );
        }

        if ( nFilter.containsUserGuidReceiver(  ) )
        {
            daoUtil.setString( nIndex++, nFilter.getUserGuidReceiver(  ) );
        }
    }
}
