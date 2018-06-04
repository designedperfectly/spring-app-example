package com.designedperfectly.passwd;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "passwd")
public class PasswdProperties {

	private boolean mustContainLowerDigitOnly;
	private boolean preventRepeatingSetChars;
	private boolean preventRepeatingMultiChars;
	private int minLength;
	private int maxLength;

	public boolean isMustContainLowerDigitOnly() {
		return mustContainLowerDigitOnly;
	}
	public void setMustContainLowerDigitOnly(boolean mustContainLowerDigitOnly) {
		this.mustContainLowerDigitOnly = mustContainLowerDigitOnly;
	}
	public boolean isPreventRepeatingSetChars() {
		return preventRepeatingSetChars;
	}
	public void setPreventRepeatingSetChars(boolean preventRepeatingSetChars) {
		this.preventRepeatingSetChars = preventRepeatingSetChars;
	}
	public boolean isPreventRepeatingMultiChars() {
		return preventRepeatingMultiChars;
	}
	public void setPreventRepeatingMultiChars(boolean preventRepeatingMultiChars) {
		this.preventRepeatingMultiChars = preventRepeatingMultiChars;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
