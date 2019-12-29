package com.example.easyorder.User.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.easyorder.R;
import com.example.easyorder.classes.Reservation;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.ResturantCooker.Main_Cooker;
import com.example.easyorder.ResturantManager.Manager_Home_Activity;
import com.example.easyorder.classes.account;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;
import com.example.easyorder.owner.Main_owner;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity{
    private ResturantApiInterface resturantApiInterface;
    String user_name,time,date;
    List<account>Account;
    public static final int MINUTE=Calendar.getInstance().get(Calendar.MINUTE);
    public static final int HOURS=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    int id_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      RadioButton checkBox=findViewById(R.id.checkbox);
         Button signin=(Button)findViewById(R.id.signin);
        Button signup=(Button)findViewById(R.id.signup);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        date= df.format(Calendar.getInstance().getTime());
        DateFormat dff = new SimpleDateFormat("HH:MM");
        time=dff.format(Calendar.getInstance().getTime());

        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            } });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 EditText username=findViewById(R.id.username);
                 EditText password1=findViewById(R.id.password);

                 user_name=username.getText().toString();
                String password=password1.getText().toString();
if(user_name==null){
    Toast.makeText(MainActivity.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
}
else if(password==null){
    Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
}
else if(username==null  && password==null){
    Toast.makeText(MainActivity.this, "Please Enter User Name and Password", Toast.LENGTH_SHORT).show();
}

else {

    Call<List<account>> call = resturantApiInterface.getAccount(user_name,password,1);

    call.enqueue(new Callback <List<account>>(){
                    @Override
                    public void onResponse(Call <List<account>> call, Response<List<account>>response) {

                    Account = response.body();
                        if (Integer.toString(Account.get(0).getId_account()) != null) {
                            if (Account.get(0).getId_acc_type() == 4) {
                                id_account=Account.get(0).getId_account();
    Call<List<Reservation>> cal2 = resturantApiInterface.getReservationTimeAndDate(id_account,23);
    cal2.enqueue(new Callback<List<Reservation>>() {
    @Override
    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
        List<Reservation> res=response.body();
        if(res.size()!=0){
        for(int i=0;i<res.size();i++){
            int hours=Integer.parseInt(res.get(i).getHours());
            int min=Integer.parseInt(res.get(i).getMin());
            if(((HOURS-hours==0)&&((MINUTE-min>=0)&&(MINUTE-min<=30)))){
                Intent intent = new Intent(getApplicationContext(), meal_typeActivity.class);
                intent.putExtra("id_resturant",Integer.toString(res.get(i).getId_resturant()));
                intent.putExtra("id_reservation",Integer.toString(res.get(i).getId_reservation()));
                intent.putExtra("id_account",Integer.toString(Account.get(0).getId_account()));
                intent.putExtra("reservation_type",Integer.toString(4));
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("id_account",Integer.toString(Account.get(0).getId_account()));
                startActivity(intent);}

        } }
        else {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("id_account",Integer.toString(Account.get(0).getId_account()));
            startActivity(intent);
        }

    }

    @Override
    public void onFailure(Call<List<Reservation>> call, Throwable t) {

    }
});
                        }
                        else if(Account.get(0).getId_acc_type() == 2) {
                            id_account=Account.get(0).getId_account();
                                Call<List<Resturant>> cal3 = resturantApiInterface.getManagerResturant(id_account,15);
                                cal3.enqueue(new Callback<List<Resturant>>() {
                                                 @Override
                                                 public void onResponse(Call<List<Resturant>> call, Response<List<Resturant>> response) {
                                                     List<Resturant> res = response.body();
                                                     Intent intent = new Intent(getApplicationContext(), Manager_Home_Activity.class);
                                                     intent.putExtra("RESTURANT_OBJECT",res.get(0));
                                                     startActivity(intent);
                                               }

                                                 @Override
                                                 public void onFailure(Call<List<Resturant>> call, Throwable t) {

                                                 }
                                             });
                            }
                            else if(Account.get(0).getId_acc_type() == 3) {
                                Intent intent = new Intent(getApplicationContext(), Main_Cooker.class);
                                intent.putExtra("id_cooker",Integer.toString(Account.get(0).getId_account()));
                                startActivity(intent);
                            }
                            else if(Account.get(0).getId_acc_type() == 1) {
                                Intent intent = new Intent(getApplicationContext(), Main_owner.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<account>>call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainActivity.this, "Please check User Name and Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            } }
        });

    }
}
