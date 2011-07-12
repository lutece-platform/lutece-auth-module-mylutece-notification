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
package fr.paris.lutece.plugins.mylutece.modules.notification.rs;

import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationPlugin;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.NotificationService;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


/**
 *
 * DatabaseBlobStoreRest
 *
 */
@Path( RestConstants.BASE_PATH + NotificationPlugin.PLUGIN_NAME )
public class NotificationRest
{
    // CONSTANTS
    private static final String SLASH = "/";

    // PARAMETERS
    private static final String PARAMETER_NOTIFICATION_OBJECT = "notification_object";
    private static final String PARAMETER_NOTIFICATION_MESSAGE = "notification_message";
    private static final String PARAMETER_NOTIFICATION_SENDER = "notification_sender";
    private static final String PARAMETER_NOTIFICATION_RECEIVER = "notification_receiver";

    // MARKS
    private static final String MARK_BASE_URL = "base_url";

    // TEMPLATES
    private static final String TEMPLATE_WADL = "admin/plugins/mylutece/modules/notification/wadl.xml";

    // PATHS
    private static final String PATH_WADL = "wadl";
    private static final String PATH_NOTIFY = "notify";

    // MESSAGES
    private static final String MESSAGE_NOTIFICATION_REST = "MyLutce Notification Rest - ";
    private static final String MESSAGE_MANDATORY_FIELDS = "Every mandatory fields are not filled.";
    private static final String MESSAGE_NOTIFICATION_SUCCESSFUL = "Notification successful.";

    /**
     * Get the wadl.xml content
     * @param request {@link HttpServletRequest}
     * @return the content of wadl.xml
     */
    @GET
    @Path( PATH_WADL )
    @Produces( MediaType.APPLICATION_XML )
    public String getWADL( @Context
    HttpServletRequest request )
    {
        StringBuilder sbBase = new StringBuilder( AppPathService.getBaseUrl( request ) );

        if ( sbBase.toString(  ).endsWith( SLASH ) )
        {
            sbBase.deleteCharAt( sbBase.length(  ) - 1 );
        }

        sbBase.append( RestConstants.BASE_PATH + NotificationPlugin.PLUGIN_NAME );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BASE_URL, sbBase.toString(  ) );

        HtmlTemplate t = AppTemplateService.getTemplate( TEMPLATE_WADL, request.getLocale(  ), model );

        return t.getHtml(  );
    }

    /**
     * Notify
     * @param strNotificationObject the notification object
     * @param strNotificationMessage the notification message
     * @param strNotificationSender the sender
     * @param strNotificationReceiver the receiver
     * @return a message
     */
    @POST
    @Path( PATH_NOTIFY )
    @Produces( MediaType.TEXT_HTML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doNotify( @FormParam( PARAMETER_NOTIFICATION_OBJECT )
    String strNotificationObject, @FormParam( PARAMETER_NOTIFICATION_MESSAGE )
    String strNotificationMessage, @FormParam( PARAMETER_NOTIFICATION_SENDER )
    String strNotificationSender, @FormParam( PARAMETER_NOTIFICATION_RECEIVER )
    String strNotificationReceiver )
    {
        String strMessage = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strNotificationObject ) )
        {
            try
            {
                if ( StringUtils.isNotBlank( strNotificationReceiver ) )
                {
                    NotificationService.getService(  )
                                       .notify( strNotificationSender, strNotificationReceiver, strNotificationObject,
                        strNotificationMessage );
                }
                else
                {
                    NotificationService.getService(  )
                                       .notifyAll( strNotificationSender, strNotificationObject, strNotificationMessage );
                }

                strMessage = MESSAGE_NOTIFICATION_REST + MESSAGE_NOTIFICATION_SUCCESSFUL;
            }
            catch ( AppException e )
            {
                strMessage = e.getMessage(  );
                AppLogService.error( strMessage );
            }
        }
        else
        {
            strMessage = MESSAGE_NOTIFICATION_REST + MESSAGE_MANDATORY_FIELDS;
            AppLogService.error( strMessage );
        }

        return strMessage;
    }
}
