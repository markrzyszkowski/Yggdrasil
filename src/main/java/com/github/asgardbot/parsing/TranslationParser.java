package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.TranslationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty("TRANSLATE_KEY")
class TranslationParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(TranslationParser.class);
    private List<String> languages = List.of("af", "am", "ar", "az", "ba", "be", "bg", "bn", "bs", "ca", "ceb", "cs",
                                             "cy", "da", "de", "el", "en", "eo", "es", "et", "eu", "fa", "fi", "fr",
                                             "ga", "gd", "gl", "gu", "he", "hi", "hr", "ht", "hu", "hy", "id", "is",
                                             "it", "ja", "jv", "ka", "kk", "km", "kn", "ko", "ky", "la", "lb", "lo",
                                             "lt", "lv", "mg", "mhr", "mi", "mk", "ml", "mn", "mr", "mrj", "ms", "mt",
                                             "my", "ne", "nl", "no", "pa", "pap", "pl", "pt", "ro", "ru", "si", "sk",
                                             "sl", "sq", "sr", "su", "sv", "sw", "ta", "te", "tg", "th", "tl", "tr",
                                             "tt", "udm", "uk", "ur", "uz", "vi", "xh", "yi", "zh");

    @Override
    public TranslationRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        String[] tokens = query.getQueryText().split(" ");
        if (tokens.length >= 3 && tokens[0].equalsIgnoreCase("!translate")) {
            LOGGER.info("Query matched");
            if (languages.contains(tokens[2])) {
                String text = Arrays.stream(tokens).skip(3).collect(Collectors.joining(" "));
                return new TranslationRequest().withDirection(tokens[1] + "-" + tokens[2]).withText(text);
            } else {
                String text = Arrays.stream(tokens).skip(2).collect(Collectors.joining(" "));
                return new TranslationRequest().withDirection(tokens[1]).withText(text);
            }
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!translate [<from_lang>] [to_lang] [phrase]";
    }
}
