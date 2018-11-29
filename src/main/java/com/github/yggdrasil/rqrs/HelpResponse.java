package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Response;

import java.util.List;
import java.util.stream.Collectors;

public class HelpResponse extends Response<HelpResponse> {

    private List<String> commands;

    @Override
    public List<String> getMessages() {
        return List.of(commands.stream().collect(Collectors.joining(System.lineSeparator())));
    }

    public HelpResponse withCommands(List<String> commands) {
        this.commands = commands;
        return this;
    }
}
