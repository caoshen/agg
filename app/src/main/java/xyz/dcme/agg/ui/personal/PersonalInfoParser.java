package xyz.dcme.agg.ui.personal;

import java.util.List;

import xyz.dcme.agg.parser.BodyParser;

import static xyz.dcme.agg.util.LogUtils.makeLogTag;

public class PersonalInfoParser implements BodyParser {
    private static final String TAG = makeLogTag("PersonalInfoParser");


    @Override
    public PersonalInfo parse(String body) {
        return null;
    }

    @Override
    public List<PersonalInfo> parseList(String body) {
        return null;
    }
}
