package vn.astec.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartAsyncTask;
    private ProgressDialog progressDialog;
    public static final int progress_bar_type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartAsyncTask = findViewById(R.id.btn_start_async_task);
        btnStartAsyncTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_async_task:
                startAsyncTask();
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }
    private void startAsyncTask() {
        AsyncTask<Void, String, Void> asyncTask = new AsyncTask<Void, String, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progress_bar_type);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                for (int i=0;i<=100;i++){
                    try {
                        publishProgress(i+"");
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                progressDialog.setProgress(Integer.parseInt(values[0]));
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                dismissDialog(progress_bar_type);
            }
        };
        asyncTask.execute();
    }
}