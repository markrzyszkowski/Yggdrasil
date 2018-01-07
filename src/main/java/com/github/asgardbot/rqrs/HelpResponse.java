package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelpResponse extends Response {

    private List<String> commands;

    @Override
    public String getResponseText() {
        Stream<String> cmdStream = commands.stream();
        return Stream.concat(cmdStream, Stream.of("Try also queries in natural language"))
                     .collect(Collectors.joining(System.lineSeparator()));
    }

    public HelpResponse withCommands(List<String> commands) {
        this.commands = commands;
        return this;
    }
}
