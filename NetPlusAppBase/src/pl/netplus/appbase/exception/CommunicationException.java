package pl.netplus.appbase.exception;

import pl.netplus.appbase.enums.ExceptionErrorCodes;

public class CommunicationException extends Exception {

	private ExceptionErrorCodes errorCode;

	public CommunicationException(String string, ExceptionErrorCodes errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode.toString();
	}

}
