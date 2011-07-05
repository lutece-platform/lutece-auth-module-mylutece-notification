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

import org.apache.commons.lang.StringUtils;


/**
 *
 * NotificationFilter
 *
 */
public class NotificationFilter
{
    private static final int ALL_INT = -1;
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    private boolean _bIsWideSearch;
    private int _nIdFolder;
    private String _strUserGuidSender;
    private String _strUserGuidReceiver;
    private int _nIsRead;

    /**
     * Constructor
     */
    public NotificationFilter(  )
    {
        _bIsWideSearch = false;
        _nIdFolder = ALL_INT;
        _strUserGuidSender = StringUtils.EMPTY;
        _strUserGuidReceiver = StringUtils.EMPTY;
        _nIsRead = ALL_INT;
    }

    /**
     * Set true if the filter is applied to a wide search.
     * <br/>
     * In other words, the SQL query will use
     * <ul>
     * <li>SQL <b>OR</b> if it is applied to a wide search</li>
     * <li>SQL <b>AND</b> if it is not applied to a wide search</li>
     * </ul>
     * @param bIsWideSearch true if it a wide search, false otherwise
     */
    public void setIsWideSearch( boolean bIsWideSearch )
    {
        _bIsWideSearch = bIsWideSearch;
    }

    /**
     * Check if the filter is applied to a wide search or not.
     * <br/>
     * In other words, the SQL query will use
     * <ul>
     * <li>SQL <b>OR</b> if it is applied to a wide search</li>
     * <li>SQL <b>AND</b> if it is not applied to a wide search</li>
     * </ul>
     * @return true if it is applied to a wide search
     */
    public boolean getIsWideSearch(  )
    {
        return _bIsWideSearch;
    }

    /**
     * Set the id folder
     * @param nIdFolder the id folder
     */
    public void setIdFolder( int nIdFolder )
    {
        _nIdFolder = nIdFolder;
    }

    /**
     * Get the attribute id folder of the notification
     * @return true if it is read, false otherwise
     */
    public int getIdFolder(  )
    {
        return _nIdFolder;
    }

    /**
     * Check if the filter contains the attribute id folder
     * @return true if it contains, false otherwise
     */
    public boolean containsIdFolder(  )
    {
        return _nIdFolder != ALL_INT;
    }

    /**
     * Returns the UserGuidSender
     * @return The UserGuidSender
     */
    public String getUserGuidSender(  )
    {
        return _strUserGuidSender;
    }

    /**
     * Sets the UserGuidSender
     * @param strUserGuidSender The UserGuidSender
     */
    public void setUserGuidSender( String strUserGuidSender )
    {
        _strUserGuidSender = strUserGuidSender;
    }

    /**
     * Check if the filter contains the attribute UserGuidSender
     * @return true if it contains, false otherwise
     */
    public boolean containsUserGuidSender(  )
    {
        return StringUtils.isNotBlank( _strUserGuidSender );
    }

    /**
     * Returns the UserGuidReceiver
     * @return The UserGuidReceiver
     */
    public String getUserGuidReceiver(  )
    {
        return _strUserGuidReceiver;
    }

    /**
     * Sets the UserGuidReceiver
     * @param strUserGuidReceiver The UserGuidReceiver
     */
    public void setUserGuidReceiver( String strUserGuidReceiver )
    {
        _strUserGuidReceiver = strUserGuidReceiver;
    }

    /**
     * Check if the filter contains the attribute UserGuidReceiver
     * @return true if it contains, false otherwise
     */
    public boolean containsUserGuidReceiver(  )
    {
        return StringUtils.isNotBlank( _strUserGuidReceiver );
    }

    /**
     * Set the attribute is_read
     * @param bIsRead true if it is read, false otherwise
     */
    public void setIsRead( boolean bIsRead )
    {
        _nIsRead = bIsRead ? TRUE : FALSE;
    }

    /**
     * Get the attribute is_read of the notification
     * @return true if it is read, false otherwise
     */
    public boolean isRead(  )
    {
        return _nIsRead == TRUE;
    }

    /**
     * Check if the filter contains the attribute is_read
     * @return true if it contains, false otherwise
     */
    public boolean containsIsRead(  )
    {
        return _nIsRead != ALL_INT;
    }
}
