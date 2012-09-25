package eu.europeana.corelib.dereference.impl;

import eu.europeana.corelib.definitions.jibx.Alt;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Begin;
import eu.europeana.corelib.definitions.jibx.BiographicalInformation;
import eu.europeana.corelib.definitions.jibx.BroadMatch;
import eu.europeana.corelib.definitions.jibx.Broader;
import eu.europeana.corelib.definitions.jibx.CloseMatch;
import eu.europeana.corelib.definitions.jibx.Date;
import eu.europeana.corelib.definitions.jibx.DateOfBirth;
import eu.europeana.corelib.definitions.jibx.DateOfDeath;
import eu.europeana.corelib.definitions.jibx.DateOfEstablishment;
import eu.europeana.corelib.definitions.jibx.DateOfTermination;
import eu.europeana.corelib.definitions.jibx.End;
import eu.europeana.corelib.definitions.jibx.ExactMatch;
import eu.europeana.corelib.definitions.jibx.Gender;
import eu.europeana.corelib.definitions.jibx.HasMet;
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.Identifier;
import eu.europeana.corelib.definitions.jibx.InScheme;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.IsRelatedTo;
import eu.europeana.corelib.definitions.jibx.Lat;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.Name;
import eu.europeana.corelib.definitions.jibx.NarrowMatch;
import eu.europeana.corelib.definitions.jibx.Narrower;
import eu.europeana.corelib.definitions.jibx.Notation;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.ProfessionOrOccupation;
import eu.europeana.corelib.definitions.jibx.Related;
import eu.europeana.corelib.definitions.jibx.RelatedMatch;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.jibx._Long;
@SuppressWarnings("unchecked")
public enum RdfMethod {

