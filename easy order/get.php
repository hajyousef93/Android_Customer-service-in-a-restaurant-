<?php
$host="localhost";
$user="root";
$password="root";
$con = mysqli_connect($host,$user,$password,"res_db");
$re=$_GET['re'];
if($con){
if($re==0){
$query = "SELECT id_resturant,resturant_name,resturant_address,resturant_image,manger.Full_name as manger_Full_name,cooker.Full_name as cooker_Full_name
FROM resturant,account manger,account cooker
where resturant.id_cooker=cooker.id_account
and resturant.id_manger=manger.id_account;";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_resturant'=>$row['id_resturant'],'resturant_name'=>$row['resturant_name'],
'resturant_address'=>$row['resturant_address'],'resturant_image'=>$row['resturant_image'],
'manger_name'=>$row['manger_Full_name'],
'cooker_name'=>$row['cooker_Full_name']));}
}   
else if($re==1){
$user_name=$_GET['user_name'];
$password=$_GET['password'];
$query = "SELECT id_account,id_acc_type FROM res_db.account 
where user_name='$user_name' and password='$password';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_account'=>$row['id_account'],'id_acc_type'=>$row['id_acc_type']));} 
}
else if($re==3){
$query="SELECT id_meal, meal_type  FROM res_db.meal_type;";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_meal'=>$row['id_meal'],'meal_type'=>$row['meal_type']));}
}
else if($re==5){
$id_resturant=$_GET['id_resturant'];
$query = "SELECT `table`.id_table ,`table`.type_table FROM res_db.resturant_table,res_db.`table`
where resturant_table.id_table=`table`.id_table and resturant_table.id_resturant='$id_resturant';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_table'=>$row['id_table'],'type_table'=>$row['type_table']));} 
}
else if($re==6){
$id_resturant=$_GET['id_resturant'];
$id_table=$_GET['id_table'];
$query = "SELECT resturant_table.num_table,resturant_table.id_resturant_table FROM res_db.resturant_table 
where resturant_table.id_table='$id_table' and resturant_table.id_resturant='$id_resturant';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('num_table'=>$row['num_table'],'id_resturant_table'=>$row['id_resturant_table']));} 
}
else if($re==8){
$id_resturant_table=$_GET['id_resturant_table'];
$query = "SELECT count(reservation_table.id_resturant_table) as count FROM res_db.reservation_table
 where reservation_table.id_resturant_table='$id_resturant_table' and reservation_table.isBusy=1";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('count'=>$row['count']));} 
}
else if($re==9){
$id_customer=$_GET['id_customer'];
$query = "SELECT count(res_db.reservation.id_reservation) as numOfVisites FROM res_db.reservation
 where reservation.id_customer='$id_customer';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('numOfVisites '=>$row['numOfVisites']));} 
}
else if($re==10){
$id_customer=$_GET['id_customer'];
$query = "select max(reservation.id_reservation) as id_reservation from res_db.reservation where reservation.id_customer='$id_customer'and reservation.id_reservation_type=(1 or 3);";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_reservation'=>$row['id_reservation']));} 
}
else if($re==12){
$id_resturant=$_GET['id_resturant'];
$id_meal=$_GET['id_meal'];
$query="SELECT resturant_food.id_res_food,resturant_food.id_food,food.name_food,resturant_food.price,resturant_food.Image,resturant_food.offer,resturant_food.price_offer
 from food,resturant_food
 where resturant_food.id_resturant='$id_resturant'
 and food.id_food=resturant_food.id_food
 and food.id_meal='$id_meal';";
 $response=array();
 $result = mysqli_query($con,$query);
 while($row=mysqli_fetch_array($result)){
 array_push($response,array('id_res_food'=>$row['id_res_food'],'id_food'=>$row['id_food'],'name_food'=>$row['name_food'],'price'=>$row['price'],
 'Image'=>$row['Image'],'offer'=>$row['offer'],'price_offer'=>$row['price_offer']));
}
} 
else if($re==13){
        $query="SELECT resturant_food.id_food,food.name_food,
        resturant_food.price,resturant_food.Image,resturant_food.offer
        from food,resturant_food
        where food.id_food=resturant_food.id_food;";
        $response=array();
        $result = mysqli_query($con,$query);
        while($row=mysqli_fetch_array($result)){
        array_push($response,array('id_food'=>$row['id_food'],'name_food'=>$row['name_food'],'price'=>$row['price'],
         'Image'=>$row['Image']));
    }
    }
