package com.krzyszkowski.yggdrasil.parsing.nlp;

import com.neovisionaries.i18n.LanguageCode;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Map.entry;

final class QueryFactory {

    private static Map<String, QueryFormer> queryFormers = Map.ofEntries(
      entry("weather", QueryFactory::forWeather),
      entry("news", __ -> "!news"),
      entry("translate", QueryFactory::forTranslate),
      entry("search", QueryFactory::forSearch)
    );

    private QueryFactory() {
    }

    static String formQuery(String action, Map<String, com.google.protobuf.Value> parameters) {
        return queryFormers.getOrDefault(action, __ -> "!help")
                           .apply(parameters);
    }

    private static String forWeather(Map<String, com.google.protobuf.Value> params) {
        String cityName = params.get("location").getStructValue().getFieldsOrThrow("city").getStringValue();
        return "!weather " + cityName;
    }

    private static String forSearch(Map<String, com.google.protobuf.Value> params) {
        String source = params.get("source").getStringValue();
        String query = params.get("query").getStringValue();
        switch (source) {
            case "google":
                return "!search " + query;
            case "google_image":
                return "!imagesearch " + query;
            case "youtube":
                return "!youtube " + query;
            case "wikipedia":
                return "!wiki " + query;
            default:
                throw new IllegalArgumentException("Unrecognised search source");
        }
    }

    private static String forTranslate(Map<String, com.google.protobuf.Value> params) {
        String fromLang = params.get("fromLang").getStringValue();
        String toLang = params.get("toLang").getStringValue();
        String text = params.get("text").getStringValue();

        if (isNullOrEmpty(toLang) || isNullOrEmpty(text)) {
            throw new IllegalArgumentException("Missing arguments");
        }

        String fromLangCode = !isNullOrEmpty(fromLang) ? getLanguageCodeForName(fromLang) : "";
        String toLangCode = getLanguageCodeForName(toLang);

        return Stream.of("!translate", fromLangCode, toLangCode, text)
                     .filter(e -> !isNullOrEmpty(e))
                     .collect(Collectors.joining(" "));
    }

    private static String getLanguageCodeForName(String languageName) {
        try {
            return LanguageCode.findByName(languageName).get(0).toString();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No language code found for " + languageName);
        }
    }
}
