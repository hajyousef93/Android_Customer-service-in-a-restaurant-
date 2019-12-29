package com.example.easyorder.User.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyorder.R;
import com.example.easyorder.classes.Reservation;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;

public class Reservation_Activity extends AppCompatActivity {
    EditText NumberOfPeople;
    TextView Date,Time;
    String getNumberOfPeople,dateWithQ,date,amPm,time;
    private int id_table,num_table,currentHour,CurrentMinuts, id_resturant,id_account,id_resturant_table,num;
    Button submit;
    Calendar cal;
    Calendar calendar;
    int reservation_type;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    TimePickerDialog timePickerDialog;
    private ResturantApiInterface resturantApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_);
        final CheckBox takeaway=findViewById(R.id.takeaway);
        final CheckBox onTheRestaurant=findViewById(R.id.OnTheRestaurrent);
        submit=findViewById(R.id.Submit);
        takeaway.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.italics));
        final CheckBox resrvation=findViewById(R.id.reservation);
        resrvation.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.italics));
        onTheRestaurant.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.italics));
        NumberOfPeople=findViewById(R.id.numerOfpeople_et);
        Date=findViewById(R.id.DateOfReservation_et);
        Time=findViewById(R.id.TimeOfReservation_et);
        Date.setEnabled(false);
        Time.setEnabled(false);
        NumberOfPeople.setEnabled(false);
        Date.setVisibility(View.INVISIBLE);
        Time.setVisibility(View.INVISIBLE);
        NumberOfPeople.setVisibility(View.INVISIBLE);
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        resrvation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    NumberOfPeople.setEnabled(true);
                    Date.setEnabled(true);
                    Time.setEnabled(true);
                    Date.setVisibility(View.VISIBLE);
                    Time.setVisibility(View.VISIBLE);
                    NumberOfPeople.setVisibility(View.VISIBLE);
                }
                else {
                    Date.setEnabled(false);
                    Time.setEnabled(false);
                    NumberOfPeople.setEnabled(false);
                    Date.setVisibility(View.INVISIBLE);
                    Time.setVisibility(View.INVISIBLE);
                    NumberOfPeople.setVisibility(View.INVISIBLE);  }
            }
        });
        onTheRestaurant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    NumberOfPeople.setEnabled(true);
                    NumberOfPeople.setVisibility(View.VISIBLE);
                } else {

                    NumberOfPeople.setEnabled(false);
                    NumberOfPeople.setVisibility(View.INVISIBLE);
                }
            }});
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal=  Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(
                        Reservation_Activity.this,
                        R.style.Theme_AppCompat_Light_DarkActionBar,
                        onDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_light)));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date= year +"-"+ month+"-"+dayOfMonth;
                // dateWithQ=year+"-"+month+"-"+dayOfMonth;
                Date.setText(String.format("%04d-%02d-%02d",year,month,dayOfMonth));
                dateWithQ=String.format("%04d-%02d-%02d",year,month,dayOfMonth);
                //     Date.setText(date);
            }
        };
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar= Calendar.getInstance();
                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                CurrentMinuts=calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(
                        Reservation_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm="PM";
                        }
                        else {
                            amPm="AM";
                        }
                        Time.setText(String.format("%02d:%02d",hourOfDay,minute)+amPm);
                        time=String.format("%02d:%02d",hourOfDay,minute);

                    }
                } ,currentHour,CurrentMinuts,false);
                timePickerDialog.show();
            }
        });
        id_resturant=Integer.parseInt(getIntent().getStringExtra("id_resturant"));
        id_account=Integer.parseInt(getIntent().getStringExtra("id_account"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resrvation.isChecked() && takeaway.isChecked() && onTheRestaurant.isChecked()) {
                    Toast.makeText(Reservation_Activity.this, "Please Only Select One", Toast.LENGTH_SHORT).show();
                } else if (resrvation.isChecked()) {
                    reservation_type=1;
                    Call<List<Reservation>> call1 = resturantApiInterface.getTablesType(id_resturant, 5);
                    call1.enqueue(new Callback<List<Reservation>>() {
                        @Override
                        public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                            getNumberOfPeople = NumberOfPeople.getText().toString();
                            List<Reservation> reservationList = response.body();
                            for (int i = 0; i < reservationList.size(); i++) {
                                if (reservationList.get(i).getType_table() >= Integer.parseInt(getNumberOfPeople)) {
                                    id_table = reservationList.get(i).getId_table();
                                    Call<List<Reservation>> call2 = resturantApiInterface.getTable(id_resturant, id_table, 6);
                                    call2.enqueue(new Callback<List<Reservation>>() {
                                        @Override
                                        public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                                            List<Reservation> aa = response.body();
                                            if (Integer.toString(aa.get(0).getNum_table()) == null) {
                                                num_table = 0;
                                            } else {
                                                num_table = aa.get(0).getNum_table();
                                            }
                                            id_resturant_table = aa.get(0).getId_resturant_table();
                                            Call<List<Reservation>> call3 = resturantApiInterface.getReservesdTables(id_resturant_table, 8);
                                            call3.enqueue(new Callback<List<Reservation>>() {
                                                @Override
                                                public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                                                    List<Reservation> body = response.body();
                                                    if (body.get(0).getCount() < num_table) {
                                                        if (response.isSuccessful()) {
                                                            reservation_type=1;
                                                            Call<List<Reservation>> call4 = resturantApiInterface.getNumberOfVisites(id_account,9);
                                                            call4.enqueue(new Callback <List<Reservation>>(){
                                                                @Override
                                                                public void onResponse(Call <List<Reservation>> call, Response<List<Reservation>>response) {
                                                                    List<Reservation>numberofvisit = response.body();
                                                                    num=numberofvisit.get(0).getNumOfVisites();
                                                                    if ( num <4) {
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,1,1,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);
                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, dateWithQ, num,Integer.toString(currentHour),Integer.toString(CurrentMinuts), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(), meal_typeActivity.class);
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                    else if(num<8){
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,1,2,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);

                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, dateWithQ, num,Integer.toString(currentHour),Integer.toString(CurrentMinuts), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                    else if(num<12){
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,1,3,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);

                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, dateWithQ, num,Integer.toString(currentHour),Integer.toString(CurrentMinuts), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                    else {
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,1,4,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);

                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, dateWithQ, num,Integer.toString(currentHour),Integer.toString(CurrentMinuts), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                                @Override
                                                                public void onFailure(Call<List<Reservation>>call, Throwable t) {
                                                                    if (t instanceof IOException) {
                                                                        Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else {
                                                                        Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    Toast.makeText(Reservation_Activity.this, "Error from call 4", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                    if (t instanceof IOException) {
                                                        Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else {
                                                        Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                    }
                                                    Toast.makeText(Reservation_Activity.this, "Error from call 3", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<List<Reservation>>call, Throwable t) {
                                            if (t instanceof IOException) {
                                                Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                            }
                                            Toast.makeText(Reservation_Activity.this, "Error from call 2", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Reservation>>call, Throwable t) {
                            Toast.makeText(Reservation_Activity.this, "Error from call 1", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(takeaway.isChecked()) {
                    reservation_type=2;
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                    date= df.format(Calendar.getInstance().getTime());
                    DateFormat dff = new SimpleDateFormat("HH:MM");
                    time=dff.format(Calendar.getInstance().getTime());
                    Call<List<Reservation>> call2 = resturantApiInterface.getTable(id_resturant, 5, 6);
                    call2.enqueue(new Callback<List<Reservation>>() {
                        @Override
                        public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                            List<Reservation> aa = response.body();
                                num_table = aa.get(0).getNum_table();
                            id_resturant_table = aa.get(0).getId_resturant_table();
                            Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,2,1,7);
                            call5.enqueue(new Callback<Reservation>() {
                                @Override
                                public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                    Reservation res = response.body();
                                    Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);
                                    call6.enqueue(new Callback<List<Reservation>>() {
                                        @Override
                                       public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                                            List<Reservation> rr = response.body();
                                            final int id_reservation = rr.get(0).getId_reservation();
                                            Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, date, 1,Integer.toString(0),Integer.toString(0), 11);
                                            call7.enqueue(new Callback<Reservation>() {
                                                @Override
                                                public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                    Reservation resss = response.body();
                                                    Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                    intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                    intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                    intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                    intent.putExtra("id_account",Integer.toString(id_account));
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onFailure(Call<Reservation> call, Throwable t) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<List<Reservation>> call, Throwable t) {

                                        }
                                    });

                                }
                                @Override
                                public void onFailure(Call<Reservation> call, Throwable t) {
                                    if (t instanceof IOException) {
                                        Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<List<Reservation>> call, Throwable t) {

                        }
                    });


                }
                else if(onTheRestaurant.isChecked()){
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                    date= df.format(Calendar.getInstance().getTime());
                    DateFormat dff = new SimpleDateFormat("HH:MM");
                    time=dff.format(Calendar.getInstance().getTime());
                    reservation_type=3;
                    Call<List<Reservation>> call1 = resturantApiInterface.getTablesType(id_resturant,5);
                    call1.enqueue(new Callback <List<Reservation>>(){
                        @Override
                        public void onResponse(Call <List<Reservation>> call, Response<List<Reservation>>response) {
                            getNumberOfPeople= NumberOfPeople.getText().toString();
                            List<Reservation>reservationList = response.body();
                            for(int i=0;i<reservationList.size();i++) {
                                if (reservationList.get(i).getType_table()>=Integer.parseInt(getNumberOfPeople) )
                                {   id_table=reservationList.get(i).getId_table();
                                    Call<List<Reservation>> call2 = resturantApiInterface.getTable(id_resturant,id_table,6);
                                    call2.enqueue(new Callback <List<Reservation>>(){
                                        @Override
                                        public void onResponse(Call <List<Reservation>> call, Response<List<Reservation>>response) {
                                            List<Reservation> aa = response.body();
                                            if(Integer.toString(aa.get(0).getNum_table())==null){
                                                num_table=0;
                                            }
                                            else{num_table= aa.get(0).getNum_table();}
                                            id_resturant_table=aa.get(0).getId_resturant_table();
                                            Call<List<Reservation>> call3 = resturantApiInterface.getReservesdTables(id_resturant_table,8);
                                            call3.enqueue(new Callback<List<Reservation>>() {
                                                @Override
                                                public void onResponse(Call<List<Reservation>>call, Response<List<Reservation>> response) {
                                                    List<Reservation> body = response.body();
                                                    if(body.get(0).getCount()<num_table){
                                                        if (response.isSuccessful()){
                                                            Call<List<Reservation>> call4 = resturantApiInterface.getNumberOfVisites(id_account,9);
                                                            call4.enqueue(new Callback <List<Reservation>>(){
                                                                @Override
                                                                public void onResponse(Call <List<Reservation>> call, Response<List<Reservation>>response) {
                                                                    List<Reservation>numberofvisit = response.body();
                                                                    num=numberofvisit.get(0).getNumOfVisites();
                                                                    if ( num <4) {
                                                                        num++;
                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,3,1,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);
                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, date, num,Integer.toString(0),Integer.toString(0), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                    else if(num<8){
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,3,2,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);

                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, date, num,Integer.toString(0),Integer.toString(0), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                    else if(num<12){
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,3,3,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);

                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, date, num,Integer.toString(0),Integer.toString(0), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                    else {
                                                                        num++;

                                                                        Call<Reservation> call5 = resturantApiInterface.postReservation(id_account,3,4,7);
                                                                        call5.enqueue(new Callback<Reservation>() {
                                                                            @Override
                                                                            public void onResponse(Call<Reservation>call, Response<Reservation> response) {
                                                                                Reservation res = response.body();
                                                                                Call<List<Reservation>> call6 = resturantApiInterface.getIdReservation(id_account,10);

                                                                                call6.enqueue(new Callback <List<Reservation>>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                                                        List<Reservation> rr = response.body();
                                                                                        final int id_reservation = rr.get(0).getId_reservation();
                                                                                        Call<Reservation> call7 = resturantApiInterface.postReservation_table(id_reservation, id_resturant_table, time, 1, date, num,Integer.toString(0),Integer.toString(0), 11);
                                                                                        call7.enqueue(new Callback<Reservation>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                                                                Reservation resss = response.body();
                                                                                                Toast.makeText(Reservation_Activity.this, "No Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                                Intent intent = new Intent(getApplicationContext(),meal_typeActivity.class);
                                                                                                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                                                                                                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                                                                                                intent.putExtra("id_account",Integer.toString(id_account));
                                                                                                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                                                                                                startActivity(intent);
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                                if (t instanceof IOException) {
                                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 7", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                                                        if (t instanceof IOException) {
                                                                                            Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else {
                                                                                            Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        Toast.makeText(Reservation_Activity.this, "Error from call 6", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } ); }
                                                                            @Override
                                                                            public void onFailure(Call<Reservation> call, Throwable t) {
                                                                                if (t instanceof IOException) {
                                                                                    Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                Toast.makeText(Reservation_Activity.this, "Error from call 5", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                                @Override
                                                                public void onFailure(Call<List<Reservation>>call, Throwable t) {
                                                                    if (t instanceof IOException) {
                                                                        Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else {
                                                                        Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    Toast.makeText(Reservation_Activity.this, "Error from call 4", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                    if (t instanceof IOException) {
                                                        Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else {
                                                        Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                                    }
                                                    Toast.makeText(Reservation_Activity.this, "Error from call 3", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<List<Reservation>>call, Throwable t) {
                                            if (t instanceof IOException) {
                                                Toast.makeText(Reservation_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(Reservation_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                                            }
                                            Toast.makeText(Reservation_Activity.this, "Error from call 2", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Reservation>>call, Throwable t) {
                            Toast.makeText(Reservation_Activity.this, "Error from call 1", Toast.LENGTH_SHORT).show();
                        }
                    });
                } } }
                    );

                }
    }

