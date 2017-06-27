package jomedia.com.rssnewsfeed.data;

import android.util.Log;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jomedia.com.rssnewsfeed.utils.Utils;

public class PubDateConverter implements Converter<String> {

    private static final DateFormat fromDF = new SimpleDateFormat("EEE, dd MMM yyy kk:mm:ss z", Locale.ENGLISH);
    private static final DateFormat toDF = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
    @Override
    public String read(InputNode node) throws Exception {
        try {
            final Date date = (node == null)? new Date() : fromDF.parse(node.getValue());
            //Log.v(Utils.LOG, "PubDateConverter -> read^ " + toDF.format(date));
            return toDF.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            //Log.v(Utils.LOG, "PubDateConverter -> error -> " + e.toString());
        }
        return null;
    }

    @Override
    public void write(OutputNode node, String value) throws Exception {

    }
}
