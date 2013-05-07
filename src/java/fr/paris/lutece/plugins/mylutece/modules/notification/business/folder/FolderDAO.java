/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.mylutece.modules.notification.business.folder;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * FolderDAO
 *
 */
public class FolderDAO implements IFolderDAO
{
    // SQL QUERIES
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_notification ) FROM mylutece_notification_folder ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO mylutece_notification_folder (id_folder, type_class_name, folder_label, url_icon, user_guid) VALUES (?,?,?,?,?) ";
    private static final String SQL_QUERY_SELECT = " SELECT type_class_name, id_folder, folder_label, url_icon, user_guid FROM mylutece_notification_folder WHERE id_folder = ? ";
    private static final String SQL_QUERY_SELECT_ALL = " SELECT type_class_name, id_folder, folder_label, url_icon, user_guid FROM mylutece_notification_folder ORDER BY folder_label ASC, id_folder ASC ";
    private static final String SQL_QUERY_SELECT_BY_USER_GUID = " SELECT type_class_name, id_folder, folder_label, url_icon, user_guid FROM mylutece_notification_folder WHERE ( user_guid = '' OR user_guid = ? ) ORDER BY folder_label ASC, id_folder ASC ";
    private static final String SQL_QUERY_UPDATE = " UPDATE mylutece_notification_folder SET folder_label = ?, url_icon = ?, user_guid = ? WHERE id_folder = ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM mylutece_notification_folder WHERE id_folder = ? ";

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
    public int insert( IFolder folder, Plugin plugin )
    {
        int nKey = -1;

        if ( folder != null )
        {
            DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
            int nIndex = 1;
            folder.setIdFolder( newPrimaryKey( plugin ) );
            daoUtil.setInt( nIndex++, folder.getIdFolder(  ) );
            daoUtil.setString( nIndex++, folder.getClass(  ).getName(  ) );
            daoUtil.setString( nIndex++, folder.getLabel(  ) );
            daoUtil.setString( nIndex++, folder.getUrlIcon(  ) );
            daoUtil.setString( nIndex++, folder.getUserGuid(  ) );

            daoUtil.executeUpdate(  );
            daoUtil.free(  );
            nKey = folder.getIdFolder(  );
        }

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    public List<IFolder> selectAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.executeQuery(  );

        List<IFolder> listFolders = new ArrayList<IFolder>(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            IFolder folder = null;
            boolean bException = false;

            try
            {
                folder = (IFolder) Class.forName( daoUtil.getString( nIndex++ ) ).newInstance(  );
            }
            catch ( ClassNotFoundException e )
            {
                // class doesn't exist
                AppLogService.error( e );
                bException = true;
            }
            catch ( InstantiationException e )
            {
                // Class is abstract or is an interface or haven't accessible
                // builder
                AppLogService.error( e );
                bException = true;
            }
            catch ( IllegalAccessException e )
            {
                // can't access to rhe class
                AppLogService.error( e );
                bException = true;
            }

            if ( bException )
            {
                daoUtil.free(  );

                return null;
            }

            folder.setIdFolder( daoUtil.getInt( nIndex++ ) );
            folder.setLabel( daoUtil.getString( nIndex++ ) );
            folder.setUrlIcon( daoUtil.getString( nIndex++ ) );
            folder.setUserGuid( daoUtil.getString( nIndex++ ) );
            listFolders.add( folder );
        }

        daoUtil.free(  );

        return listFolders;
    }

    /**
     * {@inheritDoc}
     */
    public IFolder load( int nIdFolder, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nIdFolder );
        daoUtil.executeQuery(  );

        IFolder folder = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            boolean bException = false;

            try
            {
                folder = (IFolder) Class.forName( daoUtil.getString( nIndex++ ) ).newInstance(  );
            }
            catch ( ClassNotFoundException e )
            {
                // class doesn't exist
                AppLogService.error( e );
                bException = true;
            }
            catch ( InstantiationException e )
            {
                // Class is abstract or is an interface or haven't accessible
                // builder
                AppLogService.error( e );
                bException = true;
            }
            catch ( IllegalAccessException e )
            {
                // can't access to rhe class
                AppLogService.error( e );
                bException = true;
            }

            if ( bException )
            {
                daoUtil.free(  );

                return null;
            }

            folder.setIdFolder( daoUtil.getInt( nIndex++ ) );
            folder.setLabel( daoUtil.getString( nIndex++ ) );
            folder.setUrlIcon( daoUtil.getString( nIndex++ ) );
            folder.setUserGuid( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return folder;
    }

    /**
     * {@inheritDoc}
     */
    public void store( IFolder folder, Plugin plugin )
    {
        if ( folder != null )
        {
            int nIndex = 1;

            DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

            daoUtil.setString( nIndex++, folder.getLabel(  ) );
            daoUtil.setString( nIndex++, folder.getUrlIcon(  ) );
            daoUtil.setString( nIndex++, folder.getUserGuid(  ) );

            daoUtil.setInt( nIndex++, folder.getIdFolder(  ) );
            daoUtil.executeUpdate(  );
            daoUtil.free(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nIdFolder, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );

        daoUtil.setInt( 1, nIdFolder );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public List<IFolder> selectByUserGuid( String strUserGuid, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER_GUID, plugin );
        daoUtil.setString( 1, strUserGuid );
        daoUtil.executeQuery(  );

        List<IFolder> listFolders = new ArrayList<IFolder>(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            IFolder folder = null;
            boolean bException = false;

            try
            {
                folder = (IFolder) Class.forName( daoUtil.getString( nIndex++ ) ).newInstance(  );
            }
            catch ( ClassNotFoundException e )
            {
                // class doesn't exist
                AppLogService.error( e );
                bException = true;
            }
            catch ( InstantiationException e )
            {
                // Class is abstract or is an interface or haven't accessible
                // builder
                AppLogService.error( e );
                bException = true;
            }
            catch ( IllegalAccessException e )
            {
                // can't access to rhe class
                AppLogService.error( e );
                bException = true;
            }

            if ( bException )
            {
                daoUtil.free(  );

                return null;
            }

            folder.setIdFolder( daoUtil.getInt( nIndex++ ) );
            folder.setLabel( daoUtil.getString( nIndex++ ) );
            folder.setUrlIcon( daoUtil.getString( nIndex++ ) );
            folder.setUserGuid( daoUtil.getString( nIndex++ ) );
            listFolders.add( folder );
        }

        daoUtil.free(  );

        return listFolders;
    }
}
