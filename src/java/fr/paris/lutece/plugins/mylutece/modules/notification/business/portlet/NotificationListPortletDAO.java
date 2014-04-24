/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 *
 * NotificationPortletDAO
 *
 */
public final class NotificationListPortletDAO implements INotificationListPortletDAO
{
    private static final String SQL_QUERY_INSERT = " INSERT INTO mylutece_notification_list_portlet (id_portlet, show_date_creation, show_sender ) VALUES ( ?,?,? ) ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM mylutece_notification_list_portlet WHERE id_portlet = ? ";
    private static final String SQL_QUERY_SELECT = " SELECT id_portlet, show_date_creation, show_sender FROM mylutece_notification_list_portlet WHERE id_portlet = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE mylutece_notification_list_portlet SET show_date_creation = ?, show_sender = ? WHERE id_portlet = ? ";

    /**
     * {@inheritDoc}
     */
    public void insert( Portlet portlet )
    {
        NotificationListPortlet p = (NotificationListPortlet) portlet;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, p.getId(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowDateCreation(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowSender(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nPortletId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setInt( 1, nPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public Portlet load( int nPortletId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setInt( 1, nPortletId );
        daoUtil.executeQuery(  );

        NotificationListPortlet portlet = new NotificationListPortlet(  );

        int nIndex = 1;

        if ( daoUtil.next(  ) )
        {
            portlet.setId( daoUtil.getInt( nIndex++ ) );
            portlet.setShowDateCreation( daoUtil.getBoolean( nIndex++ ) );
            portlet.setShowSender( daoUtil.getBoolean( nIndex++ ) );
        }

        daoUtil.free(  );

        return portlet;
    }

    /**
     * {@inheritDoc}
     */
    public void store( Portlet portlet )
    {
        NotificationListPortlet p = (NotificationListPortlet) portlet;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );
        int nIndex = 1;

        daoUtil.setBoolean( nIndex++, p.getShowDateCreation(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowSender(  ) );

        daoUtil.setInt( nIndex++, p.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
