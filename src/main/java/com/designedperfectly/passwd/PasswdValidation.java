package com.designedperfectly.passwd;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class PasswdValidation {

	@Inject
	private PasswdProperties properties;

	static final String OK_CONTENT = "OK: password contains only lowercase letters and numeric digits, with at least one of each.<br>";
	static final String FAIL_CONTENT_OTHER = "FAILED: password contains characters that are neither lowercase letters nor numeric digits.<br>";
	static final String FAIL_CONTENT_LOWER = "FAILED: password has no lowercase letters.<br>";
	static final String FAIL_CONTENT_DIGIT = "FAILED: password has no numeric digits.<br>";

	static final String OK_SEQUENCE_SET = "OK: password has no sequence of characters immediately followed by the same sequence.";
	static final String FAIL_SEQUENCE_SET_CHAR = "FAILED: password has a character immediately followed by the same character.";
	static final String FAIL_SEQUENCE_SET_TWICE = "FAILED: password has a character immediately followed by the same character.";

	static final String OK_SEQUENCE_MULTI = "OK: password has no sequence of characters immediately followed by the same sequence.";
	static final String FAIL_SEQUENCE_MULTI = "FAILED: password has a character immediately followed by the same character.";

	public String OK_LENGTH;
	public String FAIL_LENGTH;

	// • Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
	// • Must have length between configured minimum and maximum characters.
	// • Must not contain any sequence of characters immediately followed by the same sequence.

	//		A "sequence" can be defined as a set of elements that can be counted in order (one or more)
	//		Or a set that elements follow one after another (implies more than one)
	//		So this configuration allows either limitation to be applied (or both makes "multi" option redundant)

	private Boolean containsLower = null;
	private Boolean containsDigit = null;
	private Boolean containsOther = null;
	private Boolean twiceChar = null;
	private Boolean twiceSeq = null;
	private Integer passwdLength = null;

	public PasswdValidation(PasswdProperties properties) {
		OK_LENGTH = "OK: password has between " + properties.getMinLength() + " and " + properties.getMaxLength() + " characters.<br>";
		FAIL_LENGTH = "FAILED: password needs to have between " + properties.getMinLength() + " and " + properties.getMaxLength() + " characters.<br>";
		this.properties = properties;
	}

	public PasswdProperties getProperties() {
		return properties;
	}

	public String validatePasswd(String passwd) {
		parsePasswd(passwd);
		return validatePasswd();
	}
	
    private void parsePasswd(String passwd)
    {
    	char[] chars = passwd.toCharArray();
    	int i = 0;

    	containsLower = false;
    	containsDigit = false;
    	containsOther = false;
    	twiceChar = false;
    	twiceSeq = false;
    	passwdLength = chars.length;

    	for ( ; i < passwdLength ; i++ ) {
    		if (chars[i] >= 'a' && chars[i] <= 'z') {
    			containsLower = true;
    		} else if (chars[i] >= '0' && chars[i] <= '9') {
    			containsDigit = true;
    		} else {
    			containsOther = true;
    		}

    		// Note: because there are limits to where duplicated characters will
    		// appear in a string, only certain indexes have to be checked.
    		// This calculation is intended to optimize where to look for repeating
    		// characters since you can't for example duplicate more than half of
    		// the passwd (by definition, duplication requires twice as many
    		// characters as what is being duplicated)

    		int j = i - chars.length;
    		j +=  i;

			if ( j < 0 )
				j = 0;

    		for ( ; j < i; j++ ) {
    			if ( chars[i] == chars[j] ) {

    				if ( j + 1 == i ) {
    					twiceChar = true;
    				} else if ( passwd.substring(j, i).equals(passwd.substring(i, i + i - j)) ) {
    					twiceSeq = true;
    				}
    			}
    		}
    	}
    }

    private String validatePasswd() {
    	StringBuilder responseText = new StringBuilder("");
    	String response;

    	try {
    		if (properties.isMustContainLowerDigitOnly()) {
		    	if ( containsLower && containsDigit && ! containsOther ) {
		    		responseText.append(OK_CONTENT);
		    	} else {
		    		if ( containsOther ) {
		    			responseText.append(FAIL_CONTENT_OTHER);
			    	}
		    		if ( ! containsLower ) {
			    		responseText.append(FAIL_CONTENT_LOWER);
			    	}
		    		if ( ! containsDigit ) {
			    		responseText.append(FAIL_CONTENT_DIGIT);
			    	}
		    	}
    		}

	    	if ( properties.getMinLength() <= passwdLength && passwdLength <= properties.getMaxLength() ) {
	    		responseText.append(OK_LENGTH);
	    	} else {
	    		responseText.append(FAIL_LENGTH);
	    	}

	    	if ( properties.isPreventRepeatingSetChars() ) {
		    	if ( twiceChar ) {
		    		responseText.append(FAIL_SEQUENCE_SET_CHAR);
		    	} else if ( twiceSeq ) {
		    		responseText.append(FAIL_SEQUENCE_SET_TWICE);
		    	} else {
		    		responseText.append(OK_SEQUENCE_SET);
		    	}
	    	} else if ( properties.isPreventRepeatingMultiChars() ) {
		    	if ( twiceSeq ) {
		    		responseText.append(FAIL_SEQUENCE_MULTI);
		    	} else {
		    		responseText.append(OK_SEQUENCE_MULTI);
		    	}
	    	}
	    	response = responseText.toString();
    	} catch (NullPointerException npe) {
    		response = "ERROR - Must parse password before requesting validation response.";
    	} catch (Exception ex) {
    		response = "ERROR - " + ex.getMessage();
    	}
    	return response;
    }
}
