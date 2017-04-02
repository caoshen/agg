package xyz.dcme.agg.parser;

import java.util.List;

public interface BodyParser<T> {

    T parse(String body);

    List<T> parseList(String body);
}
