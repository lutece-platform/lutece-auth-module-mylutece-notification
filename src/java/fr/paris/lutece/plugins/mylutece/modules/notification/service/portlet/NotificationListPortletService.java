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
package fr.paris.lutece.plugins.mylutece.modules.notification.service.portlet;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.portlet.NotificationListPortlet;
import fr.paris.lutece.plugins.mylutece.modules.notification.business.portlet.NotificationListPortletHome;
import fr.paris.lutece.portal.business.portlet.PortletHome;


/**
 *
 * NotificationPortletService
 *
 */
public class NotificationListPortletService
{
    private static NotificationListPortletService _singleton;

    /**
     * Return the ThemeService singleton
     *
     * @return the ThemeService singleton
     */
    public static NotificationListPortletService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new NotificationListPortletService(  );
        }

        return _singleton;
    }

    /**
     * Init
     */
    public void init(  )
    {
    }

    /**
     * Get the portlet with the given portlet id
     * @param nPortletId the portlet id
     * @return a {@link NotificationListPortlet}
     */
    public NotificationListPortlet getPortlet( int nPortletId )
    {
        return (NotificationListPortlet) PortletHome.findByPrimaryKey( nPortletId );
    }

    /**
     * Get the portlet type id
     * @return the portlet type id
     */
    public String getPortletTypeId(  )
    {
        return NotificationListPortletHome.getInstance(  ).getPortletTypeId(  );
    }

    /**
     * Create a new {@link NotificationListPortlet}
     * @param portlet a {@link NotificationListPortlet}
     */
    public void create( NotificationListPortlet portlet )
    {
        NotificationListPortletHome.getInstance(  ).create( portlet );
    }

    /**
     * Update a {@link NotificationListPortlet}
     * @param portlet a {@link NotificationListPortlet}
     */
    public void update( NotificationListPortlet portlet )
    {
        NotificationListPortletHome.getInstance(  ).update( portlet );
    }

    /**
     * Remove a {@link NotificationListPortlet}
     * @param portlet a {@link NotificationListPortlet}
     */
    public void remove( NotificationListPortlet portlet )
    {
        NotificationListPortletHome.getInstance(  ).remove( portlet );
    }
}
