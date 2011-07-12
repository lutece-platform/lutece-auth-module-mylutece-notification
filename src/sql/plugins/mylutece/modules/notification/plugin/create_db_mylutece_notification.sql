--
-- Structure for table mylutece_notification
--
DROP TABLE IF EXISTS mylutece_notification;
CREATE TABLE mylutece_notification (
	id_notification INT(11) DEFAULT 0 NOT NULL,
	id_folder INT(11) DEFAULT 0 NOT NULL,
	is_read SMALLINT DEFAULT 0 NOT NULL,
	user_guid_sender VARCHAR(255) DEFAULT '' NOT NULL,
	user_guid_receiver VARCHAR(255) DEFAULT '' NOT NULL,
	object VARCHAR(100) DEFAULT '' NOT NULL,
	message LONG VARCHAR,
	date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (id_notification)
);

--
-- Structure for table mylutece_notification_folder
--
DROP TABLE IF EXISTS mylutece_notification_folder;
CREATE TABLE mylutece_notification_folder (
	id_folder INT(11) DEFAULT 0 NOT NULL,
	type_class_name VARCHAR(255) DEFAULT '' NOT NULL,
	folder_label VARCHAR(100) DEFAULT '' NOT NULL,
	url_icon VARCHAR(255) DEFAULT '' NOT NULL,
	user_guid VARCHAR(255) DEFAULT '' NOT NULL,
	PRIMARY KEY (id_folder)
);

--
-- Structure for table mylutece_notification_parameter
--
DROP TABLE IF EXISTS mylutece_notification_parameter;
CREATE TABLE mylutece_notification_parameter (
	parameter_key varchar(100) NOT NULL,
	parameter_value varchar(100) NOT NULL,
	PRIMARY KEY (parameter_key)
);
