/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package eu.europeana.uim.sugarcrmclient.enums;

/**
 *  This enumeration indicates the Fields that can be updated
 *  by the SugarCRM client. 
 *  
 * @author Georgios Markakis
 * @author Yorgos Mamakis
 */
public enum EuropeanaUpdatableField {

	AMOUNT("amount", "amount", "Amount"),

	DATE_OF_REPLICATION("actual_ingestion_date_c", "actual_ingestion_date_c", "Date of Replication"),

	TOTAL_INGESTED("ingested_total_c", "ingested_total_c", "Ingested Total"),
	INGESTED_SOUND("ingested_sound_c", "ingested_sound_c", "Ingested Sound"),
	INGESTED_VIDEO("ingested_video_c", "ingested_video_c", "Ingested Video"),
	INGESTED_TEXT("ingested_text_c", "ingested_text_c", "Ingested Text"),
	INGESTED_IMAGE("ingested_image_c", "ingested_image_c", "Ingested Images"),

	PLANNED_TOTAL("planned_total_c", "planned_total_c", "Ingested Total"),
	PLANNED_SOUND("planned_sound_c", "planned_sound_c", "Ingested Sound"),
	PLANNED_VIDEO("planned_video_c", "planned_video_c", "Ingested Video"),
	PLANNED_TEXT("planned_text_c", "planned_text_c", "Ingested Text"),
	PLANNED_IMAGE("planned_image_c", "planned_image_c", "Ingested Images"),
	DELETED_RECORDS("deleted_records_c", "deleted_records_c", "Deleted Records"),

	HARVEST_URL("harvest_url_c", "harvest_url_c", "Harvest URL"),
	SETSPEC("setspec_c", "setspec_c", "Setspec"),
	METADATA_FORMAT("metadata_format_c", "metadata_format_c", "Metadata Format"),
	METADATA_SCHEMA("metadata_schema_c", "metadata_schema_c", "Metadata Schema"),
	METADATA_NAMESPACE("metadata_namespace_c", "metadata_namespace_c", "Metadata Namespace"),
	Z3950ADDRESS("address_c", "address_c", "Address"),
	Z3950PORT("port_c", "port_c", "Port"),
	Z3950DATABASE("database_c", "database_c", "Database"),
	FTP_Z3950_USER("user_c", "user_c", "User"),
	FTP_Z3950_PASSWORD("password_c", "password_c", "Password"),
	Z3950RECORD_SYNTAX("record_syntax_c", "record_syntax_c", "Record Syntax"),
	Z3950CHARSET("charset_c", "charset_c", "Charset"),
	Z3950METHOD("z39_50_method_c", "z39_50_method_c", "Z3950 Method"),
	Z3950FILEPATH("filepath_c", "filepath_c", "File Path"),
	Z3950MAXIMUMID("maximumid_c", "maximumid_c", "Maximum ID"),
	Z3950EARLIEST_TIMESTAMP("earliest_timestamp_c", "earliest_timestamp_c", "Earliest Timestamp"),
	FTPPATH("ftppath_c", "ftppath_c", "FTP Path"),
	FTP_HTTP_ISOFORMAT("isoformat_c", "isoformat_c", "ISO Format"),
	FTPSERVER("server_c", "server_c", "Server"),
	HTTPURL("http_url_c", "http_url_c", "HTTP URL"),
	FOLDER("folder_c", "folder_c", "Folder"),
	RECORDXPATH("record_xpath_c", "record_xpath_c", "Record XPatth"),

	ENABLED("enabled_c", "enabled_c", "Enabled"),
	DELETED("deleted", "deleted", "Deleted"),
	NEXT_STEP("next_step", "next_step", "Next Step"),
	STATUS("sales_stage", "sales_stage", "sales_stage"),
	TYPE("opportunity_type", "opportunity_type", "Type"),
	;

	private final String fieldId;
	private final String qualifiedFieldId;
	private final String description;	

	EuropeanaUpdatableField(String fieldId, String qualifiedFieldId, String description) {
		this.fieldId = fieldId;
		this.qualifiedFieldId = qualifiedFieldId;
		this.description = description;
	}

	public String getFieldId() {
		return fieldId;
	}

	public String getDescription() {
		return description;
	}

	public String getQualifiedFieldId() {
		return qualifiedFieldId;
	}
}
