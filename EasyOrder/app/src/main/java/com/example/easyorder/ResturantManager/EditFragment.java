package com.example.easyorder.ResturantManager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.easyorder.R;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.classes.account;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFragment extends Fragment {

    private int id_cook;
    private View rootView;
    private ResturantApiInterface resturantApiInterface;
    private FloatingActionButton floatingActionButton;
    private EditText resname, resaddress, resmanager, rescooker;

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\

        rootView = inflater.inflate(R.layout.fragment_edit__resturant__info_, container, false);
        getAndSetUis();
        resname.setText(Manager_Home_Activity.resturant.getResturant_name());
        resaddress.setText(Manager_Home_Activity.resturant.getResturant_address());
        resmanager.setText(Manager_Home_Activity.resturant.getManager_name());
        rescooker.setText(Manager_Home_Activity.resturant.getCooker_name());
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Call<Resturant> call = resturantApiInterface.updateResturantInfo(Manager_Home_Activity.resturant.getId_resturant()
                            , resname.getText().toString(), resaddress.getText().toString(), 1);
                    call.enqueue(new Callback<Resturant>() {
                        @Override
                        public void onResponse(Call<Resturant> call, Response<Resturant> response) {
                            if (response.code() == 200) {
                                Resturant resturant = response.body();
                                Toast.makeText(getActivity(), "تم التعديل بنجاح" + resname.getText() + "  " +
                                        resaddress.getText() + "   ", Toast.LENGTH_SHORT).show();

                            }


                            Call<account> call1 = resturantApiInterface.RenameManagerCooker(Manager_Home_Activity.id_account, resmanager.getText().toString(), 0);
                            call1.enqueue(new Callback<account>() {
                                @Override
                                public void onResponse(Call<account> call, Response<account> response) {
                                    account account = response.body();
                                    Toast.makeText(getActivity(), "تم التعديل بنجاح " + resmanager.getText(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<account> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Error in call 1", Toast.LENGTH_SHORT).show();
                                }
                            });


//                            Call<account> call2 = resturantApiInterface.getcooker_id(rescooker.getText().toString(), 3);
//                            call2.enqueue(new Callback<account>() {
//                                @Override
//                                public void onResponse(Call<account> call, Response<account> response) {
//                                    account account = response.body();
//                                    id_cook = account.getId_account();
//                                    Toast.makeText(getActivity(), "تم التعديل بنجاح" + id_cook, Toast.LENGTH_SHORT).show();
//
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<account> call, Throwable t) {
//                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
                         id_cook=Manager_Home_Activity.id_Cooker;

                         Call<account> call2= resturantApiInterface.RenameManagerCooker(id_cook,rescooker.getText().toString(),0);

                         call2.enqueue(new Callback<account>() {
                             @Override
                             public void onResponse(Call<account> call, Response<account> response) {
                                 account account1=response.body();
                                 Toast.makeText(getActivity(), "تم التعديل بنجاح " + rescooker.getText(), Toast.LENGTH_SHORT).show();


                             }

                             @Override
                             public void onFailure(Call<account> call, Throwable t) {
                                 Toast.makeText(getActivity(), "Error in call2", Toast.LENGTH_SHORT).show();

                             }
                         });


                        }

                        @Override
                        public void onFailure(Call<Resturant> call, Throwable t) {
                            Toast.makeText(getActivity(), "Error in call", Toast.LENGTH_SHORT).show();
                        }
                    });
                }




        });
        return rootView;

    }


    private void getAndSetUis() {
        resname = rootView.findViewById(R.id.edit_res_name_edit_text);
        resaddress = rootView.findViewById(R.id.edit_res_address_edit_text);
        resmanager = rootView.findViewById(R.id.edit_res_manager_edit_text);
        rescooker = rootView.findViewById(R.id.edit_res_cooker_edit_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        floatingActionButton = rootView.findViewById(R.id.edit_resturant_info_floating_action_button);


    }

}