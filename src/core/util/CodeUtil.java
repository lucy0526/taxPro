package core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CodeUtil {
	public static String dealDecode(String str) throws UnsupportedEncodingException{
		return URLDecoder.decode(str, "utf-8");
	}
}
