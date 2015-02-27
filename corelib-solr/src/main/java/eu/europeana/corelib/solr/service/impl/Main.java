package eu.europeana.corelib.solr.service.impl;

public class Main {

    public static void main(String[] args) {
        Integer tag = 54263944;
        System.out.println(Integer.toBinaryString(tag));
        System.out.println("Label: " + FacetLabelExtractor.getFacetLabel(tag));
    }
}
