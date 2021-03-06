package pl.netplus.appbase.interfaces;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.exception.RepositoryException;

public interface IReadRepository {

	void onTaskEnd();

	void onTaskStart(String message, boolean showProgress);

	void onTaskResponse(AsyncTaskResult response);

	void onTaskInvalidResponse(RepositoryException exception);

	void onTaskProgressUpdate(int actualProgress);

}
