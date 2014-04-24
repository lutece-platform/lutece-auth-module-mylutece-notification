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
package fr.paris.lutece.plugins.mylutece.modules.notification.business.folder;

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 *
 * IFolderDAO
 *
 */
public interface IFolderDAO
{
    /**
     * Create a new primary key
     * @param plugin {@link Plugin}
     * @return the new primary key
     */
    int newPrimaryKey( Plugin plugin );

    /**
     * Insert a new folder
     * @param folder the folder
     * @param plugin {@link Plugin}
     * @return the new primary key
     */
    int insert( IFolder folder, Plugin plugin );

    /**
     * Load a folder
     * @param nIdFolder the ID of the folder
     * @param plugin {@link Plugin}
     * @return a {@link IFolder}
     */
    IFolder load( int nIdFolder, Plugin plugin );

    /**
     * Update a folder
     * @param folder the folder
     * @param plugin {@link Plugin}
     */
    void store( IFolder folder, Plugin plugin );

    /**
     * Find all folders
     * @param plugin {@link Plugin}
     * @return a list of {@link IFolder}
     */
    List<IFolder> selectAll( Plugin plugin );

    /**
     * Delete a folder
     * @param nIdFolder the ID folder
     * @param plugin {@link Plugin}
     */
    void delete( int nIdFolder, Plugin plugin );

    /**
     * Find folders associated to the user guid
     * @param strUserGuid the user guid
     * @param plugin {@link Plugin}
     * @return a list of {@link IFolder}
     */
    List<IFolder> selectByUserGuid( String strUserGuid, Plugin plugin );
}
