package pl.netplus.wishesbase.interfaces;

import pl.netplus.wishesbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.wishesbase.exception.RepositoryException;

public interface IReadRepository {

	void onTaskEnd();

	void onTaskStart();

	void onTaskResponse(AsyncTaskResult response);

	void onTaskInvalidResponse(RepositoryException exception);

	void onTaskProgress();

}
