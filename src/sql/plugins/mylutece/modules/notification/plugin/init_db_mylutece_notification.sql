--
-- Init table mylutece_notification_folder
--
INSERT INTO mylutece_notification_folder (id_folder, type_class_name, folder_label, url_icon, user_guid) 
VALUES (1,'fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderInbox','','images/local/skin/plugins/mylutece/modules/notification/inbox.png', '');
INSERT INTO mylutece_notification_folder (id_folder, type_class_name, folder_label, url_icon, user_guid) 
VALUES (2,'fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderArchive','','images/local/skin/plugins/mylutece/modules/notification/archive.png', '');
INSERT INTO mylutece_notification_folder (id_folder, type_class_name, folder_label, url_icon, user_guid) 
VALUES (3,'fr.paris.lutece.plugins.mylutece.modules.notification.business.folder.FolderOutbox','','images/local/skin/plugins/mylutece/modules/notification/outbox.png', '');

--
-- Init table mylutece_notification_parameter
--
INSERT INTO mylutece_notification_parameter (parameter_key, parameter_value) VALUES ('enable_notifications_sending', '1');
