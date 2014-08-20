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
 *  This enumeration indicates the Fields that should be just retrieved
 *  by the SugarCRM client. 
 * @author Georgios Markakis
 */
public enum EuropeanaRetrievableField {

	ID("id", "id", "ID"),
	NAME("name", "collectionID", "Name"),
	DATE_ENTERED("date_entered", "date_entered", "Date Entered"),
	DATE_MODIFIED("date_modified", "date_modified", "Date Modified"),
	MODIFIED_BY_USER("modified_user_id", "modified_user_id", "Modified by ID"),
	CREATED_BY_USER("created_by", "created_by", "sales_stage"),
	DESCRIPTION("description", "description", "Description"),
	ASSIGNED_USER_ID("assigned_user_id", "assigned_user_id", "Assigned User Id"),
	ASSIGNED_USER_NAME("assigned_user_name", "assigned_user_name", "Assigned User Name"),
	ORGANIZATION_NAME("account_name", "account_name", "Organisation Name"),
	CAMPAIGN_NAME("campaign_name", "campaign_name", "Campaign Name"),
	EXPECTED_INGESTION_DATE("date_closed", "date_closed", "Expected ingestion date"),
	NOTES("notes_c", "notes_c", "Notes"),
	COUNTRY("country_c", "country_c", "Country"),
	ACRONYM("name_acronym_c", "name_acronym_c", "Acronym"),
	IDENTIFIER("name_id_c", "name_id_c", "Identifier"),
	DATASET_COUNTRY("language_c", "language_c", "Dataset Country"),
	ACCESS_TO_CONTENT_CHECKER("access_to_content_checker_c", "access_to_content_checker_c", "Access To Content Checker"),
	PREVIEWS_ONLY_IN_PORTAL("previews_only_on_europeana_por_c", "previews_only_on_europeana_por_c", "Previews only in Europeana Portal"),
	HARVESTING_TYPE("harvesting_type_list_c", "harvesting_type_list_c", "Harvesting Type")
	;

	private final String fieldId;
	private final String qualifiedFieldId;
	private final String description;

	EuropeanaRetrievableField(String fieldId, String qualifiedFieldId, String description) {
		this.fieldId = fieldId;
		this.qualifiedFieldId=qualifiedFieldId;
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
