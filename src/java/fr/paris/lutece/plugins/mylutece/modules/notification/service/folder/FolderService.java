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
package fr.paris.lutece.plugins.mylutece.modules.notification.service.folder;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderHome;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderType;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.IFolder;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationPlugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 *
 * FolderService
 *
 */
public final class FolderService
{
    private static final String BEAN_MYLUTECENOTIFICATION_FOLDERSERVICE = "mylutece-notification.folderService";
    private List<FolderType> _listFolderTypes;

    /**
     * Private constructor
     */
    private FolderService(  )
    {
    }

    /**
     * Get the instance of {@link FolderService}
     * @return the instance of {@link FolderService}
     */
    public static FolderService getService(  )
    {
        return (FolderService) SpringContextService.getPluginBean( NotificationPlugin.PLUGIN_NAME,
            BEAN_MYLUTECENOTIFICATION_FOLDERSERVICE );
    }

    /**
     * Find the notification by its primary key
     * @param nIdFolder the id notification
     * @param locale the {@link Locale}
     * @return a {@link IFolder}
     */
    public IFolder findByPrimaryKey( int nIdFolder, Locale locale )
    {
        IFolder folder = FolderHome.findByPrimaryKey( nIdFolder );
        folder.setFolderType( locale );

        return folder;
    }

    /**
     * Create a folder
     * @param folder the folder
     * @return the newly created notification id
     */
    public int create( IFolder folder )
    {
        int nNewPrimaryKey = -1;

        if ( folder != null )
        {
            nNewPrimaryKey = FolderHome.create( folder );
        }

        return nNewPrimaryKey;
    }

    /**
     * Update the folder
     * @param folder the folder
     */
    public void update( IFolder folder )
    {
        if ( folder != null )
        {
            FolderHome.update( folder );
        }
    }

    /**
     * Remove the folder
     * @param nIdFolder the id folder
     */
    public void remove( int nIdFolder )
    {
        FolderHome.remove( nIdFolder );
    }

    /**
     * Find all folders
     * @param locale the {@link Locale}
     * @return a list of {@link IFolder}
     */
    public List<IFolder> findAll( Locale locale )
    {
        List<IFolder> listFolders = FolderHome.findAll(  );

        for ( IFolder folder : listFolders )
        {
            folder.setFolderType( locale );
        }

        return listFolders;
    }

    /**
     * Find the folders associated to the user guid
     * @param strUserGuid the user guid
     * @param locale {@link Locale}
     * @return a list of {@link IFolder}
     */
    public List<IFolder> findByUserGuid( String strUserGuid, Locale locale )
    {
        List<IFolder> listFolders = FolderHome.findByUserGuid( strUserGuid );

        for ( IFolder folder : listFolders )
        {
            folder.setFolderType( locale );
        }

        return listFolders;
    }

    /**
     * Get the list of folder types
     * @param locale the {@link Locale}
     * @return a list of {@link FolderType}
     */
    public List<FolderType> getFolderTypes( Locale locale )
    {
        if ( _listFolderTypes == null )
        {
            _listFolderTypes = new ArrayList<FolderType>(  );

            for ( IFolder folder : SpringContextService.getBeansOfType( IFolder.class ) )
            {
                folder.setFolderType( locale );
                _listFolderTypes.add( folder.getFolderType(  ) );
            }
        }

        return _listFolderTypes;
    }
}
