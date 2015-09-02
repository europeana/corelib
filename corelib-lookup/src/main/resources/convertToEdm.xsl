<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/*">
		<rdf:RDF xmlns:dcterms="http://purl.org/dc/terms/" xmlns:edm="http://www.europeana.eu/schemas/edm/"
			xmlns:enrichment="http://www.europeana.eu/schemas/edm/enrichment/"
			xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:wgs84="http://www.w3.org/2003/01/geo/wgs84_pos#"
			xmlns:skos="http://www.w3.org/2004/02/skos/core#" xmlns:oai="http://www.openarchives.org/OAI/2.0/"
			xmlns:ore="http://www.openarchives.org/ore/terms/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:rdagr2="http://rdvocab.info/ElementsGr2" xmlns:foaf="http://xmlns.com/foaf/0.1"
			xsi:schemaLocation="http://www.w3.org/1999/02/22-rdf-syntax-ns# EDM-INTERNAL.xsd">




			<!-- PROVIDED CHO -->
			<xsl:element name="edm:ProvidedCHO">
				<xsl:attribute name="rdf:about">
					<xsl:value-of select="europeana_id" />
				</xsl:attribute>
			</xsl:element>
			<!-- ORE:AGGREGATION -->
			<xsl:element name="ore:Aggregation">
				<xsl:attribute name="rdf:about">
					<xsl:value-of select="provider_aggregation_ore_aggregation" />
				</xsl:attribute>
				<xsl:element name="edm:aggregatedCHO">
					<xsl:attribute name="rdf:resource">
						<xsl:value-of select="provider_aggregation_edm_aggregatedCHO" />
					</xsl:attribute>
				</xsl:element>
				<xsl:if test="//*/provider_aggregation_edm_dataProvider">
					<xsl:element name="edm:dataProvider">
						<xsl:attribute name="rdf:resource">
							<xsl:value-of select="provider_aggregation_edm_dataProvider" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_edm_hasView">
					<xsl:element name="edm:hasView">
						<xsl:attribute name="rdf:resource">
							<xsl:value-of select="provider_aggregation_edm_hasView" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_edm_isShownAt">
					<xsl:element name="edm:isShownAt">
						<xsl:attribute name="rdf:resource">
							<xsl:value-of select="provider_aggregation_edm_isShownAt" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_edm_isShownBy">
					<xsl:element name="edm:isShownBy">
						<xsl:attribute name="rdf:resource">
							<xsl:value-of select="provider_aggregation_edm_isShownBy" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_edm_object">
					<xsl:element name="edm:object">
						<xsl:attribute name="rdf:resource">
							<xsl:value-of select="provider_aggregation_edm_object" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_edm_provider">
					<xsl:element name="edm:provider">
						<xsl:choose>
							<xsl:when test="starts-with(provider_aggregation_edm_provider, 'http://')">
								<xsl:attribute name="rdf:resource">
									<xsl:value-of select="provider_aggregation_edm_provider" />
								</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="provider_aggregation_edm_provider" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_dc_rights">
					<xsl:element name="dc:rights">
						<xsl:choose>
							<xsl:when test="starts-with(provider_aggregation_dc_rights, 'http://')">
								<xsl:attribute name="rdf:resource">
									<xsl:value-of select="provider_aggregation_dc_rights" />
								</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="provider_aggregation_dc_rights" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:if>
				<xsl:if test="//*/provider_aggregation_edm_rights">
					<xsl:element name="edm:rights">
						<xsl:choose>
							<xsl:when test="starts-with(provider_aggregation_edm_rights, 'http://')">
								<xsl:attribute name="rdf:resource">
									<xsl:value-of select="provider_aggregation_edm_rights" />
								</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="provider_aggregation_edm_rights" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:if>
				<xsl:if test="edm_UGC=true">
					<xsl:element name="edm:UGC">
						true
					</xsl:element>
				</xsl:if>
			</xsl:element>
			<!-- WEB RESOURCE -->
			<xsl:for-each select="//*/edm_webResource">
				<xsl:element name="edm:WebResource">
					<xsl:attribute name="rdf:about">
						<xsl:value-of select="." />
					</xsl:attribute>
					<xsl:if test="//*/wr_dc_description">
						<xsl:element name="dc:description">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dc_description, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dc_description" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dc_description" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dc_format">
						<xsl:element name="dc:format">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dc_format, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dc_format" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dc_format" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dc_rights">
						<xsl:element name="dc:rights">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dc_rights, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dc_rights" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dc_rights" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dc_source">
						<xsl:element name="dc:source">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dc_source, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dc_source" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dc_source" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dcterms_conformsTo">
						<xsl:element name="dcterms:conformsTo">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dcterms_conformsTo, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dcterms_conformsTo" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dcterms_conformsTo" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dcterms_created">
						<xsl:element name="dcterms:created">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dcterms_created, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dcterms_created" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dcterms_created" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dcterms_extent">
						<xsl:element name="dcterms:extent">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dcterms_extent, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dcterms_extent" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dcterms_extent" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dcterms_hasPart">
						<xsl:element name="dcterms:hasPart">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dcterms_hasPart, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dcterms_hasPart" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dcterms_hasPart" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dcterms_isFormatOf">
						<xsl:element name="dcterms:isFormatOf">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dcterms_isFormatOf, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dcterms_isFormatOf" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dcterms_isFormatOf" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_dcterms_issued">
						<xsl:element name="dcterms:issued">
							<xsl:choose>
								<xsl:when test="starts-with(wr_dcterms_issued, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_dcterms_issued" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_dcterms_issued" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_edm_isNextInSequence">
						<xsl:element name="edm:isNextInSequence">
							<xsl:choose>
								<xsl:when test="starts-with(wr_edm_isNextInSequence, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_edm_isNextInSequence" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_edm_isNextInSequence" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/wr_edm_rights">
						<xsl:element name="edm:rights">
							<xsl:choose>
								<xsl:when test="starts-with(wr_edm_rights, 'http://')">
									<xsl:attribute name="rdf:resource">
										<xsl:value-of select="//*/wr_edm_rights" />
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/wr_edm_rights" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
				</xsl:element>
			</xsl:for-each>
			<!-- AGENT -->
			<xsl:for-each select="//*[starts-with(name(), 'edm_agent')]">
				<xsl:variable name="indexAgent" select="substring-after(name(), 'edm_agent')" />
				<xsl:element name="edm:Agent">
					<xsl:attribute name="rdf:about">
					<xsl:value-of select="." />
				</xsl:attribute>
					<xsl:for-each select="//*[name()=concat('ag_skos_prefLabel',$indexAgent)]">
						<xsl:element name="skos:prefLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each select="//*[name()=concat('ag_skos_altLabel',$indexAgent)]">
						<xsl:element name="skos:altLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:if test="//*[name()=concat('ag_skos_note',$indexAgent)]">
						<xsl:element name="skos:note">
							<xsl:value-of select="ag_skos_note" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_dc_date',$indexAgent)]">
						<xsl:element name="dc:date">
							<xsl:choose>
								<xsl:when test="starts-with(ag_dc_date, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="." />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_dc_identifier',$indexAgent)]">
						<xsl:element name="dc:identifier">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_edm_hasMet', $indexAgent)]">
						<xsl:element name="edm:hasMet">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_edm_isRelatedTo',$indexAgent)]">
						<xsl:element name="edm:isRelatedTo">
							<xsl:choose>
								<xsl:when test="starts-with(ag_edm_isRelatedTo, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="." />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_foaf_name',$indexAgent)]">
						<xsl:element name="foaf:name">
							<xsl:value-of select="ag_foaf_name" />
						</xsl:element>
					</xsl:if>
					<xsl:if
						test="//*[name()=concat('ag_rdagr2_biographicalInformation',$indexAgent)]">
						<xsl:element name="rdagr2:biographicalInformation">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_rdagr2_dateOfBirth',$indexAgent)]">
						<xsl:element name="rdagr2:dateOfBirth">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_rdagr2_dateOfDeath',$indexAgent)]">
						<xsl:element name="rdagr2:dateOfDeath">
							<xsl:value-of select="ag_rdagr2_dateOfDeath" />
						</xsl:element>
					</xsl:if>
					<xsl:if
						test="//*[name()=concat('ag_rdagr2_dateOfEstablishment',$indexAgent)]">
						<xsl:element name="rdagr2:dateOfEstablishment">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_rdagr2_dateOfTermination',$indexAgent)]">
						<xsl:element name="rdagr2:dateOfTermination">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_rdagr2_gender',$indexAgent)]">
						<xsl:element name="rdagr2:gender">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if
						test="//*[name()=concat('ag_rdagr2_professionOrOccupation',$indexAgent)]">
						<xsl:element name="rdagr2:professionOrOccupation">
							<xsl:choose>
								<xsl:when test="starts-with(name(), 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="." />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ag_owl_sameAs',$indexAgent)]">
						<xsl:element name="owl:sameAs">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
				</xsl:element>
			</xsl:for-each>

			<!-- TIMESPAN -->
			<xsl:for-each select="//*[starts-with(name(), 'edm_timespan')]">
				<xsl:variable name="indexTS"	select="substring-after(name(), 'edm_timespan')" />
				<xsl:element name="edm:TimeSpan">
					<xsl:attribute name="rdf:about">
					<xsl:value-of select="." />
				</xsl:attribute>
					<xsl:for-each select="//*[name()=concat('ts_skos_prefLabel',$indexTS)]">
						<xsl:element name="skos:prefLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each select="//*[name()=concat('ts_skos_altLabel',$indexTS)]">
						<xsl:element name="skos:altLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>

							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:if test="//*[name()=concat('ts_skos_note',$indexTS)]">
						<xsl:element name="skos:note">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ts_dcterms_hasPart',$indexTS)]">
						<xsl:element name="dcterms:hasPart">
							<xsl:choose>
								<xsl:when test="starts-with(ts_dcterms_hasPart, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="." />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ts_dcterms_isPartOf',$indexTS)]">
						<xsl:element name="dcterms:isPartOf">
							<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
							<xsl:value-of
								select="//*[name()=concat('ts_dcterms_isPartOfLabel',$indexTS)]" />
						</xsl:element>
					</xsl:if>
					<xsl:if
						test="//*[name()=concat('ts_crm_P79F_beginning_is_qualified_by',$indexTS)]">
						<xsl:element name="edm:begin">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if
						test="//*[name()=concat('ts_crm_P80F_end_is_qualified_by',$indexTS)]">
						<xsl:element name="edm:end">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('ts_owl_sameAs',$indexTS)]">
						<xsl:element name="owl:sameAs">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
				</xsl:element>
			</xsl:for-each>


			<!-- PLACE -->
			
			<xsl:for-each select="//*[starts-with(name(), 'edm_place')]">
				<xsl:variable name="indexPL"
					select="substring-after(name(), 'edm_place')" />
				<xsl:element name="edm:Place">
					<xsl:attribute name="rdf:about">
					<xsl:value-of select="." />
				</xsl:attribute>
					<xsl:if test="//*[name()=concat('pl_wgs84_pos_lat',$indexPL)]">
						<xsl:element name="wgs84:lat">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('pl_wgs84_pos_long',$indexPL)]">
						<xsl:element name="wgs84:long">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('pl_wgs84_pos_alt',$indexPL)]">
						<xsl:element name="wgs84:alt">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:for-each select="//*[name()=concat('pl_skos_prefLabel',$indexPL)]">
						<xsl:element name="skos:prefLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each select="//*[name()=concat('pl_skos_altLabel',$indexPL)]">
						<xsl:element name="skos:altLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:if test="//*[name()=concat('pl_skos_note',$indexPL)]">
						<xsl:element name="skos:note">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('pl_dcterms_hasPart',$indexPL)]">
						<xsl:element name="dcterms:hasPart">
							<xsl:choose>
								<xsl:when test="starts-with(pl_dcterms_hasPart, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="." />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('pl_dcterms_isPartOf',$indexPL)]">
						<xsl:element name="dcterms:isPartOf">
							<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
							<xsl:value-of
								select="//*[name()=concat('pl_dcterms_isPartOfLabel',$indexPL)]" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[name()=concat('pl_owl_sameAs',$indexPL)]">
						<xsl:element name="owl:sameAs">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>

				</xsl:element>
			</xsl:for-each>

			<!-- CONCEPT -->
			<xsl:for-each select="//*[starts-with(name(), 'skos_concept')]">
				<xsl:variable name="index"
					select="substring-after(name(), 'skos_concept')" />
				<xsl:element name="skos:Concept">
					<xsl:attribute name="rdf:about">
					<xsl:value-of select="." />
				</xsl:attribute>

					<xsl:for-each select="//*[name()=concat('cc_skos_prefLabel',$index)]">
						<xsl:element name="skos:prefLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each select="//*[name()=concat('cc_skos_altLabel',$index)]">
						<xsl:element name="skos:altLabel">
							<xsl:if test="@xml:lang">
								<xsl:attribute name="xml:lang">
				<xsl:value-of select="@xml:lang" />
				</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:for-each>
					<xsl:if test="//*[starts-with(name(),'cc_skos_note')]">
						<xsl:element name="skos:note">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_broader')]">
						<xsl:element name="skos:broader">
							<xsl:attribute name="rdf:resource">
								<xsl:value-of select="." />
							</xsl:attribute>
							<xsl:if test="//*[starts-with(name(),'cc_skos_broaderLabel')]">
								<xsl:value-of select="." />
							</xsl:if>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_narrower')]">
						<xsl:element name="skos:narrower">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_related')]">
						<xsl:element name="skos:related">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_broadMatch')]">
						<xsl:element name="skos:broadMatch">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_narrowMatch')]">
						<xsl:element name="skos:narrowMatch">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_relatedMatch')]">
						<xsl:element name="skos:relatedMatch">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_exactMatch')]">
						<xsl:element name="skos:exactMatch">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_closeMatch')]">
						<xsl:element name="skos:closeMatch">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="." />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*[starts-with(name(),'cc_skos_notation')]">
						<xsl:element name="skos:notation">
							<xsl:value-of select="." />
						</xsl:element>
					</xsl:if>
				</xsl:element>
			</xsl:for-each>
			<!-- EUROPEANA AGGREGATION -->
			<xsl:if test="//*/europeana_edm_aggregation">
				<xsl:element name="edm:EuropeanaAggregation">
					<xsl:attribute name="rdf:about">
					<xsl:value-of select="europeana_edm_aggregation" />
				</xsl:attribute>
					<xsl:if test="//*/europeana_aggregation_dc_creator">
						<xsl:element name="dc:creator">
							<xsl:choose>
								<xsl:when
									test="starts-with(europeana_aggregation_dc_creator, 'http://')">
									<xsl:attribute name="rdf:resource">
				<xsl:value-of select="europeana_aggregation_dc_creator" />
				</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="europeana_aggregation_dc_creator" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_aggregatedCHO">
						<xsl:element name="edm:aggregatedCHO">
							<xsl:attribute name="rdf:resource">
						<xsl:value-of select="europeana_aggregation_edm_aggregatedCHO" />
					</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_country">
						<xsl:element name="edm:country">

							<xsl:value-of select="europeana_aggregation_edm_country" />

						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_hasView">
						<xsl:element name="edm:hasView">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="europeana_aggregation_edm_hasView" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_isShownBy">
						<xsl:element name="edm:isShownBy">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="europeana_aggregation_edm_isShownBy" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_landingPage">
						<xsl:element name="edm:landingPage">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="europeana_aggregation_edm_landingPage" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_language">
						<xsl:element name="edm:language">
							<xsl:value-of select="europeana_aggregation_edm_language" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_edm_rights">
						<xsl:element name="edm:rights">
							<xsl:choose>
								<xsl:when
									test="starts-with(europeana_aggregation_edm_rights, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="europeana_aggregation_edm_rights" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="europeana_aggregation_edm_rights" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/europeana_aggregation_ore_aggregates">
						<xsl:element name="ore:aggregates">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="europeana_aggregation_ore_aggregates" />
					</xsl:attribute>
						</xsl:element>
					</xsl:if>
				</xsl:element>
			</xsl:if>

			<!-- PROXY -->
			<xsl:for-each select="//*[starts-with(name(), 'proxy_ore_proxy')]">
				
				<xsl:element name="edm:Proxy">
					<xsl:attribute name="rdf:about">
					<xsl:value-of select="." />
				</xsl:attribute>
					<xsl:if test="//*/proxy_dc_contributor">
						<xsl:element name="dc:contributor">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_contributor, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_contributor" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_contributor" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_creator">
						<xsl:element name="dc:creator">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_creator, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_creator" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_creator" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_coverage">
						<xsl:element name="dc:coverage">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_coverage, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_coverage" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_coverage" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_date">
						<xsl:element name="dc:date">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_date, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_date" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_date" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_description">
						<xsl:element name="dc:description">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_description, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_description" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_description" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_format">
						<xsl:element name="dc:format">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_format, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_format" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_format" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_language">
						<xsl:element name="dc:language">
							<xsl:value-of select="//*/proxy_dc_language" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_publisher">
						<xsl:element name="dc:publisher">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_publisher, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_publisher" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_publisher" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_subject">
						<xsl:element name="dc:subject">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_subject, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_subject" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_subject" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_title">
						<xsl:element name="dc:title">
							<xsl:value-of select="//*/proxy_dc_title" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_type">
						<xsl:element name="dc:type">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_type, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_type" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_type" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_identifier">
						<xsl:element name="dc:identifier">
							<xsl:value-of select="//*/proxy_dc_identifier" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_relation">
						<xsl:element name="dc:relation">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_dc_relation" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_source">
						<xsl:element name="dc:source">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_source, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_source" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_source" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dc_rights">
						<xsl:element name="dc:rights">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dc_rights, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dc_rights" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dc_rights" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_alternative">
						<xsl:element name="dcterms:alternative">
							<xsl:value-of select="//*/proxy_dcterms_alternative" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_conformsTo">
						<xsl:element name="dcterms:conformsTo">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_conformsTo, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_conformsTo" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_conformsTo" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_created">
						<xsl:element name="dcterms:created">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_created, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_created" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_created" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_extent">
						<xsl:element name="dcterms:extent">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_extent, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_extent" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_extent" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_hasFormat">
						<xsl:element name="dcterms:hasFormat">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_hasFormat, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_hasFormat" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_hasFormat" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_isPartOf">
						<xsl:element name="dcterms:isPartOf">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_isPartOf, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_isPartOf" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_isPartOf" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_isReferencedBy">
						<xsl:element name="dcterms:isReferencedBy">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_isReferencedBy, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_isReferencedBy" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_isReferencedBy" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_isRequiredby">
						<xsl:element name="dcterms:isRequiredBy">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_isRequiredBy, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_isRequiredBy" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_isRequiredBy" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_isReplacedBy">
						<xsl:element name="dcterms:isReplacedBy">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_isReplacedBy, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_isReplacedBy" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_isReplacedBy" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_isVersionOf">
						<xsl:element name="dcterms:isVersionOf">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_isVersionOf, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_isVersionOf" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_isVersionOf" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_hasVersion">
						<xsl:element name="dcterms:hasVersion">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_hasVersion, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_hasVersion" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_hasVersion" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_issued">
						<xsl:element name="dcterms:issued">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_issued, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_issued" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_issued" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_medium">
						<xsl:element name="dcterms:medium">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_medium, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_medium" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_medium" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_provenance">
						<xsl:element name="dcterms:provenance">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_provenance, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_provenance" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_provenance" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_references">
						<xsl:element name="dcterms:references">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_references, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_references" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_references" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_replaces">
						<xsl:element name="dcterms:replaces">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_replaces, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_replaces" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_replaces" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_requires">
						<xsl:element name="dcterms:requires">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_requires, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_requires" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_requires" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_spatial">
						<xsl:element name="dcterms:spatial">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_spatial, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_spatial" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_spatial" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_tableOfContents">
						<xsl:element name="dcterms:tableOfContents">
							<xsl:choose>
								<xsl:when
									test="starts-with(proxy_dcterms_tableOfContents, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_tableOfContents" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_tableOfContents" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_dcterms_temporal">
						<xsl:element name="dcterms:temporal">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_dcterms_temporal, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_dcterms_temporal" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_dcterms_temporal" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_currentLocation">
						<xsl:element name="edm:currentLocation">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_currentLocation" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_hasMet">
						<xsl:element name="edm:hasMet">
							<xsl:value-of select="//*/proxy_edm_hasMet" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_hasType">
						<xsl:element name="edm:hasType">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_edm_hasType, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_edm_hasType" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_edm_hasType" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_incorporates">
						<xsl:element name="edm:incorporates">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_incorporates" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_isDerivativeOf">
						<xsl:element name="edm:isDerivativeOf">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_isDerivativeOf" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_isNextInSequence">
						<xsl:element name="edm:isNextInSequence">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_isNextInSequence" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_isRelatedTo">
						<xsl:element name="edm:isRelatedTo">
							<xsl:choose>
								<xsl:when test="starts-with(proxy_edm_isRelatedTo, 'http://')">
									<xsl:attribute name="rdf:resource">
								<xsl:value-of select="//*/proxy_edm_isRelatedTo" />
							</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*/proxy_edm_isRelatedTo" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_isRepresentationOf">
						<xsl:element name="edm:isRepresentationOf">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_isRepresentationOf" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_isSimilarTo">
						<xsl:element name="edm:isSimilarTo">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_isSimilarTo" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_isSuccessorOf">
						<xsl:element name="edm:isSuccessorOf">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_isSuccessorOf" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_realizes">
						<xsl:element name="edm:realizes">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_edm_realizes" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_edm_type">
						<xsl:element name="edm:type">
							<xsl:value-of select="//*/proxy_edm_type" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/edm_europeana_proxy">
						<xsl:element name="edm:europeanaProxy">
							<xsl:value-of select="//*/edm_europeana_proxy" />
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_ore_proxyFor">
						<xsl:element name="ore:proxyFor">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_ore_proxyFor" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_ore_proxyIn">
						<xsl:element name="ore:proxyIn">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_ore_proxyIn" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
					<xsl:if test="//*/proxy_owl_sameAs">
						<xsl:element name="owl:sameAs">
							<xsl:attribute name="rdf:resource">
					<xsl:value-of select="//*/proxy_owl_sameAs" />
				</xsl:attribute>
						</xsl:element>
					</xsl:if>
				</xsl:element>
			</xsl:for-each>
		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>