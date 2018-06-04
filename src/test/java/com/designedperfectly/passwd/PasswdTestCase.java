package com.designedperfectly.passwd;

public class PasswdTestCase {

	private String in;
	private String resultContent;
	private String resultLength;
	private String resultSequence;

	public PasswdTestCase(String in, String resultContent, String resultLength, String resultSequence) {
		this.in = in;
		this.resultContent = resultContent;
		this.resultLength = resultLength;
		this.resultSequence = resultSequence;
	}

	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public String getResultContent() {
		return resultContent;
	}
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
	public String getResultLength() {
		return resultLength;
	}
	public void setResultLength(String resultLength) {
		this.resultLength = resultLength;
	}
	public String getResultSequence() {
		return resultSequence;
	}
	public void setResultSequence(String resultSequence) {
		this.resultSequence = resultSequence;
	}
}
