package the9UtilPack.nopLogger;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.Marker;

import the9UtilPack.CommonLibrary;
import the9UtilPack.LogUtil;


public class NopLogger implements Logger {

	private String name = "";
	public String prefix = "";
	public String postfix = "";

	@SuppressWarnings ( "unused" )
	private NopLogger() {

	}

	public NopLogger(Class<?> clazz) {

		this.name = clazz.getName();
		this.prefix = CommonLibrary.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + " [" + this.name + "] : ";

	}

	private String textMaker(String msg) {

		String stringMsg = String.valueOf(msg.toString());
		return prefix + ( stringMsg ) + postfix;

	}

	private String textMaker(Marker marker, String format, Object arg1, Object arg2) {

		return textMaker(marker, String.format(format, arg1, arg2));

	}

	private String textMaker(Marker marker, String msg, Throwable t) {

		return textMaker(marker, msg + "\n" + LogUtil.getStackTraceString(t.getStackTrace()));

	}

	private String textMaker(Marker marker, String format, Object... arguments) {

		return textMaker(marker, String.format(format, arguments));

	}

	private String textMaker(Marker marker, String format, Object arg) {

		return textMaker(marker, String.format(format, arg));

	}

	private String textMaker(Marker marker, String msg) {

		String markerName = marker.getName();
		return " * [" + markerName + "] ====\n" + textMaker(msg);

	}

	private String textMaker(String format, Object arg1, Object arg2) {

		return textMaker(String.format(format, arg1, arg2));

	}

	private String textMaker(String msg, Throwable t) {

		return textMaker(msg + "\n" + LogUtil.getStackTraceString(t.getStackTrace()));

	}

	private String textMaker(String format, Object... arguments) {

		return textMaker(String.format(format, arguments));

	}

	private String textMaker(String format, Object arg) {

		return textMaker(String.format(format, arg));

	}

	private void printErr(String msg, String level) {

		System.err.println("[" + level + "] " + msg);

	}

