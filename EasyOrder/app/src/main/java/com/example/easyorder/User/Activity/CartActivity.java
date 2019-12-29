package com.example.easyorder.User.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyorder.User.Adapter.CartAdapter;
import com.example.easyorder.DataBase.CartDataBase;
import com.example.easyorder.DataBase.CartModel;
import com.example.easyorder.classes.Menu;
import com.example.easyorder.classes.Order;
import com.example.easyorder.R;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter cartAdapter;
    private TextView totalprice;
    private ResturantApiInterface resturantApiInterface;
    private Button submite;
    private int id_reservation,id_res_table,id_order,type;
    List<Menu> menus;
    CartDataBase orderDataBase;
    private List<CartModel> carts;
    private int id_account;
    private String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.Cart_rec);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        totalprice=findViewById(R.id.total);
        submite=findViewById(R.id.btnPlaceOrder);
        id_reservation=Integer.parseInt(getIntent().getStringExtra("id_reservation"));
        id_account=Integer.parseInt(getIntent().getStringExtra("id_account"));
        type=Integer.parseInt(getIntent().getStringExtra("reservation_type"));
        user_name=getIntent().getStringExtra("user_name");
        carts= new ArrayList<CartModel>();
        orderDataBase=new CartDataBase(getApplicationContext());
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        submite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Order>> call = resturantApiInterface.getResturantReservationTable(id_reservation,20);

                call.enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                       List<Order> res = response.body();
                       id_res_table=res.get(0).getId_res_table();
                        Call<Order> cal2 = resturantApiInterface.postOrder
                                (id_res_table,17);
                        cal2.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                Order order=response.body();
                                Call<List<Order>>cal3=resturantApiInterface.getOrderId(id_res_table,19);
                                cal3.enqueue(new Callback<List<Order>>() {
                                    @Override
                                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                                        List<Order>orders=response.body();
                                        id_order=orders.get(0).getId_ordar();

                                        for (int i=0;i<carts.size();i++) {
                                            Call <Order> cal4=resturantApiInterface.postFoodOrder(id_order,carts.get(i).getID_RES_FOOD(),carts.get(i).getQuantity(),18);
                                            cal4.enqueue(new Callback<Order>() {
                                                @Override
                                                public void onResponse(Call<Order> call, Response<Order> response) {
                                                    Order o=response.body();
                                                    Log.d("addddd",carts.size()+"");
                                                    if(type==4){
                                                    final Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
                                                    intent.putExtra("id_account",Integer.toString(id_account));
                                                    intent.putExtra("user_name",user_name);
                                                               startActivity(intent);}

                                                }

                                                @Override
                                                public void onFailure(Call<Order> call, Throwable t) {

                                                }
                                            });
                                        }

                                        Toast.makeText(CartActivity.this, "Order is done", Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onFailure(Call<List<Order>> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {

                            }
                        });
                    }



                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {

                    }
                });
            }  });
        fetchInformation();
    }

    private void fetchInformation(){
      /* resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        Call<List<Menu>> call = resturantApiInterface.getOrderDetail(id_order,19);
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                menus=response.body();
            }
            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
            }
        });*/
       carts.clear();
       ArrayList<CartModel> ac=orderDataBase.GetCart();

       carts.addAll(ac);
        for(CartModel c:ac) {
            Log.d("Afterrrrrrrrrrrrrrrr", ac.size() + "");
            Log.d("Afterrrrrrrrrrrrrrrr",c.getID()+"");
            Log.d("Afterrrrrrrrrrrrrrrr", c.getMeal_id() + "");
            Log.d("Afterrrrrrrrrrrrrrrr", c.getPrice() + "");
            Log.d("Afterrrrrrrrrrrrrrrr", c.getQuantity() + "");
        }
        cartAdapter = new CartAdapter(getApplicationContext(),carts);

        recyclerView.setAdapter(cartAdapter);
        enableSwipe();
        int total=0;
        for (CartModel Sqliteorder :carts){
total+=Sqliteorder.getQuantity()*Sqliteorder.getPrice();
totalprice.setText(Integer.toString(total));
        }
    }
    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT){
                    final CartModel deletedModel = carts.get(position);
                    final int deletedPosition = position;
                    Log.d("delete", carts.get(position).getMeal_id() + "");
                    Log.d("delete", carts.get(position).getMeal_name() + "");
                    Log.d("delete", carts.get(position).getPrice() + "");
                    Log.d("Before delete the id is",carts.get(position).getID()+"");
                    orderDataBase.removeFromCart(carts.get(position).getID());
                    cartAdapter.removeItem(position);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), " removed from Recyclerview!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cartAdapter.restoreItem(deletedModel, deletedPosition);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                    int total=0;
                    if(carts.size()==0){
                        total=0;
                        totalprice.setText(Integer.toString(total));}
                    else{
                    for (CartModel Sqliteorder :carts) {
                        total += Sqliteorder.getQuantity() * Sqliteorder.getPrice();
                        totalprice.setText(Integer.toString(total));
                    }
                    }
                } else {
                    final CartModel deletedModel = carts.get(position);
                    final int deletedPosition = position;
                    cartAdapter.removeItem(position);
                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), " removed from Recyclerview!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            cartAdapter.restoreItem(deletedModel, deletedPosition);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                    int total=0;
                    if(carts.size()==0){
                        total=0;
                        totalprice.setText(Integer.toString(total));
                    }
                    else{
                        for (CartModel Sqliteorder :carts) {
                            total += Sqliteorder.getQuantity() * Sqliteorder.getPrice();
                            totalprice.setText(Integer.toString(total));
                        }
                    }

                }
            }

            /*@Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                     Paint p=new Paint();
                    if(dX > 0){
                        p.setColor(Color.RED);
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_black_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.BLACK);
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_black_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }*/
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /*private ArrayList<CartModel> populateList(){

        ArrayList<CartModel> list = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            CartModel imageModel = new CartModel(carts.get(i).getMeal_id(),carts.get(i).getMeal_name(),carts.get(i).getPrice(),carts.get(i).getQuantity());

            list.add(imageModel);
        }

        return list;
    }*/
}

