package pl.netplus.appbase.asynctask;

import pl.netplus.appbase.entities.ModelBase;
import pl.netplus.appbase.enums.ERepositoryException;
import pl.netplus.appbase.enums.ERepositoryManagerMethods;
import pl.netplus.appbase.enums.ExceptionErrorCodes;
import pl.netplus.appbase.exception.CommunicationException;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.repositories.ContentObjectRepository;
import pl.netplus.appbase.repositories.ReadAllDataRepository;
import android.os.AsyncTask;

public class ObjectsAsycnTask extends AsyncTask<Void, Void, Void> implements
		IHttpRequestToAsyncTaskCommunication {

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
					response.bundle = repository.insertOrUpdate(item);
				}
				break;
			case Read:
				break;
			case ReadById:
				if (repository != null) {
					response.bundle = repository.readById(item.getId());
				}
				break;
			case ReadAll:
				if (repository != null) {
					response.bundle = repository.readAll();
				}
				break;
			case ReadFavorites:
				if (repository != null) {
					if (repository instanceof ContentObjectRepository) {
						response.bundle = ((ContentObjectRepository) repository)
								.readFavorites();
					} else
						throw new CommunicationException("",
								ExceptionErrorCodes.InvalidRepository);

				}
				break;
			case ReadFromServer:
				if (repository != null) {
					response.bundle = repository.getFromServer(this);
				}
				break;
			case ReadItemContainer:
				if (repositoryItemContainer != null) {
					response.bundle = repositoryItemContainer.readAll();
				}
				break;
			case TotalCount:
				if (repository != null) {
					response.bundle = repository.readTotalCount();
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
			listener.onTaskProgressUpdate(1);
		}
	}

	public class AsyncTaskResult {
		public Object bundle;
		public RepositoryException exception;
	}

	@Override
	public void onObjectsProgressUpdate(int progressPercent) {
		if (listener != null) {
			int actualProgress = 100 / progressPercent;
			actualProgress = actualProgress + progressPercent / 1;
			listener.onTaskProgressUpdate(actualProgress);
		}

	}

	@Override
	public boolean checkIsTaskCancled() {
		// TODO Auto-generated method stub
		return false;
	}

}
