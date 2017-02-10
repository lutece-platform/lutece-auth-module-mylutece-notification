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

import java.sql.Timestamp;


/**
 *
 * Notification
 *
 */
public class Notification
{
    public static final String RESOURCE_TYPE = "MYLUTECE_NOTIFICATION";
    private int _nIdNotification;
    private int _nIdFolder;
    private String _strSender;
    private String _strUserGuidReceiver;
    private String _strObject;
    private String _strMessage;
    private Timestamp _dateCreation;
    private boolean _bIsRead;

    /**
     * Constructor
     */
    public Notification(  )
    {
        // Initialize the id folder to 1 (which is the inbox)
        _nIdFolder = 1;
    }

    /**
     * Set the id notification
     * @param nIdNotification the id notification
     */
    public void setIdNotification( int nIdNotification )
    {
        _nIdNotification = nIdNotification;
    }

    /**
     * Get the id notification
     * @return the id notification
     */
    public int getIdNotification(  )
    {
        return _nIdNotification;
    }

    /**
     * Set the object of the notification
     * @param strObject the object
     */
    public void setObject( String strObject )
    {
        _strObject = strObject;
    }

    /**
     * Get the object of the notification
     * @return the object of the notification
     */
    public String getObject(  )
    {
        return _strObject;
    }

    /**
     * Set the message of the notification
     * @param strMessage the message of the notification
     */
    public void setMessage( String strMessage )
    {
        _strMessage = strMessage;
    }

    /**
     * Get the message of the notification
     * @return the message
     */
    public String getMessage(  )
    {
        return _strMessage;
    }

    /**
     * Set the id folder of the notification
     * @param nIdFolder the id folder
     */
    public void setIdFolder( int nIdFolder )
    {
        _nIdFolder = nIdFolder;
    }

    /**
     * Get the id folder of the notification
     * @return the id folder
     */
    public int getIdFolder(  )
    {
        return _nIdFolder;
    }

    /**
     * Set the sender
     * @param strSender the sender
     */
    public void setSender( String strSender )
    {
        _strSender = strSender;
    }

    /**
     * Get the sender
     * @return the sender
     */
    public String getSender(  )
    {
        return _strSender;
    }

    /**
     * Set the user guid receiver
     * @param strUserGuidReceiver the user guid receiver
     */
    public void setUserGuidReceiver( String strUserGuidReceiver )
    {
        _strUserGuidReceiver = strUserGuidReceiver;
    }

    /**
     * Get the user guid receiver
     * @return the user guid receiver
     */
    public String getUserGuidReceiver(  )
    {
        return _strUserGuidReceiver;
    }

    /**
     * Set the date creation of the notification
     * @param dateCreation the date creation
     */
    public void setDateCreation( Timestamp dateCreation )
    {
        _dateCreation = dateCreation;
    }

    /**
     * Get the date creation of the notification
     * @return the date creation of the notification
     */
    public Timestamp getDateCreation(  )
    {
        return _dateCreation;
    }

    /**
     * Set the attribute is_read of the notification
     * @param bIsRead true if it is read, false otherwise
     */
    public void setIsRead( boolean bIsRead )
    {
        _bIsRead = bIsRead;
    }

    /**
     * Check if the notification is read or not
     * @return true if it is read, false otherwise
     */
    public boolean isRead(  )
    {
        return _bIsRead;
    }
}
