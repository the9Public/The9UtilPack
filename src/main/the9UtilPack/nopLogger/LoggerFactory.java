package the9UtilPack.nopLogger;

import org.slf4j.Logger;


public class LoggerFactory {

	private static boolean DISPLAYED_WARNING = false;

	public static Logger getLogger(Class<?> clazz) {

		Logger log = org.slf4j.LoggerFactory.getLogger(clazz);

		if ( "NOP".equals(log.getName()) ) {
			log = new NopLogger(clazz);

			if ( !DISPLAYED_WARNING ) {
				System.err.println("========================== CHECK THIS =====================");
				System.err.println(log.getName() + " is USED please implement SLF4J provider");
				System.err.println("===========================================================");
			}

			DISPLAYED_WARNING = true;
		}

		return log;

	}

}
