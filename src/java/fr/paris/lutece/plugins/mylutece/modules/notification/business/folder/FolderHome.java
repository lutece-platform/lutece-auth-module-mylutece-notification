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
package fr.paris.lutece.plugins.mylutece.modules.notification.business.folder;

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
public final class FolderHome
{
    private static final String BEAN_MYLUTECENOTIFICATION_FOLDERDAO = "mylutece-notification.folderDAO";
    private static Plugin _plugin = PluginService.getPlugin( NotificationPlugin.PLUGIN_NAME );
    private static IFolderDAO _dao = (IFolderDAO) SpringContextService.getPluginBean( NotificationPlugin.PLUGIN_NAME,
            BEAN_MYLUTECENOTIFICATION_FOLDERDAO );

    /**
     * Private constructor - this class need not be instantiated
     */
    private FolderHome(  )
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
     * @param folder instance of the IFolder object to insert
     * @return the key of the newly created folder
     */
    public static int create( IFolder folder )
    {
        return _dao.insert( folder, _plugin );
    }

    /**
     * Update the record in the table
     * @param folder the folder
     */
    public static void update( IFolder folder )
    {
        _dao.store( folder, _plugin );
    }

    /**
     * Delete a record from the table
     * @param nIdFolder int identifier of the folder to delete
     */
    public static void remove( int nIdFolder )
    {
        _dao.delete( nIdFolder, _plugin );
    }

    /**
     * Load the data from the table
     * @param nIdFolder The identifier of the folder
     * @return The instance of the folder
     */
    public static IFolder findByPrimaryKey( int nIdFolder )
    {
        return _dao.load( nIdFolder, _plugin );
    }

    /**
     * Find all folders
     * @return a list of {@link IFolder}
     */
    public static List<IFolder> findAll(  )
    {
        return _dao.selectAll( _plugin );
    }

    /**
     * Find folders associated to the given user guid
     * @param strUserGuid the user guid
     * @return a list of {@link IFolder}
     */
    public static List<IFolder> findByUserGuid( String strUserGuid )
    {
        return _dao.selectByUserGuid( strUserGuid, _plugin );
    }
}
