package org.mokai.action.mail;

import org.mokai.Action;
import org.mokai.Configurable;
import org.mokai.ExposableConfiguration;
import org.mokai.Message;
import org.mokai.ui.annotation.Label;

/**
 * An action that rearranges message properties to be consumed by an SMPP Connector or similar.
 *
 * @author German Escobar
 */
public class EmailToSmsAction implements Action, Configurable, ExposableConfiguration<EmailToSmsAction> {

	@Label("Use Subject as To")
	private boolean useSubjectAsTo = false;

	@Label("SMS To")
	private String smsTo;

	@Label("SMS From")
	private String smsFrom = "12345";

	@Label("Max Text Length")
	private int maxTextLength = 160;

	@Override
	public void execute(Message message) throws Exception {
		message.setProperty("emailFrom", message.getProperty("from", String.class));
		message.setProperty("from", smsFrom);

		message.setProperty("emailTo", message.getProperty("to", String.class));
		if (useSubjectAsTo) {
			message.setProperty("to", message.getProperty("subject", String.class));
		} else {
			String text = message.getProperty("subject", String.class) + " - " + message.getProperty("text", String.class);
			message.setProperty("text", text);

			message.setProperty("to", smsTo);
		}

		// cut long texts
		String text = message.getProperty("text", String.class);
		if (maxTextLength > 0 && text.length() > maxTextLength) {
			message.setProperty("text", text.substring(0, maxTextLength));
		}
	}

	@Override
	public void configure() throws Exception {
		if (!useSubjectAsTo && smsTo == null) {
			throw new Exception("You must either provide an 'smsTo' value or set 'useSubjectAsTo' as true");
		}
	}

	@Override
	public void destroy() throws Exception {

	}

	@Override
	public EmailToSmsAction getConfiguration() {
		return this;
	}

	public boolean isUseSubjectAsTo() {
		return useSubjectAsTo;
	}

	public void setUseSubjectAsTo(boolean useSubjectAsTo) {
		this.useSubjectAsTo = useSubjectAsTo;
	}

	public String getSmsTo() {
		return smsTo;
	}

	public void setSmsTo(String smsTo) {
		this.smsTo = smsTo;
	}

	public String getSmsFrom() {
		return smsFrom;
	}

	public void setSmsFrom(String smsFrom) {
		this.smsFrom = smsFrom;
	}

	public int getMaxTextLength() {
		return maxTextLength;
	}

	public void setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

}