else if($re==14){
$id_resturant=$_GET['id_resturant'];
$id_meal=$_GET['id_meal'];
$query = "SELECT resturant_food.id_food,food.name_food,resturant_food.price FROM res_db.food,res_db.resturant_food 
where food.id_food=resturant_food.id_food and resturant_food.id_resturant='$id_resturant' and food.id_meal='$id_meal';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_food'=>$row['id_food'],'name_food'=>$row['name_food'],'price'=>$row['price']));} 
}
else if ($re==15){
$id_manager=$_GET['id_manager'];
$query = "SELECT id_resturant,resturant_name,resturant_address,resturant_image,id_cooker,manger.Full_name as manger_Full_name,cooker.Full_name as cooker_Full_name
FROM resturant,account manger,account cooker
where resturant.id_cooker=cooker.id_account
and resturant.id_manger='$id_manager'
and resturant.id_manger=manger.id_account;"; 
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_resturant'=>$row['id_resturant'],'resturant_name'=>$row['resturant_name'],
'resturant_address'=>$row['resturant_address'],'resturant_image'=>$row['resturant_image'],
'id_cooker'=>$row['id_cooker'],
'manger_name'=>$row['manger_Full_name'],
'cooker_name'=>$row['cooker_Full_name']));} 
}
else if ($re==16){
$id_food=$_GET['id_food'];
$query ="SELECT name_ingredient FROM res_db.food_ingredient,res_db.resturant_food,res_db.ingredient
WHERE resturant_food.id_food=food_ingredient.id_food
and resturant_food.id_food='$id_food'
AND food_ingredient.idl_ingredient=ingredient.id_ingredient;"; 
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('name_ingredient'=>$row['name_ingredient']));} 
}
else if($re==19){
$id_res_table=$_GET['id_res_table'];
$query = "select max(id_order) as id_order from res_db.order where id_res_table='$id_res_table';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_order'=>$row['id_order']));} 
}
else if($re==20){
$id_reservation=$_GET['id_reservation'];
$query = "select max(id_res_table) as id_res_table from res_db.reservation_table where id_reservation='$id_reservation';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_res_table'=>$row['id_res_table']));} 
}
else if($re==21){
$id_cooker=$_GET['id_cooker'];
$query = "select order_resturant_food.id_order_resturant_food,
order_resturant_food.id_order, 
order_resturant_food.quantity,
food.name_food
from res_db.order_resturant_food ,res_db.resturant_food,res_db.resturant,res_db.food
 where order_resturant_food.id_resturant_food=resturant_food.id_res_food 
 and resturant_food.id_resturant=resturant.id_resturant
 and resturant_food.id_food=food.id_food
and resturant.id_cooker='$id_cooker';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('id_order_resturant_food'=>$row['id_order_resturant_food'],'id_order'=>$row['id_order'],'quantity'=>$row['quantity'],'name_food'=>$row['name_food']));} 
}
else if($re==23){
$id_customer=$_GET['id_customer'];
$query = "select reservation_table.reservation_date,reservation_table.timeOfReservation,reservation_table.id_reservation,resturant_table.id_resturant,reservation_table.hours,reservation_table.min
from res_db.reservation,res_db.reservation_table,res_db.resturant_table
where reservation_table.id_reservation=reservation.id_reservation and
reservation_table.id_resturant_table=resturant_table.id_resturant_table
and reservation.id_reservation_type=1 and reservation.id_customer='$id_customer';";
$response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result)){
array_push($response,array('reservation_date'=>$row['reservation_date'],'timeOfReservation'=>$row['timeOfReservation'],'id_reservation'=>$row['id_reservation'],'id_resturant'=>$row['id_resturant'],'hours'=>$row['hours'],'min'=>$row['min']));} 
}
}
echo json_encode($response); 
?>