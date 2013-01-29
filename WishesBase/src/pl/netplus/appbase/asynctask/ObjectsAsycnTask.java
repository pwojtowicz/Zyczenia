package pl.netplus.appbase.asynctask;

import pl.netplus.appbase.entities.ModelBase;
import pl.netplus.appbase.enums.ERepositoryException;
import pl.netplus.appbase.enums.ERepositoryManagerMethods;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.repositories.ReadAllDataRepository;
import android.os.AsyncTask;

public class ObjectsAsycnTask extends AsyncTask<Void, Void, Void> {

	private AsyncTaskResult response;
	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private IBaseRepository repository;
	private ReadAllDataRepository repositoryItemContainer;
	private ModelBase item;

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, IBaseRepository repository,
			ModelBase item) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.item = item;

	}

	public ObjectsAsycnTask(IReadRepository listener,
			ReadAllDataRepository repository) {
		this.listener = listener;
		this.method = ERepositoryManagerMethods.ReadItemContainer;
		this.repositoryItemContainer = repository;
	}

	@Override
	protected Void doInBackground(Void... params) {
		response = new AsyncTaskResult();
		try {

			switch (method) {
			case InsertOrUpdate:
				if (repository != null) {
					Thread.sleep(5000);
					response.bundle = repository.insertOrUpdate(item);
				}
				break;
			case Read:
				break;
			case ReadAll:
				if (repository != null) {
					response.bundle = repository.readAll();
				}
				break;
			case ReadItemContainer:
				if (repositoryItemContainer != null) {
					response.bundle = repositoryItemContainer.readAll();
				}
				break;
			default:
				break;
			}

		} catch (Exception ex) {
			response.exception = new RepositoryException(
					ERepositoryException.AsyncTaskException, ex);
		}

		return null;
	}

	@Override
	public void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (listener != null) {
			listener.onTaskEnd();
			if (response.exception == null)
				listener.onTaskResponse(response);
			else
				listener.onTaskInvalidResponse(response.exception);
		}
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();
		if (listener != null) {
			listener.onTaskStart("");
		}
	}

	public void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		if (listener != null) {
			listener.onTaskProgress();
		}
	}

	public class AsyncTaskResult {
		public Object bundle;
		public RepositoryException exception;
	}

}
