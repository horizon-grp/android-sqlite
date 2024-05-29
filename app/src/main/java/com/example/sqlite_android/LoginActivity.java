package com.example.sqlite_android;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin, btnView, btnreturn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnLogin = (Button) findViewById(R.id.btnsignin1);
        btnView = (Button) findViewById(R.id.btnview);
        btnreturn = (Button) findViewById(R.id.btnreturn);
        DB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass= password.getText().toString();
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields",
                            Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                    if(checkUserPass == true) {
                        Toast.makeText(LoginActivity.this, "Sign in succesfull!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),
                                HomeActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(LoginActivity.this, "Invalidcreedentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getData();
                if(res.getCount() == 0) {
                    Toast.makeText(LoginActivity.this, "No entry exists",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0) + "\n");
                    buffer.append("Password :"+res.getString(1) + "\n\n");
                }
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(LoginActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new
                        Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}