package com.krzyszkowski.yggdrasil.dataproviders;

import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.parsing.Parser;
import com.krzyszkowski.yggdrasil.rqrs.HelpRequest;
import com.krzyszkowski.yggdrasil.rqrs.HelpResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class HelpDataProvider implements DataProvider {

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
