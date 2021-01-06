package edu.upc.whatsapp;

import edu.upc.whatsapp.comms.RPC;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import edu.upc.whatsapp.service.PushService;
import entity.User;
import entity.UserInfo;

public class b_LoginActivity extends Activity implements View.OnClickListener {

  _GlobalState globalState;
  ProgressDialog progressDialog;
  User user;
  OperationPerformer operationPerformer;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    globalState = (_GlobalState)getApplication();
    setContentView(R.layout.b_login);
    (findViewById(R.id.editloginButton)).setOnClickListener(this);
  }

  public void onClick(View arg0) {
    if (arg0 == findViewById(R.id.editloginButton)) {

        // Get the texts from the login xml and set them to the user object
        User user = new User();
        user.setLogin(findViewById(R.id.editloginUserText).toString());
        user.setPassword(findViewById(R.id.editloginPasswordText).toString());

        // With processDialog we indicate a communication in progress
        progressDialog = ProgressDialog.show(this, "LoginActivity", "Logging into the server...");
        // if there's still a running thread doing something, we don't create a new one
        if (operationPerformer == null) {
          operationPerformer = new OperationPerformer();
          operationPerformer.start();
        }
    }
  }

  private class OperationPerformer extends Thread {

    @Override
    public void run() {
      Message msg = handler.obtainMessage();
      Bundle b = new Bundle();

      // Log in with the RPC and get the userInfo back
      UserInfo userInfo = RPC.login(user);
      // We put the userInfo on both message bundle and globalState
      b.putSerializable("userInfo", userInfo);
      globalState.my_user = userInfo;

      msg.setData(b);
      handler.sendMessage(msg);
    }
  }

  // Handler to interact with the UI thread
  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {

      operationPerformer = null;
      progressDialog.dismiss();

      // Get the userInfo from the msg
      UserInfo userInfo = (UserInfo) msg.getData().getSerializable("userInfo");

      if (userInfo.getId() >= 0) {
        toastShow("Login successful");
        // An Intent object is the communication unit between components of the same or different applications.
        Intent intent = new Intent(b_LoginActivity.this, d_UsersListActivity.class);
        startActivity(intent);

        // finish();
      }
      else if (userInfo.getId() == -1){
        toastShow("Login unsuccessful, try again please.");
      }
      else if (userInfo.getId() == -2){
        toastShow("Not logged in, connection problem due to: " + userInfo.getName());
      }

    }
  };

  private void toastShow(String text) {
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(0, 0, 200);
    toast.show();
  }
}
