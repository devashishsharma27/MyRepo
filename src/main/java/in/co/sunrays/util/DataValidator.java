package in.co.sunrays.util;

public class DataValidator {

	public boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isInteger(String val) {
		if (!(isNull(val))) {
			try {
				int i = Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public boolean isLong(String val) {
		if (!(isNull(val))) {
			try {
				long i = Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public boolean isEmail(String val) {
		if (!(isNull(val))) {
			if (val.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	
	
	public  boolean isDate(String val) {
		if (!(isNull(val))) {
			
			if (val.matches("^[0-3][0-9]+/[0-1][1-]+$")) {
				return true;
			} else {
				return false;
			}
		}else {
			return false ;
		}
	}
}
