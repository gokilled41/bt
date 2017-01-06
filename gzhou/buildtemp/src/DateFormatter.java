//Copyright (c) 2015-2016 Vitria Technology, Inc.
//All Rights Reserved.
//

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateFormatter extends SimpleDateFormat {

    private static Map<String, DateFormatter> cache_ = new HashMap<String, DateFormatter>();

    private SimpleDateFormat[] formatters_;
    private Type[] types_;
    private String[] patterns_;

    public synchronized static DateFormatter getDateFormatter(String patternsStr) {
        DateFormatter df = cache_.get(patternsStr);
        if (df == null) {
            df = new DateFormatter(patternsStr);
            cache_.put(patternsStr, df);
        }
        return df;
    }

    private DateFormatter(String patternsStr) {
        patterns_ = patternsStr.split(",");
        formatters_ = new SimpleDateFormat[patterns_.length];
        types_ = new Type[patterns_.length];
        for (int i = 0; i < patterns_.length; i++) {
            formatters_[i] = new SimpleDateFormat(patterns_[i]);
            types_[i] = getType(patterns_[i]);
        }
        sort();
    }

    private void sort() {
        List<Item> list = toItems(patterns_, types_, formatters_);
        Comparator<Item> c = new Comparator<Item>() {
            public int compare(Item o1, Item o2) {
                Integer i1 = toPriority(o1.type_);
                Integer i2 = toPriority(o2.type_);
                return i1.compareTo(i2);
            }

            private Integer toPriority(Type t) {
                if (t == Type.DateTime)
                    return 1;
                if (t == Type.Time)
                    return 2;
                if (t == Type.Date)
                    return 3;
                return 4;
            }
        };
        Collections.sort(list, c);
        for (int i = 0; i < list.size(); i++) {
            patterns_[i] = list.get(i).pattern_;
            types_[i] = list.get(i).type_;
            formatters_[i] = list.get(i).formatter_;
        }
    }

    public String[] getPatterns() {
        return patterns_;
    }

    public SimpleDateFormat[] getFormatters() {
        return formatters_;
    }

    @Override
    public synchronized Date parse(String source) throws ParseException {
        for (int i = 0; i < formatters_.length; i++) {
            try {
                return toDate(formatters_[i].parse(source), types_[i]);
            } catch (Exception e) {
            }
        }
        throw new ParseException("Cannot parse: " + source, 0);
    }

    private Date toDate(Date d, Type type) {
        switch (type) {
        case DateTime:
            return new java.sql.Timestamp(d.getTime());
        case Date:
            return new java.sql.Timestamp(d.getTime());
        case Time:
            return new java.sql.Timestamp(d.getTime());
        default:
            return d;
        }
    }

    private Type getType(String format) {
        try {
            SimpleDateFormat sdf123 = new SimpleDateFormat(format);
            Date now = new Date();
            Date newd = sdf123.parse(sdf123.format(now));
            Calendar c = Calendar.getInstance();
            c.setTime(newd);
            if (c.get(Calendar.YEAR) == 1970)
                return Type.Time;
            else if (c.get(Calendar.HOUR) == 0 && c.get(Calendar.MINUTE) == 0 && c.get(Calendar.SECOND) == 0)
                return Type.Date;
            else
                return Type.DateTime;
        } catch (ParseException e) {
        }
        return Type.DateTime;
    }

    private static enum Type {
        DateTime, Date, Time
    }

    private static List<Item> toItems(String[] patterns, Type[] types, SimpleDateFormat[] formatters) {
        List<Item> list = new ArrayList<Item>();
        for (int i = 0; i < patterns.length; i++) {
            Item item = new Item();
            item.pattern_ = patterns[i];
            item.type_ = types[i];
            item.formatter_ = formatters[i];
            list.add(item);
        }
        return list;
    }

    private static class Item {
        private Type type_;
        private String pattern_;
        private SimpleDateFormat formatter_;
    }

    public static List<String> sort(List<String> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i));
            if (i < params.size() - 1)
                sb.append(",");
        }
        String patternsStr = sb.toString();
        DateFormatter df = getDateFormatter(patternsStr);
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(df.patterns_));
        return list;
    }
}
