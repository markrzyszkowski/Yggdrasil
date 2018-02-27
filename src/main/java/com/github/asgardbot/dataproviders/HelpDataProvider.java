package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.rqrs.HelpRequest;
import com.github.asgardbot.rqrs.HelpResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HelpDataProvider implements DataProvider {

    private List<String> commands;

    public HelpDataProvider(@Lazy Set<Parser> parsers) {
        commands = parsers.stream()
                          .map(Parser::getCommand)
                          .filter(Objects::nonNull)
                          .sorted()
                          .collect(Collectors.toList());
    }

    @Override
    public HelpResponse process(Request request) {
        return new HelpResponse().withCommands(commands);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof HelpRequest;
    }
}
