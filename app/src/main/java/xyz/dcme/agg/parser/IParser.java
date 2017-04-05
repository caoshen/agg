package xyz.dcme.agg.parser;

import java.util.List;

public interface IParser<T> {

    T parse(String url);

    List<T> parseList(String url);
}
