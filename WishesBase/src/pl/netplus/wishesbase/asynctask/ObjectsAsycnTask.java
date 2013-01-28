package pl.netplus.wishesbase.asynctask;

import pl.netplus.wishesbase.enums.ERepositoryException;
import pl.netplus.wishesbase.enums.ERepositoryManagerMethods;
import pl.netplus.wishesbase.exception.RepositoryException;
import pl.netplus.wishesbase.interfaces.IBaseRepository;
import pl.netplus.wishesbase.interfaces.IReadRepository;
import android.os.AsyncTask;

public class ObjectsAsycnTask<T> extends AsyncTask<Void, Void, Void> {
	
	private AsyncTaskResult response;
	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private IBaseRepository<T> repository;

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, IBaseRepository<T> repository) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;

	}
	
	@Override
	protected Void doInBackground(Void... params) {
		response = new AsyncTaskResult();
		try {
			
			switch (method) {
			case Read: break;
			case ReadAll: break;
			default: break;
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
			listener.onTaskStart();
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
