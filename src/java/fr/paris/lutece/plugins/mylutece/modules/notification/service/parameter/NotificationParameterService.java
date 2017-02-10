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
package fr.paris.lutece.plugins.mylutece.modules.notification.service.parameter;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.parameter.NotificationParameterHome;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationPlugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;


/**
 *
 * MyTasksParameterService
 *
 */
public final class NotificationParameterService
{
    private static final String BEAN_MYLUTECENOTIFICATION_NOTIFICATIONPARAMETERSERVICE = "mylutece-notification.notificationParameterService";
    private static final String PARAMETER_ENABLE_NOTIFICATIONS_SENDING = "enable_notifications_sending";

    /**
     * Private constructor
     */
    private NotificationParameterService(  )
    {
    }

    /**
     * Get the instance of NotificationParameterService
     * @return the instance of NotificationParameterService
     */
    public static NotificationParameterService getService(  )
    {
        return (NotificationParameterService) SpringContextService.getPluginBean( NotificationPlugin.PLUGIN_NAME,
            BEAN_MYLUTECENOTIFICATION_NOTIFICATIONPARAMETERSERVICE );
    }

    /**
     * Load all the parameter default values
     * @return a list of {@link ReferenceItem}
     */
    public ReferenceList getParamDefaultValues(  )
    {
        return NotificationParameterHome.findAll(  );
    }

    /**
     * Get the parameter default value given a parameter key
     * @param strParameterKey the parameter key
     * @return a {@link ReferenceItem}
     */
    public ReferenceItem getParamDefaultValue( String strParameterKey )
    {
        return NotificationParameterHome.findByKey( strParameterKey );
    }

    /**
     * Update the parameter
     * @param param The parameter
     */
    public void update( ReferenceItem param )
    {
        NotificationParameterHome.update( param );
    }

    /**
     * Check if the notifications sending is enable or not
     * @return true if it is enable, false otherwise
     */
    public boolean isNotificationSendingEnable(  )
    {
        boolean bIsEnable = false;
        ReferenceItem item = getParamDefaultValue( PARAMETER_ENABLE_NOTIFICATIONS_SENDING );

        if ( item != null )
        {
            bIsEnable = item.isChecked(  );
        }

        return bIsEnable;
    }
}
