package com.designedperfectly.passwd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswdController {

	static final String OK_CONTENT = "OK: password contains only lowercase letters and numeric digits, with at least one of each.<br>";
	static final String FAIL_CONTENT_OTHER = "FAILED: password contains characters that are neither lowercase letters nor numeric digits.<br>";
	static final String FAIL_CONTENT_LOWER = "FAILED: password has no lowercase letters.<br>";
	static final String FAIL_CONTENT_DIGIT = "FAILED: password has no numeric digits.<br>";

	static final String OK_LENGTH = "OK: password has between 5 and 12 characters.<br>";
	static final String FAIL_LENGTH = "FAILED: password needs to have between 5 and 12 characters.<br>";

	static final String OK_SEQUENCE = "OK: password has no sequence of characters immediately followed by the same sequence.";
	static final String FAIL_TWICE_CHAR = "FAILED: password has a character immediately followed by the same character.";
	static final String FAIL_TWICE_SEQ = "FAILED: password has a sequence of characters immediately followed by the same sequence.";

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ResponseEntity<?> errorPasswd() {
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/passwd", method = RequestMethod.POST)
    public ResponseEntity<?> getPasswdResponse(@ModelAttribute PasswdBody passwdBody)
    {
    	ResponseEntity<String> response = new ResponseEntity<String>(validatePasswd(passwdBody.getPasswd()), HttpStatus.OK);
        return response;
    }

    public String validatePasswd(String passwd)
    {
    	// • Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
    	// • Must be between 5 and 12 characters in length.
    	// • Must not contain any sequence of characters immediately followed by the same sequence.

    	StringBuilder responseText = new StringBuilder("");
    	char[] chars = passwd.toCharArray();
    	int i = 0;
    	boolean isContainsLower = false;
    	boolean isContainsDigit = false;
    	boolean isContainsOther = false;

    	// If only multiple character sequences are invalid, disable where marked below

    	boolean isTwiceChar = false;		// Can disable to allow single characters in sequence
    	boolean isTwiceSeq = false;

    	for ( ; i < chars.length; i++ ) {
    		if (chars[i] >= 'a' && chars[i] <= 'z') {
    			isContainsLower = true;
    		} else if (chars[i] >= '0' && chars[i] <= '9') {
    			isContainsDigit = true;
    		} else {
    			isContainsOther = true;
    		}

    		int j = i - chars.length;
    		j +=  i;

			if ( j < 0 )
				j = 0;

    		for ( ; j < i; j++ ) {
    			if ( chars[i] == chars[j] ) {

					// Can disable 'if' to allow single characters in sequence
    				if ( j + 1 == i ) {
    					isTwiceChar = true;
    				} else
    			    // end of code to disable to allow single characters in sequence

    				if ( passwd.substring(j, i).equals(passwd.substring(i, i + i - j)) ) {
    					isTwiceSeq = true;
    				}
    			}
    		}
    	}
    	if ( isContainsLower && isContainsDigit && ! isContainsOther ) {
    		responseText.append(OK_CONTENT);
    	} else {
    		if ( isContainsOther ) {
    			responseText.append(FAIL_CONTENT_OTHER);
	    	}
    		if ( ! isContainsLower ) {
	    		responseText.append(FAIL_CONTENT_LOWER);
	    	}
    		if ( ! isContainsDigit ) {
	    		responseText.append(FAIL_CONTENT_DIGIT);
	    	}
    	}

    	if ( i >= 5 && i <= 12 ) {
    		responseText.append(OK_LENGTH);
    	} else {
    		responseText.append(FAIL_LENGTH);
    	}

		// Can disable 'if' to allow single characters in sequence
    	if ( isTwiceChar ) {
    		responseText.append(FAIL_TWICE_CHAR);
    	} else
    	// end of code to disable to allow single characters in sequence

    	if ( isTwiceSeq ) {
    		responseText.append(FAIL_TWICE_SEQ);
    	} else {
    		responseText.append(OK_SEQUENCE);
    	}

    	return responseText.toString();
    }
}