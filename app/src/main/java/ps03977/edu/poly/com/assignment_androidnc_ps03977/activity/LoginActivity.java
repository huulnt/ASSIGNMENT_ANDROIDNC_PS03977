package ps03977.edu.poly.com.assignment_androidnc_ps03977.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import ps03977.edu.poly.com.assignment_androidnc_ps03977.R;

public class LoginActivity extends AppCompatActivity {

    EditText ed_user, ed_pass;
    Button bt_login;
    CheckBox cb;
    LoginButton lgonLoginButton;
    CallbackManager callbackManager;

    String prefname = "my_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebookSDKInitialize();
        setContentView(R.layout.login_activity);
        
        ed_user = (EditText) findViewById(R.id.ed_username);
        ed_pass = (EditText) findViewById(R.id.ed_passworld);

        cb = (CheckBox) findViewById(R.id.checkBox);

        lgonLoginButton = (LoginButton) findViewById(R.id.login_button);


        bt_login = (Button) findViewById(R.id.btn_login);

        getLoginDetails(lgonLoginButton);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_user.getText().toString().equals("admin") && ed_pass.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), " Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                    ed_user.startAnimation(shake);
                    Toast.makeText(LoginActivity.this, "Please enter an age", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), " Đăng nhập thất bại " + "\n" + " Vùi lòng kiểm tra lại tài khoảng hoặc mật khẩu ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }
    protected void getLoginDetails(LoginButton login_button){

        // Callback registration
        lgonLoginButton.setReadPermissions(Arrays.asList("email", "user_photos", "public_profile", "user_friends"));
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                GraphRequest request = GraphRequest.newMeRequest(login_result.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("LoginActivity", response.toString() + "    " + object.toString());
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");

                                    Log.e("LoginActivity", name + email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }



            @Override
            public void onCancel() {
                // code for cancellation
                Log.e("LOGIN Cancle", "LOGIN Cancle");
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
                Log.e("LOGIN Cancle", exception.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data",data.toString());
    }

    public void savingPreferences() {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre = getSharedPreferences
                (prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        String user = ed_user.getText().toString();
        String pwd = ed_pass.getText().toString();
        boolean bchk = cb.isChecked();
        if (!bchk) {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        } else {
            //lưu vào editor
            editor.putString("user", user);
            editor.putString("pwd", pwd);
            editor.putBoolean("checked", bchk);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }

    public void restoringPreferences() {
        SharedPreferences pre = getSharedPreferences
                (prefname, MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk = pre.getBoolean("checked", false);
        if (bchk) {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user = pre.getString("user", "");
            String pwd = pre.getString("pwd", "");
            ed_user.setText(user);
            ed_pass.setText(pwd);
        }
        cb.setChecked(bchk);
    }

    @Override
    protected void onPause() {

        super.onPause();
        //gọi hàm lưu trạng thái ở đây
        savingPreferences();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
        AppEventsLogger.activateApp(this);
    }
}
