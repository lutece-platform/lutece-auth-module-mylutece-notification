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

import org.apache.commons.lang.StringUtils;

import java.util.Locale;


/**
 *
 * FolderOutbox
 *
 */
public class FolderOutbox extends AbstractFolder
{
    private static final int ID_FOLDER = 3;
    private static final String PROPERTY_FOLDER_TYPE_LABEL_KEY = "module.mylutece.notification.folderType.outbox.label";
    private static final String IMG_FOLDER = "images/local/skin/plugins/mylutece/modules/notification/outbox.png";

    /**
     * Get the ID of the FolderOutbox
     * @return the ID of the FolderOutbox
     */
    public static final int getId(  )
    {
        return ID_FOLDER;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEditable(  )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void setFolderType( Locale locale )
    {
        FolderType folderType = new FolderType(  );
        folderType.setClassName( this.getClass(  ).getName(  ) );
        folderType.setLocale( locale );
        folderType.setLabelKey( PROPERTY_FOLDER_TYPE_LABEL_KEY );
        setFolderType( folderType );
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel(  )
    {
        return getFolderType(  ).getLabel(  );
    }

    /**
     * {@inheritDoc}
     */
    public String getUserGuid(  )
    {
        return StringUtils.EMPTY;
    }

    /**
     * {@inheritDoc}
     */
    public String getImgFolder(  )
    {
        return IMG_FOLDER;
    }
}
