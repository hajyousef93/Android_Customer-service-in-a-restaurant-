package com.example.easyorder.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.R;
import com.example.easyorder.connector.ResturantApiInterface;
import com.example.easyorder.classes.account;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_owner extends AppCompatActivity {
private Button CreateAccount_btn;
    private EditText fn,un,ph,pass,CPass;
    String usern,passw;
    ResturantApiInterface resturantApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_owner);
        CreateAccount_btn= findViewById(R.id.CreateAccount_btn);
        fn=(EditText)findViewById(R.id.fullName_et);
        un=(EditText)findViewById(R.id.UserName_et);
        ph=(EditText)findViewById(R.id.phonenumber_et);
        pass=(EditText)findViewById(R.id.Password_et);
        CPass=(EditText)findViewById(R.id.CPassword_et);
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        CreateAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fulln= fn.getText().toString();
                usern=  un.getText().toString();
                String poneh=  ph.getText().toString();
                passw=  pass.getText().toString();
                Call<account> call = resturantApiInterface.postAccount
                        (2,fulln,usern,passw,poneh,2);
                call.enqueue(new Callback<account>() {
                    @Override
                    public void onResponse(Call<account> call, Response<account> response) {
                        final account account = response.body();
                        Call<List<account>> cal2 = resturantApiInterface.getAccount(usern, passw, 1);
                        cal2.enqueue(new Callback<List<com.example.easyorder.classes.account>>() {
                            @Override
                            public void onResponse(Call<List<com.example.easyorder.classes.account>> call, Response<List<com.example.easyorder.classes.account>> response) {
                                List<account>accounts=response.body();
                                int id_account=accounts.get(0).getId_account();
                                Intent intent = new Intent(getApplicationContext(),Create_Restaurant.class);
                                intent.putExtra("id_account",Integer.toString(id_account));
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<com.example.easyorder.classes.account>> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<account> call, Throwable t) {

                    }
                });

            }
        });
          }
}
