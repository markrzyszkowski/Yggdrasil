package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.rqrs.HelpResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HelpDataProvider implements DataProvider {

    private Set<Parser> parsers;

    public HelpDataProvider(Set<Parser> parsers) {
        this.parsers = parsers;
    }

    @Override
    public HelpResponse process(Request request) {
        final List<String> commands = parsers.stream()
                                             .map(Parser::getCommand)
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());
        return new HelpResponse().withCommands(commands);
    }
}
