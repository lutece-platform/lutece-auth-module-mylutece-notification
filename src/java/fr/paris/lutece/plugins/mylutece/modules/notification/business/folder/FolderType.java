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
package fr.paris.lutece.plugins.mylutece.modules.notification.business.folder;

import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.Locale;


/**
 *
 * FolderType
 *
 */
public class FolderType
{
    private String _strLabelKey;
    private String _strClassName;
    private Locale _locale;

    /**
     * Set the label key
     * @param strLabelKey the label key
     */
    public void setLabelKey( String strLabelKey )
    {
        _strLabelKey = strLabelKey;
    }

    /**
     * Get the label key
     * @return the label key
     */
    public String getLabelKey(  )
    {
        return _strLabelKey;
    }

    /**
     * Set the class name
     * @param strClassName the class name
     */
    public void setClassName( String strClassName )
    {
        _strClassName = strClassName;
    }

    /**
     * Get the class name
     * @return the class name
     */
    public String getClassName(  )
    {
        return _strClassName;
    }

    /**
     * Set the locale
     * @param locale {@link Locale}
     */
    public void setLocale( Locale locale )
    {
        _locale = locale;
    }

    /**
     * Get the locale
     * @return the {@link Locale}
     */
    public Locale getLocale(  )
    {
        return _locale;
    }

    /**
     * Get the label
     * @return the label
     */
    public String getLabel(  )
    {
        return I18nService.getLocalizedString( _strLabelKey, _locale );
    }
}
