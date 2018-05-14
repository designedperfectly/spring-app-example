package com.designedperfectly.passwd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PasswdTest {

    @Test
    public void validPasswd() {
    	PasswdController tester = new PasswdController();

    	assertEquals(tester.validatePasswd("abab"), PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abc"), PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abb"), PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("ababab"), PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abcdef"), PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abbdef"), PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("0101"), PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("456"), PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("778"), PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("010101"), PasswdController.FAIL_CONTENT_LOWER + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("456789"), PasswdController.FAIL_CONTENT_LOWER + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("178823"), PasswdController.FAIL_CONTENT_LOWER + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("abc123!@#$!@#$yz"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abc123!@#4!@#xyz"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abc123!@##!@#xyz"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("abc123ABCABC"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abc123ABC123"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abb123ABB123"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("a1a1"), PasswdController.OK_CONTENT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abc1"), PasswdController.OK_CONTENT + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("a11"), PasswdController.OK_CONTENT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("a1a1a1"), PasswdController.OK_CONTENT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abc123"), PasswdController.OK_CONTENT + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abb123"), PasswdController.OK_CONTENT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("ABC123!@#$!@#$YZ"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("ABC123!@#4!@#XYZ"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("ABC123!@##!@#XYZ"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("AB12!@#$!@#$"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("AB12!@#4!@#$"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("AB12!@##!@#$"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("abcDEF!@#$!@#$yz"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abcDEF!@#Q!@#xyz"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abcDEF!@##!@#xyz"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("abDE!@#$!@#$"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("abDE!@#Q!@#x"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("abDE!@##!@#x"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd(""), PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);

    	assertEquals(tester.validatePasswd("ABCDEF!@#$!@#$YZ"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("ABCDEF!@#Q!@#XYZ"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("ABCDEF!@##!@#XYZ"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.FAIL_LENGTH + PasswdController.FAIL_TWICE_CHAR);

    	assertEquals(tester.validatePasswd("ABCD!@#$!@#$"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_SEQ);
    	assertEquals(tester.validatePasswd("ABCD!@#Q!@#X"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.OK_SEQUENCE);
    	assertEquals(tester.validatePasswd("ABCD!@##!@#X"), PasswdController.FAIL_CONTENT_OTHER + PasswdController.FAIL_CONTENT_LOWER + PasswdController.FAIL_CONTENT_DIGIT + PasswdController.OK_LENGTH + PasswdController.FAIL_TWICE_CHAR);
    }
}