package pl.netplus.appbase.httpconnection;

public interface IHttpRequestToAsyncTaskCommunication {

	void onObjectsProgressUpdate(int progressPercent);

	boolean checkIsTaskCancled();

}
