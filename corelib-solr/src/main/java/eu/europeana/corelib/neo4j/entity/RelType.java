/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.neo4j.entity;

import org.neo4j.graphdb.RelationshipType;

/**
 *
 * @author gmamakis
 */
public enum RelType implements RelationshipType{
    EDM_IS_NEXT_INSEQUENCE("edm:isNextInSequence"),DCTERMS_ISPARTOF("dcterms:isPartOf"),DCTERMS_HASPART("dcterms:hasPart");
    
    private String relType;
    private RelType(String relType){
        this.relType = relType;
    }
 
    public String getRelType(){
        return this.relType;
    }
}
