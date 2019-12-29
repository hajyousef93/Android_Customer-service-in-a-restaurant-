package com.example.easyorder.User.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyorder.R;
import com.example.easyorder.classes.account;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity {
    private EditText fn,un,ph,pass,CPass;
    private Button ca;
    String usern,passw;
    private ResturantApiInterface resturantApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
         fn=(EditText)findViewById(R.id.fullName_et);
         un=(EditText)findViewById(R.id.UserName_et);
         ph=(EditText)findViewById(R.id.phonenumber_et);
         pass=(EditText)findViewById(R.id.Password_et);
         CPass=(EditText)findViewById(R.id.CPassword_et);
         ca=(Button) findViewById(R.id.CreateAccount_btn);

        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fulln= fn.getText().toString();
                 usern=  un.getText().toString();
               String poneh=  ph.getText().toString();
                 passw=  pass.getText().toString();


                Call<account> call = resturantApiInterface.postAccount
                        (4,fulln,usern,passw,poneh,2);
                call.enqueue(new Callback<account>() {
                    @Override
                    public void onResponse(Call<account>call, Response<account> response) {
                        account account = response.body();
                        Call<List<account>> cal2 = resturantApiInterface.getAccount(usern, passw, 1);
                        cal2.enqueue(new Callback<List<account>>() {
                            @Override
                            public void onResponse(Call<List<account>> call, Response<List<account>> response) {
                                List<account> Account = response.body();
                                if (Integer.toString(Account.get(0).getId_account()) != null) {

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("id_account", Integer.toString(Account.get(0).getId_account()));
                                    intent.putExtra("user_name", usern.toString());
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void onFailure(Call<List<account>>call, Throwable t) {
                                Toast.makeText(CreateAccount.this, "Error", Toast.LENGTH_SHORT).show();

                            }


                        });
                    }

                    @Override
                    public void onFailure(Call<account> call, Throwable t) {
                        Toast.makeText(CreateAccount.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
