package com.ian.tools.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * <p/>
 * Package: com.ian.tools.other <br>
 * File Name: EmailValidator <br>
 * <p/>
 * Purpose: Email Validator <br>
 * 
 * @ClassName: com.ian.tools.other.EmailValidator
 * @Description: Array Use
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2021年06月02日
 * @see https://blog.csdn.net/cunchi4221/article/details/107470879
 */

public class EmailValidator {

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

	// static Pattern object, since pattern is fixed
	private Pattern pattern;

	// non-static Matcher object because it's created from the input String
	private Matcher matcher;

	public EmailValidator() {
		// initialize the Pattern object
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	}

	/**
	 * This method validates the input email address with EMAIL_REGEX pattern
	 * 
	 * @param email
	 * @return boolean
	 */
	public boolean validateEmail(String email) {
		if (null == email) {
			return false;
		} else {
			matcher = pattern.matcher(email);
			return matcher.matches();

		}
	}

}
