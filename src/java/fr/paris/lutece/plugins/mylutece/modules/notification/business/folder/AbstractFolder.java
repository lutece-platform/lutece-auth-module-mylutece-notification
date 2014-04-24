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

import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationService;

import org.apache.commons.lang.StringUtils;


/**
 *
 * AbstractFolder
 *
 */
public abstract class AbstractFolder implements IFolder
{
    private int _nIdFolder;
    private String _strUrlIcon;
    private FolderType _folderType;
    private String _strUserGuid;

    /**
     * Get the image folder
     * @return the image folder
     */
    public abstract String getImgFolder(  );

    /**
     * {@inheritDoc}
     */
    public void setIdFolder( int nIdFolder )
    {
        _nIdFolder = nIdFolder;
    }

    /**
     * {@inheritDoc}
     */
    public int getIdFolder(  )
    {
        return _nIdFolder;
    }

    /**
     * {@inheritDoc}
     */
    public void setUrlIcon( String strUrlIcon )
    {
        if ( StringUtils.isBlank( strUrlIcon ) )
        {
            _strUrlIcon = getImgFolder(  );
        }
        else
        {
            _strUrlIcon = strUrlIcon;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getUrlIcon(  )
    {
        return _strUrlIcon;
    }

    /**
     * {@inheritDoc}
     */
    public void setFolderType( FolderType folderType )
    {
        _folderType = folderType;
    }

    /**
     * {@inheritDoc}
     */
    public FolderType getFolderType(  )
    {
        return _folderType;
    }

    /**
     * {@inheritDoc}
     */
    public void setUserGuid( String strUserGuid )
    {
        _strUserGuid = strUserGuid;
    }

    /**
     * {@inheritDoc}
     */
    public String getUserGuid(  )
    {
        return _strUserGuid;
    }

    /**
     * {@inheritDoc}
     */
    public void setLabel( String strLabel )
    {
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberNotifications( String strUserGuid )
    {
        return NotificationService.getService(  ).getNumberNotifications( _nIdFolder, strUserGuid );
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberUnreadNotifications( String strUserGuid )
    {
        return NotificationService.getService(  ).getNumberUnreadNotifications( _nIdFolder, strUserGuid );
    }
}
