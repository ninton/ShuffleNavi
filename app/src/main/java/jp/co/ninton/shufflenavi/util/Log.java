package jp.co.ninton.shufflenavi.util;

public class Log {
	public static final int VERBOSE = android.util.Log.VERBOSE;
	public static final int DEBUG   = android.util.Log.DEBUG;
	public static final int INFO    = android.util.Log.INFO;
	public static final int WARN    = android.util.Log.WARN;
	public static final int ERROR   = android.util.Log.ERROR;
	private static final int TAG_MAXLEN = 23;
	
	private Log() {
		;
	}

	public static boolean isLoggable(String i_tag, int i_level) {
		// if tag.length() > 23 , android.util.Log.isLoggable throw IllegalArgumentException.
		String tag = "";
		if ( TAG_MAXLEN < i_tag.length() ) {
			tag = i_tag.substring(0, TAG_MAXLEN);
		} else {
			tag = i_tag;
		}
		return android.util.Log.isLoggable(tag, i_level);
	}
	
	public static void v(String i_tag, String i_msg) {
		if ( isLoggable(i_tag, VERBOSE) ) {
			android.util.Log.v(i_tag, i_msg);
		}
	}
	
	public static void v(String i_tag, String i_msg, Throwable i_tr) {
		if ( isLoggable(i_tag, VERBOSE) ) {
			android.util.Log.v(i_tag, i_msg, i_tr);
		}
	}
	
	public static void d(String i_tag, String i_msg) {
		if ( isLoggable(i_tag, DEBUG) ) {
			android.util.Log.d(i_tag, i_msg);
		}
	}

	public static void d(String i_tag, String i_msg, Throwable i_tr) {
		if ( isLoggable(i_tag, DEBUG) ) {
			android.util.Log.d(i_tag, i_msg, i_tr);
		}
	}
	
	public static void i(String i_tag, String i_msg) {
		if ( isLoggable(i_tag, INFO) ) {
			android.util.Log.i(i_tag, i_msg);
		}
	}

	public static void i(String i_tag, String i_msg, Throwable i_tr) {
		if ( isLoggable(i_tag, INFO) ) {
			android.util.Log.i(i_tag, i_msg, i_tr);
		}
	}
	
	public static void w(String i_tag, String i_msg) {
		if ( isLoggable(i_tag, WARN) ) {
			android.util.Log.w(i_tag, i_msg);
		}
	}

	public static void w(String i_tag, Throwable i_tr) {
		if ( isLoggable(i_tag, WARN) ) {
			android.util.Log.w(i_tag, i_tr);
		}
	}
	
	public static void w(String i_tag, String i_msg, Throwable i_tr) {
		if ( isLoggable(i_tag, WARN) ) {
			android.util.Log.w(i_tag, i_msg, i_tr);
		}
	}
	
	public static void e(String i_tag, String i_msg) {
		if ( isLoggable(i_tag, ERROR) ) {
			android.util.Log.e(i_tag, i_msg);
		}
	}
	
	public static void e(String i_tag, String i_msg, Throwable i_tr) {
		if ( isLoggable(i_tag, ERROR) ) {
			android.util.Log.e(i_tag, i_msg, i_tr);
		}
	}

}
