package marcura.development.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String isoDateFromDate(Date date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(format.format(date));
		return format.format(date);
	}
}
