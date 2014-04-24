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

import java.util.Locale;


/**
 *
 * IFolder
 *
 */
public interface IFolder
{
    /**
     * Get the id folder
     * @return the id folder
     */
    int getIdFolder(  );

    /**
     * Set the id folder
     * @param nIdFolder the id folder
     */
    void setIdFolder( int nIdFolder );

    /**
     * Get the url icon
     * @return the url icon
     */
    String getUrlIcon(  );

    /**
     * Set the url icon
     * @param strUrlIcon the url icon
     */
    void setUrlIcon( String strUrlIcon );

    /**
     * Get the user guid
     * @return the user guid
     */
    String getUserGuid(  );

    /**
     * Set the user guid
     * @param strUserGuid the user guid
     */
    void setUserGuid( String strUserGuid );

    /**
     * Check if the folder is editable
     * @return true if it is editable, false otherwise
     */
    boolean isEditable(  );

    /**
     * Get the label
     * @return the label
     */
    String getLabel(  );

    /**
     * Set the label
     * @param strLabel the label
     */
    void setLabel( String strLabel );

    /**
     * Set the folder type
     * @param locale {@link Locale}
     */
    void setFolderType( Locale locale );

    /**
     * Get the folder type
     * @return the {@link FolderType}
     */
    FolderType getFolderType(  );

    /**
     * Get the number of notification given an user guid
     * @param strUserGuid the user guid
     * @return the number of notifications
     */
    int getNumberNotifications( String strUserGuid );

    /**
     * Get the number of unread notification given an user guid
     * @param strUserGuid the user guid
     * @return the number of unread notifications
     */
    int getNumberUnreadNotifications( String strUserGuid );
}
