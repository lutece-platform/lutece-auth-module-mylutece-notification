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
package fr.paris.lutece.plugins.mylutece.modules.notification.web.portlet;

import fr.paris.lutece.plugins.mylutece.modules.notification.business.portlet.NotificationListPortlet;
import fr.paris.lutece.plugins.mylutece.modules.notification.service.portlet.NotificationListPortletService;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.web.portlet.PortletJspBean;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * NotificationPortletJspBean
 *
 */
public class NotificationListPortletJspBean extends PortletJspBean
{
    // RIGHT
    public static final String RIGHT_MANAGE_ADMIN_SITE = "CORE_ADMIN_SITE";

    // CONSTANTS
    private static final String PARENT_DIRECTORY = "../";

    // PARAMETERS
    private static final String PARAMETER_SHOW_DATE_CREATION = "show_date_creation";
    private static final String PARAMETER_SHOW_SENDER = "show_sender";

    // MARKS
    private static final String MARK_SHOW_DATE_CREATION = "show_date_creation";
    private static final String MARK_SHOW_SENDER = "show_sender";

    // MESSAGES
    private static final String MESSAGE_NOT_NUMERIC = "module.mylutece.notification.message.notNumeric";
    private static final String MESSAGE_PORTLET_NOT_FOUND = "module.mylutece.notification.message.portletNotFound";

    /**
     * Returns portlet creation form
     * @param request request
     * @return Html form
     */
    public String getCreate( HttpServletRequest request )
    {
        String strPageId = request.getParameter( PARAMETER_PAGE_ID );
        String strPortletTypeId = request.getParameter( PARAMETER_PORTLET_TYPE_ID );

        Map<String, Object> model = new HashMap<String, Object>(  );

        HtmlTemplate template = getCreateTemplate( strPageId, strPortletTypeId, model );

        return template.getHtml(  );
    }

    /**
     * Returns portlet modification form
     * @param request request
     * @return Html form
     */
    public String getModify( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strPortletId = request.getParameter( PARAMETER_PORTLET_ID );

        if ( StringUtils.isNotBlank( strPortletId ) && StringUtils.isNumeric( strPortletId ) )
        {
            int nPortletId = Integer.parseInt( strPortletId );
            NotificationListPortlet portlet = NotificationListPortletService.getService(  ).getPortlet( nPortletId );

            if ( portlet != null )
            {
                Map<String, Object> model = new HashMap<String, Object>(  );
                model.put( MARK_SHOW_DATE_CREATION, portlet.getShowDateCreation(  ) );
                model.put( MARK_SHOW_SENDER, portlet.getShowSender(  ) );

                HtmlTemplate template = getModifyTemplate( portlet, model );

                strUrl = template.getHtml(  );
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_PORTLET_NOT_FOUND, AdminMessage.TYPE_ERROR );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_NOT_NUMERIC, AdminMessage.TYPE_ERROR );
        }

        return strUrl;
    }

    /**
     * Process creation
     * @param request request
     * @return Portlet's modification url
     */
    public String doCreate( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strIdPage = request.getParameter( PARAMETER_PAGE_ID );

        if ( StringUtils.isNotBlank( strIdPage ) && StringUtils.isNumeric( strIdPage ) )
        {
            NotificationListPortlet portlet = new NotificationListPortlet(  );
            String strError = setPortletCommonData( request, portlet );

            if ( StringUtils.isBlank( strError ) )
            {
                int nIdPage = Integer.parseInt( strIdPage );
                String strShowDateCreation = request.getParameter( PARAMETER_SHOW_DATE_CREATION );
                String strShowSender = request.getParameter( PARAMETER_SHOW_SENDER );

                portlet.setPageId( nIdPage );
                portlet.setShowDateCreation( StringUtils.isNotEmpty( strShowDateCreation ) );
                portlet.setShowSender( StringUtils.isNotEmpty( strShowSender ) );
                // Creating portlet
                NotificationListPortletService.getService(  ).create( portlet );

                //Displays the page with the new Portlet
                strUrl = getPageUrl( portlet );
            }
            else
            {
                strUrl = strError;
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_NOT_NUMERIC, AdminMessage.TYPE_ERROR );
        }

        return strUrl;
    }

    /**
     * Process portlet's modification
     * @param request request
     * @return The Jsp management URL of the process result
     */
    public String doModify( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strPortletId = request.getParameter( PARAMETER_PORTLET_ID );

        if ( StringUtils.isNotBlank( strPortletId ) && StringUtils.isNumeric( strPortletId ) )
        {
            int nPortletId = Integer.parseInt( strPortletId );
            NotificationListPortlet portlet = (NotificationListPortlet) PortletHome.findByPrimaryKey( nPortletId );

            if ( portlet != null )
            {
                String strError = setPortletCommonData( request, portlet );

                if ( StringUtils.isBlank( strError ) )
                {
                    String strShowDateCreation = request.getParameter( PARAMETER_SHOW_DATE_CREATION );
                    String strShowSender = request.getParameter( PARAMETER_SHOW_SENDER );

                    portlet.setShowDateCreation( StringUtils.isNotEmpty( strShowDateCreation ) );
                    portlet.setShowSender( StringUtils.isNotEmpty( strShowSender ) );
                    NotificationListPortletService.getService(  ).update( portlet );
                    strUrl = getPageUrl( portlet );
                }
                else
                {
                    strUrl = strError;
                }
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_PORTLET_NOT_FOUND, AdminMessage.TYPE_ERROR );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_NOT_NUMERIC, AdminMessage.TYPE_ERROR );
        }

        return strUrl;
    }

    /**
     * Get the page url
     * @param portlet the portlet
     * @return the page url
     */
    private String getPageUrl( Portlet portlet )
    {
        return PARENT_DIRECTORY + PARENT_DIRECTORY + super.getPageUrl( portlet.getPageId(  ) );
    }
}
