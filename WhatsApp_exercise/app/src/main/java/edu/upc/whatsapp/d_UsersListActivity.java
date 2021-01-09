package edu.upc.whatsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import edu.upc.whatsapp.comms.RPC;
import edu.upc.whatsapp.adapter.MyAdapter_users;
import entity.UserInfo;

import java.util.List;

public class d_UsersListActivity extends Activity {

    _GlobalState globalState;
    MyAdapter_users adapter;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalState = (_GlobalState) getApplication();
        setContentView(R.layout.d_userslist);
        new DownloadUsers_Task().execute();
    }

    private class DownloadUsers_Task extends AsyncTask<Void, Void, List<UserInfo>> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(d_UsersListActivity.this, "UsersListActivity",
                    "downloading the users...");
        }

        @Override
        protected List<UserInfo> doInBackground(Void... nothing) {
            return RPC.allUserInfos();
        }

        @Override
        protected void onPostExecute(List<UserInfo> users) {
            progressDialog.dismiss();
            if (users == null) {
                toastShow("There's been an error downloading the users");
            } else {
                ListView usersListView = findViewById(R.id.listView);
                adapter = new MyAdapter_users(getApplicationContext(), users);
                usersListView.setAdapter(adapter);
                //perform listView item click event
                usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { // Change to messages activity on click an user
                        Intent intent = new Intent(d_UsersListActivity.this, e_MessagesActivity.class);
                        globalState.user_to_talk_to = (UserInfo) adapter.getItem(i);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private void toastShow(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(0, 0, 200);
        toast.show();
    }

}