	CC_SKOS_PREF_LABEL("cc_skos_prefLabel", "getPrefLabel", PrefLabel.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			PrefLabel pref = new PrefLabel();
			pref.setString(((LiteralType) obj).getString());
			if (((LiteralType) obj).getLang() != null) {
				pref.setLang(((LiteralType) obj).getLang());
			}
			return (T) pref;
		}
	},
	CC_SKOS_ALT_LABEL("cc_skos_altLabel", "getAltLabel", AltLabel.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			AltLabel pref = new AltLabel();
			pref.setString(((LiteralType) obj).getString());
			if (((LiteralType) obj).getLang() != null) {
				pref.setLang(((LiteralType) obj).getLang());
			}
			return (T) pref;
		}
	},
	CC_SKOS_NOTE("cc_skos_note", "getNote", Note.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			Note pref = new Note();
			pref.setString(((LiteralType) obj).getString());
			if (((LiteralType) obj).getLang() != null) {
				pref.setLang(((LiteralType) obj).getLang());
			}
			return (T) pref;
		}
	},
	CC_SKOS_BROADER("cc_skos_broader", "getBroader", Broader.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			Broader pref = new Broader();
			pref.setResource(((ResourceType) obj).getResource());
			return (T) pref;
		}
	}, CC_SKOS_NARROWER(
			"cc_skos_narrower", "getNarrower", Narrower.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Narrower pref = new Narrower();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_RELATED(
			"cc_skos_related", "getRelated", Related.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Related pref = new Related();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_BROADMATCH(
			"cc_skos_broadMatch", "getBroadMatch", BroadMatch.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					BroadMatch pref = new BroadMatch();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_NARROWMATCH(
			"cc_skos_narrowMatch", "getNarrowMatch", NarrowMatch.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					NarrowMatch pref = new NarrowMatch();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_RELATEDMATCH(
			"cc_skos_relatedMatch", "getRelatedMatch", RelatedMatch.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					RelatedMatch pref = new RelatedMatch();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_EXACTMATCH(
			"cc_skos_exactMatch", "getExactMatch", ExactMatch.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					ExactMatch pref = new ExactMatch();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_CLOSEMATCH(
			"cc_skos_closeMatch", "getCloseMatch", CloseMatch.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					CloseMatch pref = new CloseMatch();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, CC_SKOS_NOTATIONS(
			"cc_skos_notations", "getNotation", Notation.class) {
				
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Notation pref = new Notation();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, CC_SKOS_INSCHEME(
			"cc_skos_inScheme", "getInScheme", InScheme.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					InScheme pref = new InScheme();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			},

	// PLACE
	PL_SKOS_PREF_LABEL("pl_skos_prefLabel", "getPrefLabelList", PrefLabel.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			PrefLabel pref = new PrefLabel();
			pref.setString(((LiteralType) obj).getString());
			if (((LiteralType) obj).getLang() != null) {
				pref.setLang(((LiteralType) obj).getLang());
			}
			return (T) pref;
		}
	}, PL_SKOS_ALT_LABEL(
			"pl_skos_altLabel", "getAltLabelList", AltLabel.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					AltLabel pref = new AltLabel();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, PL_SKOS_NOTE(
			"pl_skos_note", "getNoteList", Note.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Note pref = new Note();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, PL_DCTERMS_ISPART_OF(
			"pl_dcterm_isPartOf", "getIsPartOfList", IsPartOf.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					IsPartOf pref = new IsPartOf();
					pref.setString(((ResourceOrLiteralType) obj).getString());
					pref.setResource(((ResourceOrLiteralType) obj).getResource());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((ResourceOrLiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, PL_WGS84_POS_LAT(
			"pl_wgs84_pos_lat", "getLat", Lat.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Lat pref = new Lat();
					pref.setLat((Float)obj);
					return (T) pref;
				}
			}, PL_WGS84_POS_LONG(
			"pl_wgs84_pos_long", "getLong", _Long.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					_Long pref = new _Long();
					pref.setLong((Float)obj);
					return (T) pref;
				}
			}, PL_WGS84_POS_ALT(
			"pl_wgs84_pos_alt", "getAlt", Alt.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Alt pref = new Alt();
					pref.setAlt((Float)obj);
					return (T) pref;
				}
			}, PL_DCTERMS_HASPART(
			"pl_dcterms_hasPart", "getHasPartList", HasPart.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					HasPart pref = new HasPart();
					pref.setString(((ResourceOrLiteralType) obj).getString());
					pref.setResource(((ResourceOrLiteralType) obj).getResource());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((ResourceOrLiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, PL_OWL_SAMEAS(
			"pl_owl_sameAs", "getSameAsList", SameAs.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					SameAs pref = new SameAs();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			},

	// TIMESPAN
	TS_SKOS_PREF_LABEL("ts_skos_prefLabel", "getPrefLabelList", PrefLabel.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			PrefLabel pref = new PrefLabel();
			pref.setString(((LiteralType) obj).getString());
			if (((LiteralType) obj).getLang() != null) {
				pref.setLang(((LiteralType) obj).getLang());
			}
			return (T) pref;
		}
	}, TS_SKOS_ALT_LABEL(
			"ts_skos_altLabel", "getAltLabelList", AltLabel.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					AltLabel pref = new AltLabel();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, TS_SKOS_NOTE(
			"ts_skos_note", "getNoteList", Note.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Note pref = new Note();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, TS_DCTERMS_ISPART_OF(
			"ts_dcterms_isPartOf", "getIsPartOfList", IsPartOf.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					IsPartOf pref = new IsPartOf();
					pref.setString(((ResourceOrLiteralType) obj).getString());
					pref.setResource(((ResourceOrLiteralType) obj).getResource());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((ResourceOrLiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, TS_DCTERMS_HASPART(
			"ts_dcterms_hasPart", "getHasPartList", HasPart.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					HasPart pref = new HasPart();
					pref.setString(((ResourceOrLiteralType) obj).getString());
					pref.setResource(((ResourceOrLiteralType) obj).getResource());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((ResourceOrLiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, TS_OWL_SAMEAS(
			"ts_owl_sameAs", "getSameAsList", SameAs.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					SameAs pref = new SameAs();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, TS_CRM_P79F_BEGINNING_IS_QUALIFIED_BY(
			"ts_crm_P79F_beginning_is_qualified_by", "getBegin", Begin.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Begin pref = new Begin();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, TS_CRM_P80F_END_IS_QUALIFIED_BY(
			"ts_crm_P80F_end_is_qualified_by", "getEnd", End.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					End pref = new End();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			},

	// EDM_AGENT
	AG_DC_DATE("ag_dc_date", "getDateList", Date.class) {
		@Override
		public <T, V> T returnObject(T clazz, V obj) {
			Date pref = new Date();
			pref.setString(((ResourceOrLiteralType) obj).getString());
			pref.setResource(((ResourceOrLiteralType) obj).getResource());
			if (((ResourceOrLiteralType) obj).getLang() != null) {
				pref.setLang(((ResourceOrLiteralType) obj).getLang());
			}
			return (T) pref;
		}
	}, AG_DC_IDENTIFIER(
			"ag_dc_identifier", "getIdentifierList", Identifier.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Identifier pref = new Identifier();
					pref.setString(((LiteralType) obj).getString());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_SKOS_PREF_LABEL(
			"ag_skos_prefLabel", "getPrefLabelList", PrefLabel.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					PrefLabel pref = new PrefLabel();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_SKOS_ALT_LABEL(
			"ag_skos_altLabel", "getAltLabelList", AltLabel.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					AltLabel pref = new AltLabel();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_SKOS_NOTE(
			"ag_skos_note", "getNoteList", Note.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Note pref = new Note();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_EDM_BEGIN(
			"ag_edm_begin", "getBegin", Begin.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Begin pref = new Begin();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_EDM_END("ag_edm_end",
			"getEnd", End.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					End pref = new End();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_EDM_HASMET("ag_edm_hasMet",
			"getHasMetList", HasMet.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					HasMet pref = new HasMet();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_EDM_ISRELATEDTO(
			"ag_edm_isRelatedto", "getIsRelatedToList", IsRelatedTo.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					IsRelatedTo pref = new IsRelatedTo();
					pref.setString(((ResourceOrLiteralType) obj).getString());
					pref.setResource(((ResourceOrLiteralType) obj).getResource());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((ResourceOrLiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_OWL_SAMEAS(
			"ag_owl_sameAs", "getSameAsList", SameAs.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					SameAs pref = new SameAs();
					pref.setResource(((ResourceType) obj).getResource());
					return (T) pref;
				}
			}, AG_FOAF_NAME(
			"ag_foaf_name", "getName", Name.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Name pref = new Name();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_DATEOFBIRTH(
			"ag_rdagr2_dateOfBirth", "getDateOfBirth", DateOfBirth.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					DateOfBirth pref = new DateOfBirth();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_DATEOFDEATH(
			"ag_rdagr2_dateOfDeath", "getDateOfDeath", DateOfDeath.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					DateOfDeath pref = new DateOfDeath();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_DATEOFESTABLISHMENT(
			"ag_rdagr2_dateOfEstablishment", "getDateOfEstablishment",
			DateOfEstablishment.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					DateOfEstablishment pref = new DateOfEstablishment();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_DATEOFTERMINATION(
			"ag_rdagr2_dateOfTermination", "getDateOfTermination",
			DateOfTermination.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					DateOfTermination pref = new DateOfTermination();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_GENDER("ag_rdagr2_gender",
			"getGender", Gender.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					Gender pref = new Gender();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_PROFESSIONOROCCUPATION(
			"ag_rdagr2_professionOrOccupation", "getProfessionOrOccupation",
			ProfessionOrOccupation.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					ProfessionOrOccupation pref = new ProfessionOrOccupation();
					pref.setString(((ResourceOrLiteralType) obj).getString());
					pref.setResource(((ResourceOrLiteralType)obj).getResource());
					if (((ResourceOrLiteralType) obj).getLang() != null) {
						pref.setLang(((ResourceOrLiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, AG_RDAGR2_BIOGRAPHICALINFORMATION(
			"ag_rdagr2_biographicalInformation", "getBiographicalInformation",
			BiographicalInformation.class) {
				@Override
				public <T, V> T returnObject(T clazz, V obj) {
					BiographicalInformation pref = new BiographicalInformation();
					pref.setString(((LiteralType) obj).getString());
					if (((LiteralType) obj).getLang() != null) {
						pref.setLang(((LiteralType) obj).getLang());
					}
					return (T) pref;
				}
			}, ;

	private String solrField;
	private String methodName;
	private Class<?> clazz;

	private RdfMethod(String solrField, String methodName, Class<?> clazz) {
		this.solrField = solrField;
		this.methodName = methodName;
		this.clazz = clazz;
	}

	public String getSolrField() {
		return this.solrField;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public Class<?> getClazz() {
		return this.clazz;
	}

	public abstract <T, V> T returnObject(T clazz, V obj);
}
