package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;

import java.util.List;

public class YouTubeResponse extends Response {

    private List<SearchResult> results;

    public YouTubeResponse withResults(List<SearchResult> results) {
        this.results = results;
        return this;
    }

    @Override
    public String getResponseText() {
        StringBuilder builder = new StringBuilder();
        if (results != null && !results.isEmpty()) {
            for (SearchResult result : results) {
                ResourceId resourceId = result.getId();
                switch (resourceId.getKind()) {
                    case "youtube#video":
                        builder.append(result.getSnippet().getTitle())
                               .append(": https://www.youtube.com/watch?v=")
                               .append(resourceId.getVideoId());
                        break;
                    case "youtube#channel":
                        builder.append(result.getSnippet().getChannelTitle())
                               .append(": https://www.youtube.com/channel/")
                               .append(resourceId.getChannelId());
                        break;
                    case "youtube#playlist":
                        builder.append(result.getSnippet().getTitle())
                               .append(": https://www.youtube.com/playlist?list=")
                               .append(resourceId.getPlaylistId());
                }
                builder.append(System.lineSeparator());
            }
        } else {
            builder.append("No items matching query found");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "YouTubeResponse{" +
               "results=" + results +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
