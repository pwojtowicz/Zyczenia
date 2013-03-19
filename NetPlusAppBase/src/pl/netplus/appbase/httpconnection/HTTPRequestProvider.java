package pl.netplus.appbase.httpconnection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class HTTPRequestProvider {

	/**
	 * Timeout po��czenia
	 */
	public static final int CONNECTION_TIMEOUT = 30000;
	/**
	 * Timeout odczytu
	 */
	public static final int READ_TIMEOUT = 30000;

	public static HTTPRequestBundle sendRequest(String address,
			IHttpRequestToAsyncTaskCommunication listener) {
		checkIsOnLine();

		return getRequest(address, listener);

	}

	private static HTTPRequestBundle getRequest(String address,
			IHttpRequestToAsyncTaskCommunication listener) {

		// Log.i("WishUrlAddress", address);
		HttpURLConnection connection = null;
		String response = null;
		byte[] rawResponse = null;
		address = address.replace(" ", "%20");
		int responseCode = 0;
		try {
			connection = getConnection(address);
			connection.setRequestMethod("GET");

			connection = setConnectionHeader(connection);

			connection.connect();
			responseCode = connection.getResponseCode();

			int fileSize = connection.getContentLength();

			InputStream in = getConnectionInputStream(connection);

			// ByteArrayOutputStream byteArray = readInpdutStream(in, fileSize,
			// listener);

			// System.out.println("Content Size: " + String.valueOf(fileSize)
			// + " byteArray Size: " + String.valueOf(byteArray.size()));

			try {
				response = readStream(in);// slurp(in, 4096);//
											// byteArray.toString();//
											// ("ISO-8859-2");
			} catch (Exception e) {
				response = "ConvertToStringProblem";
			}
			connection.disconnect();
			connection = null;

		} catch (IOException e) {
			response = e.getMessage();
		} catch (Exception ex) {
			response = ex.getMessage();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}

		}

		HTTPRequestBundle bundle = new HTTPRequestBundle();
		bundle.setResponse(response);
		bundle.setRawResponse(rawResponse);
		bundle.setStatusCode(responseCode);

		return bundle;
	}

	public static InputStream getConnectionInputStream(
			HttpURLConnection connection) throws IOException {
		InputStream input = null;
		String encoding = connection.getHeaderField("Content-Encoding");
		int responseCode = connection.getResponseCode();

		if (responseCode == 400)
			input = connection.getErrorStream();
		else
			input = connection.getInputStream();

		if (encoding != null && encoding.equals("gzip"))
			input = new GZIPInputStream(input);

		return input;
	}

	private static ByteArrayOutputStream readInpdutStream(
			InputStream inputStream, int fileSize,
			IHttpRequestToAsyncTaskCommunication listener) throws IOException {
		byte[] buf = new byte[4096];
		char[] ch = new char[4096];

		slurp(inputStream, 4096);
		int ret = 0;
		long downloadedSize = 0;
		int lastProgressValue = -1;
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		while ((ret = inputStream.read(buf)) > 0) {
			byteArray.write(buf, 0, ret);

			downloadedSize += ret;
			int progressPercent = 0;
			if (fileSize != 0)
				progressPercent = (int) (downloadedSize * 100) / fileSize;

			if ((progressPercent % 5) == 0
					&& lastProgressValue < progressPercent) {

				if (listener != null) {
					listener.onObjectsProgressUpdate(progressPercent);
					if (listener.checkIsTaskCancled()) {
						break;
					}
				}
				lastProgressValue = progressPercent;
			}
		}
		return byteArray;
	}

	public static String slurp(final InputStream is, final int bufferSize) {
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		try {
			final Reader in = new InputStreamReader(is, "UTF-8");
			try {
				for (;;) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					out.append(buffer, 0, rsz);
				}
			} finally {
				in.close();
			}
		} catch (UnsupportedEncodingException ex) {
			/* ... */
		} catch (IOException ex) {
			/* ... */
		}
		return out.toString();
	}

	private static String readStream(InputStream iStream) throws IOException {
		// build a Stream Reader, it can read char by char
		InputStreamReader iStreamReader = new InputStreamReader(iStream);
		// build a buffered Reader, so that i can read whole line at once
		BufferedReader bReader = new BufferedReader(iStreamReader);
		String line = null;
		StringBuilder builder = new StringBuilder();
		try {
			while ((line = bReader.readLine()) != null) { // Read till end
				builder.append(line);
			}
		} catch (Exception ex) {

		} finally {
			bReader.close(); // close all opened stuff
			iStreamReader.close();
			iStream.close();
		}
		return builder.toString();
	}

	private static HttpURLConnection getConnection(String address)
			throws IOException {
		URL url = new URL(address);
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		return connection;
	}

	private static HttpURLConnection setConnectionHeader(
			HttpURLConnection connection) {

		// oczekiwany typ zwracanych danych - JSON
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Accept-Encoding", "gzip");

		connection.setConnectTimeout(CONNECTION_TIMEOUT);
		connection.setReadTimeout(READ_TIMEOUT);

		return connection;
	}

	private static void checkIsOnLine() {
		// TODO Auto-generated method stub

	}
}
