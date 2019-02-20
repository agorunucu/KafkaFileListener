package com.gorunucu.dataProcessor;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

public class SolrUtil {

    private static SolrUtil _solrUtil = new SolrUtil();
    public static SolrUtil getInstance(){
        return _solrUtil;
    }

    private HttpSolrClient solr;

    private SolrUtil(){
        String urlString = "http://localhost:8983/solr/TEB";
        solr = new HttpSolrClient.Builder(urlString).build();
        solr.setParser(new XMLResponseParser());
    }

    public void save(String data) throws IOException, SolrServerException {
        SolrInputDocument solrInputDocument = parse(data);
        solr.add(solrInputDocument);
        solr.commit();
    }

    private SolrInputDocument parse(String data){
        SolrInputDocument document = new SolrInputDocument();
        String[] parsedData = data.split(",");
        document.addField("timestamp", parsedData[0]);
        document.addField("loglevel", parsedData[1]);
        document.addField("data", parsedData[2]);
        document.addField("IP", parsedData[3]);
        document.addField("host", parsedData[4]);
        document.addField("location", parsedData[5]);
        return document;
    }
}
