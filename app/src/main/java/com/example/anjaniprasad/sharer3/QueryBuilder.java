package com.example.anjaniprasad.sharer3;

public class QueryBuilder {


    public String getDatabaseName() {
        return "apps";
    }


    public String getApiKey() {
        return "FIw3R9TO9oAafLrPRw-JDU8GpnaH9Cjo";
    }

    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }


    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    public String docApiKeyUrl(String docid)
    {
        return "/"+docid+"?apiKey="+getApiKey();
    }


    public String documentRequest()
    {
        return "mycoll";
    }


    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsUpdateURL(String doc_id)
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
    }

    public String createDetails(Details details)
    {
        return String
                .format("{\"phone\": \"%s\", "
                                + "\"lat\": \"%s\", \"lon\": \"%s\"}",
                        details.phone, details.lat, details.lon);
    }

    public String setContactData(Details contact) {
        return String.format("{ \"$set\" : "
                        + "{\"fbid\" : \"%s\", "
                        + "\"lat\" : \"%s\", "
                        + "\"lon\" : \"%s\" }" + "}",
                contact.getFbid(),
                contact.getLat(), contact.getLon());
    }
}