	private void printOut(String msg, String level) {

		System.out.println("[" + level + "] " + msg);

	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {

		printErr(textMaker(marker, format, arg1, arg2), "warn");

	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {

		printErr(textMaker(marker, msg, t), "warn");

	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {

		printErr(textMaker(marker, format, arguments), "warn");

	}

	@Override
	public void warn(Marker marker, String format, Object arg) {

		printErr(textMaker(marker, format, arg), "warn");

	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {

		printErr(textMaker(format, arg1, arg2), "warn");

	}

	@Override
	public void warn(Marker marker, String msg) {

		printErr(textMaker(marker, msg), "warn");

	}

	@Override
	public void warn(String msg, Throwable t) {

		printErr(textMaker(msg, t), "warn");

	}

	@Override
	public void warn(String format, Object... arguments) {

		printErr(textMaker(format, arguments), "warn");

	}

	@Override
	public void warn(String format, Object arg) {

		printErr(textMaker(format, arg), "warn");

	}

	@Override
	public void warn(String msg) {

		printErr(textMaker(msg), "warn");

	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {

		printOut(textMaker(marker, format, arg1, arg2), "trace");

	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {

		printOut(textMaker(marker, msg, t), "trace");

	}

	@Override
	public void trace(Marker marker, String format, Object... arguments) {

		printOut(textMaker(marker, format, arguments), "trace");

	}

	@Override
	public void trace(Marker marker, String format, Object arg) {

		printOut(textMaker(marker, format, arg), "trace");

	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {

		printOut(textMaker(format, arg1, arg2), "trace");

	}

	@Override
	public void trace(Marker marker, String msg) {

		printOut(textMaker(marker, msg), "trace");

	}

	@Override
	public void trace(String msg, Throwable t) {

		printOut(textMaker(msg, t), "trace");

	}

	@Override
	public void trace(String format, Object... arguments) {

		printOut(textMaker(format, arguments), "trace");

	}

	@Override
	public void trace(String format, Object arg) {

		printOut(textMaker(format, arg), "trace");

	}

	@Override
	public void trace(String msg) {

		printOut(textMaker(msg), "trace");

	}

	@Override
	public boolean isWarnEnabled(Marker marker) {

		return true;

	}

	@Override
	public boolean isWarnEnabled() {

		return true;

	}

	@Override
	public boolean isTraceEnabled(Marker marker) {

		return true;

	}

	@Override
	public boolean isTraceEnabled() {

		return true;

	}

	@Override
	public boolean isInfoEnabled(Marker marker) {

		return true;

	}

	@Override
	public boolean isInfoEnabled() {

		return true;

	}

	@Override
	public boolean isErrorEnabled(Marker marker) {

		return true;

	}

	@Override
	public boolean isErrorEnabled() {

		return true;

	}

	@Override
	public boolean isDebugEnabled(Marker marker) {

		return true;

	}

	@Override
	public boolean isDebugEnabled() {

		return true;

	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {

		printOut(textMaker(marker, format, arg1, arg2), "info");

	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {

		printOut(textMaker(marker, msg, t), "info");

	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {

		printOut(textMaker(marker, format, arguments), "info");

	}

	@Override
	public void info(Marker marker, String format, Object arg) {

		printOut(textMaker(marker, format, arg), "info");

	}

	@Override
	public void info(String format, Object arg1, Object arg2) {

		printOut(textMaker(format, arg1, arg2), "info");

	}

	@Override
	public void info(Marker marker, String msg) {

		printOut(textMaker(marker, msg), "info");

	}

	@Override
	public void info(String msg, Throwable t) {

		printOut(textMaker(msg, t), "info");

	}

	@Override
	public void info(String format, Object... arguments) {

		printOut(textMaker(format, arguments), "info");

	}

	@Override
	public void info(String format, Object arg) {

		printOut(textMaker(format, arg), "info");

	}

	@Override
	public void info(String msg) {

		printOut(textMaker(msg), "info");

	}

	@Override
	public String getName() {

		return "NOP-The9-binder";

	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {

		printErr(textMaker(marker, format, arg1, arg2), "error");

	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {

		printErr(textMaker(marker, msg, t), "error");

	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {

		printErr(textMaker(marker, format, arguments), "error");

	}

	@Override
	public void error(Marker marker, String format, Object arg) {

		printErr(textMaker(marker, format, arg), "error");

	}

	@Override
	public void error(String format, Object arg1, Object arg2) {

		printErr(textMaker(format, arg1, arg2), "error");

	}

	@Override
	public void error(Marker marker, String msg) {

		printErr(textMaker(marker, msg), "error");

	}

	@Override
	public void error(String msg, Throwable t) {

		printErr(textMaker(msg, t), "error");

	}

	@Override
	public void error(String format, Object... arguments) {

		printErr(textMaker(format, arguments), "error");

	}

	@Override
	public void error(String format, Object arg) {

		printErr(textMaker(format, arg), "error");

	}

	@Override
	public void error(String msg) {

		printErr(textMaker(msg), "error");

	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {

		printOut(textMaker(marker, format, arg1, arg2), "debug");

	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {

		printOut(textMaker(marker, msg, t), "debug");

	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {

		printOut(textMaker(marker, format, arguments), "debug");

	}

	@Override
	public void debug(Marker marker, String format, Object arg) {

		printOut(textMaker(marker, format, arg), "debug");

	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {

		printOut(textMaker(format, arg1, arg2), "debug");

	}

	@Override
	public void debug(Marker marker, String msg) {

		printOut(textMaker(marker, msg), "debug");

	}

	@Override
	public void debug(String msg, Throwable t) {

		printOut(textMaker(msg, t), "debug");

	}

	@Override
	public void debug(String format, Object... arguments) {

		printOut(textMaker(format, arguments), "debug");

	}

	@Override
	public void debug(String format, Object arg) {

		printOut(textMaker(format, arg), "debug");

	}

	@Override
	public void debug(String msg) {

		printOut(textMaker(msg), "debug");

	}

}
