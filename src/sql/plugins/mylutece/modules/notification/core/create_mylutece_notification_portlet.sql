--
-- Structure for table mylutece_notification_list_portlet
--
DROP TABLE IF EXISTS mylutece_notification_list_portlet;
CREATE TABLE mylutece_notification_list_portlet (
	id_portlet int DEFAULT 0 NOT NULL,
	show_date_creation smallint DEFAULT 0 NOT NULL,
	show_sender smallint DEFAULT 0 NOT NULL,
	PRIMARY KEY (id_portlet)
);