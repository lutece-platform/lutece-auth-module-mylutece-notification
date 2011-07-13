<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:param name="site-path" select="site-path" />
<xsl:variable name="portlet-id" select="portlet/portlet-id" />
<xsl:variable name="page-id" select="portlet/page-id" />
<xsl:variable name="id-current-folder" select="portlet/mylutece-notification-list-portlet/id-folder" />
<xsl:variable name="page-index" select="portlet/mylutece-notification-list-portlet/page-index" />
<xsl:variable name="nb-items-per-page" select="portlet/mylutece-notification-list-portlet/nb-items-per-page" />
<xsl:variable name="is-last-page-index" select="portlet/mylutece-notification-list-portlet/is-last-page-index" />
<xsl:variable name="label-from" select="portlet/mylutece-notification-list-portlet/label-from" />
<xsl:variable name="label-to" select="portlet/mylutece-notification-list-portlet/label-to" />
<xsl:variable name="label-object" select="portlet/mylutece-notification-list-portlet/label-object" />
<xsl:variable name="label-date-received" select="portlet/mylutece-notification-list-portlet/label-date-received" />
<xsl:variable name="label-select-folder" select="portlet/mylutece-notification-list-portlet/label-select-folder" />

<xsl:template match="portlet">
	<div class="portlet-background append-bottom">
        <xsl:if test="not(string(display-portlet-title)='1')">
			<div class="portlet-background-header -lutece-border-radius-top">
				<xsl:value-of disable-output-escaping="yes" select="portlet-name" />
			</div>
        </xsl:if>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="not(string(display-portlet-title)='1')">
						portlet-background-content -lutece-border-radius-bottom
					</xsl:when>
					<xsl:otherwise>
						portlet-background-content -lutece-border-radius
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:apply-templates select="mylutece-notification-list-portlet" />
		</div>
	</div>
</xsl:template>

<xsl:template match="mylutece-notification-list-portlet">
	<xsl:choose>
		<xsl:when test="error-message != ''">
			<xsl:value-of select="error-message" />
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="folders-list" />
			<xsl:apply-templates select="notifications-list" />
		 </xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="folders-list">
	<form action="">
		<input type="hidden" name="page_id" value="{$page-id}" />
		<table>
			<tr>
				<td>
					<select name="id_folder">
						<xsl:apply-templates select="folder" />
					</select>
				</td>
				<td>
					<input type="image" src="images/local/skin/plugins/mylutece/modules/notification/go.png" title="{$label-select-folder}" />
				</td>
			</tr>
		</table>
	</form>
</xsl:template>

<xsl:template match="folder">
	<option value="{id-folder}">
		<xsl:if test="id-folder = $id-current-folder">
			<xsl:attribute name="selected">
				selected
			</xsl:attribute>
		</xsl:if>
		<xsl:value-of select="folder-label" />
	</option>
</xsl:template>

<xsl:template match="notifications-list">
	<xsl:if test="*">
		<table>
			<tr>
				<xsl:if test="*[1]/sender != ''">
					<th>
						<xsl:choose>
							<xsl:when test="$id-current-folder = 3">
								<xsl:value-of select="$label-to" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$label-from" />
							</xsl:otherwise>
						</xsl:choose>
					</th>
				</xsl:if>
				<th><xsl:value-of select="$label-object" /></th>
				<xsl:if test="*[1]/date-creation != ''">
					<th>
						<xsl:value-of select="$label-date-received" />
					</th>
				</xsl:if>
			</tr>
			<xsl:apply-templates select="notification" />
		</table>
		<xsl:call-template name="paginator" />
	</xsl:if>
</xsl:template>

<xsl:template match="notification">
	<tr>
		<xsl:if test="sender != ''">
			<td>
				<xsl:value-of select="sender" />
			</td>
		</xsl:if>
		<td>
			<a href="jsp/site/Portal.jsp?page=mylutece-notification&amp;action=view_notification&amp;id_notification={id-notification}&amp;id_folder={$id-current-folder}">
				<xsl:choose>
					<xsl:when test="is-read = 0">
						<b>
							<xsl:value-of select="object" />
						</b>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="object" />
					</xsl:otherwise>
				</xsl:choose>
			</a>
		</td>
		<xsl:if test="date-creation != ''">
			<td>
				<xsl:value-of select="date-creation" />
			</td>
		</xsl:if>
	</tr>
</xsl:template>

<xsl:template name="paginator">
	<xsl:if test="( $page-index != 1 ) or ($is-last-page-index != 1 )">
		<p>
			<xsl:if test="$page-index != 1">
				<xsl:variable name="page-index-prev">
					<xsl:value-of select="$page-index - 1" />
				</xsl:variable>
				<a href="jsp/site/Portal.jsp?page_id={$page-id}&amp;id_folder={$id-current-folder}&amp;page_index={$page-index-prev}">
					<img tile="&lt;" alt="&lt;" src="images/admin/skin/prev.png" />
				</a>
			</xsl:if>
			<xsl:if test="$is-last-page-index != 1">
				<xsl:variable name="page-index-next">
					<xsl:value-of select="$page-index + 1" />
				</xsl:variable>
				<a href="jsp/site/Portal.jsp?page_id={$page-id}&amp;id_folder={$id-current-folder}&amp;page_index={$page-index-next}">
					<img tile="&gt;" alt="&gt;" src="images/admin/skin/next.png" />
				</a>
			</xsl:if>
		</p>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>
