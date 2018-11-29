package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Response;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class YouTubeResponse extends Response<YouTubeResponse> {

    private List<SearchResult> results;

    public YouTubeResponse withResults(List<SearchResult> results) {
        this.results = results;
        return this;
    }

    @Override
    public List<String> getMessages() {
        if (results != null && !results.isEmpty()) {
            return results.stream()
                          .map(result -> {
                              StringBuilder builder = new StringBuilder();
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
                              return builder.toString();
                          })
                          .collect(Collectors.toList());
        } else {
            return List.of("No items matching query found");
        }
    }

    @Override
    public String toString() {
        return "YouTubeResponse{" +
               "results=" + results +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
