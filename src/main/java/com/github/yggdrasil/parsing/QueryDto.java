package com.github.yggdrasil.parsing;

public class QueryDto {

    private String sessionId;
    private String queryText;
    private boolean isFromDialogflow = false;

    public QueryDto withSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public QueryDto withQueryText(String queryText) {
        this.queryText = queryText;
        return this;
    }

    public QueryDto markAsDialogflow() {
        isFromDialogflow=true;
        return this;
    }

    @Override
    public String toString() {
        return "QueryDto{" +
               "sessionId='" + sessionId + '\'' +
               ", queryText='" + queryText + '\'' +
               '}';
    }

    public String getQueryText() {
        return queryText;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isFromDialogflow() {
        return isFromDialogflow;
    }
}
