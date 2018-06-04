package com.designedperfectly.passwd;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PasswdTest extends SpringBootPasswdTest {

	@Inject
	private PasswdValidation tester;

	@Inject
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Inject
	private ObjectMapper mapper;

	private ArrayList<PasswdTestCase> testCases = new ArrayList<>();

	private final int TEST_CONFIG_MIN_LENGTH = 5;
	private final int TEST_CONFIG_MAX_LENGTH = 12;
	private final boolean TEST_CONFIG_MUST_CONTAIN_LOWER_DIGIT_ONLY = true;
	private final boolean TEST_CONFIG_PREVENT_REPEATING_MULTI_CHARS = true;
	private final boolean TEST_CONFIG_PREVENT_REPEATING_SET_CHARS = true;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		// While additional tests could be added to show variations, such as allowing other length limits
		// these tests sufficiently show that the requested configuration works, and there are limited
		// variations which are even interestingly different than the one being tested, such as what
		// happens when length must be 0, but lower and digits are required (no passwd would be accepted.)

		// There would seem to be *no* such case where *any* new tests for *any other* configuration
		// would reveal a failure that these tests would not have already identified.  If it was
		// possible to show that such a test exists for another configuration, it should be added below
		// along with the appropriate configuration for that test.

		tester.getProperties().setMinLength(TEST_CONFIG_MIN_LENGTH);
		tester.getProperties().setMaxLength(TEST_CONFIG_MAX_LENGTH);
		tester.getProperties().setMustContainLowerDigitOnly(TEST_CONFIG_MUST_CONTAIN_LOWER_DIGIT_ONLY);
		tester.getProperties().setPreventRepeatingMultiChars(TEST_CONFIG_PREVENT_REPEATING_MULTI_CHARS);
		tester.getProperties().setPreventRepeatingSetChars(TEST_CONFIG_PREVENT_REPEATING_SET_CHARS);

		testCases.clear();
		testCases.add(new PasswdTestCase("abab", PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abc", PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abb", PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("ababab", PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abcdef", PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abbdef", PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("0101", PasswdValidation.FAIL_CONTENT_LOWER, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("456", PasswdValidation.FAIL_CONTENT_LOWER, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("778", PasswdValidation.FAIL_CONTENT_LOWER, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("010101", PasswdValidation.FAIL_CONTENT_LOWER, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("456789", PasswdValidation.FAIL_CONTENT_LOWER, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("178823", PasswdValidation.FAIL_CONTENT_LOWER, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("abc123!@#$!@#$yz", PasswdValidation.FAIL_CONTENT_OTHER, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abc123!@#4!@#xyz", PasswdValidation.FAIL_CONTENT_OTHER, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abc123!@##!@#xyz", PasswdValidation.FAIL_CONTENT_OTHER, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("abc123ABCABC", PasswdValidation.FAIL_CONTENT_OTHER, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abc123ABC123", PasswdValidation.FAIL_CONTENT_OTHER, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abb123ABB123", PasswdValidation.FAIL_CONTENT_OTHER, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("a1a1", PasswdValidation.OK_CONTENT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abc1", PasswdValidation.OK_CONTENT, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("a11", PasswdValidation.OK_CONTENT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("a1a1a1", PasswdValidation.OK_CONTENT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abc123", PasswdValidation.OK_CONTENT, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abb123", PasswdValidation.OK_CONTENT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("ABC123!@#$!@#$YZ", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("ABC123!@#4!@#XYZ", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("ABC123!@##!@#XYZ", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("AB12!@#$!@#$", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("AB12!@#4!@#$", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("AB12!@##!@#$", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("abcDEF!@#$!@#$yz", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abcDEF!@#Q!@#xyz", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abcDEF!@##!@#xyz", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("abDE!@#$!@#$", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("abDE!@#Q!@#x", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("abDE!@##!@#x", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("", PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));

		testCases.add(new PasswdTestCase("ABCDEF!@#$!@#$YZ", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("ABCDEF!@#Q!@#XYZ", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("ABCDEF!@##!@#XYZ", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.FAIL_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));

		testCases.add(new PasswdTestCase("ABCD!@#$!@#$", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_TWICE));
		testCases.add(new PasswdTestCase("ABCD!@#Q!@#X", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.OK_SEQUENCE_SET));
		testCases.add(new PasswdTestCase("ABCD!@##!@#X", PasswdValidation.FAIL_CONTENT_OTHER + PasswdValidation.FAIL_CONTENT_LOWER + PasswdValidation.FAIL_CONTENT_DIGIT, tester.OK_LENGTH, PasswdValidation.FAIL_SEQUENCE_SET_CHAR));
	}

    @Test
    public void validPasswd() {

    	try {
	    	for (int i = 0; i < testCases.size(); i++) {
	    		String json = mapper.writeValueAsString(new PasswdBody(testCases.get(i).getIn()));

		    	mockMvc.perform(post("/passwd").contentType(MediaType.APPLICATION_JSON)
		        	.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(jsonPath("$.passwd").value(testCases.get(i).getResultContent() + testCases.get(i).getResultLength() + testCases.get(i).getResultSequence()));
	    	}
    	} catch (Exception ex) {
    		assertTrue(false);
    	}
    }
}