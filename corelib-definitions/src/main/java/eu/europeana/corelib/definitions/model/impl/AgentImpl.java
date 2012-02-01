/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
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
package eu.europeana.corelib.definitions.model.impl;

import java.util.Date;

import com.google.code.morphia.annotations.*;

import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.model.Agent;
/**
 * @see eu.europeana.corelib.definitions.model.Agent
 * @author yorgos.mamakis@kb.nl
 *
 */
@Entity("Agent")
public class AgentImpl implements Agent {
	
@Id ObjectId agentId;
private String[] prefLabel;
private String[] altLabel;
private String[] note;
private Date begin;
private Date end;

	@Override
	public String[] getPrefLabel() {
		
		return this.prefLabel;
	}

	@Override
	public String[] getAltLabel() {
		return this.altLabel;
	}

	@Override
	public String[] getNote() {
		return this.note;
	}

	@Override
	public Date getBegin() {
		return this.begin;
	}

	@Override
	public Date getEnd() {
		return this.end;
	}

}
