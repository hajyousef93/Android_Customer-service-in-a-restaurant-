<?php
$host="localhost";
$user="root";
$password="root";
$con = mysqli_connect($host,$user,$password,"res_db");
$re=$_POST['re'];
if($re==2){
$id_acc_type=$_POST['id_acc_type'];
$Full_name=$_POST['Full_name'];
$user_name=$_POST['user_name'];
$password= $_POST['password'];
$phonenumber=$_POST['phonenumber'];
$sql="insert into account(id_acc_type,Full_name,user_name,password,phonenumber) values
('$id_acc_type','$Full_name','$user_name','$password','$phonenumber');";
} 
else if($re==7){
$id_customer=$_POST['id_customer'];
$id_reservation_type=$_POST['id_reservation_type'];
$id_loyalty=$_POST['id_loyalty'];
$sql="INSERT INTO res_db.reservation(id_customer,id_reservation_type,id_loyalty) VALUES ('$id_customer', '$id_reservation_type', '$id_loyalty');";
}
else if($re==11){
$id_reservation= $_POST['id_reservation'];
$id_resturant_table=$_POST['id_resturant_table'];
$timeOfReservation=$_POST['timeOfReservation'];
$isBusy=$_POST['isBusy'];
$reservation_date=$_POST['reservation_date'];
$numOfVisits=$_POST['numOfVisits'];
$hours=$_POST['hours'];
$min=$_POST['min'];
$sql="INSERT INTO res_db.reservation_table(id_reservation,id_resturant_table,timeOfReservation,isBusy,reservation_date,numOfVisits,hours,min) VALUES 
('$id_reservation','$id_resturant_table','$timeOfReservation','$isBusy','$reservation_date','$numOfVisits','$hours','$min');";
}
else if($re==17){
$id_res_table= $_POST['id_res_table'];
$sql="insert into res_db.order (id_res_table) values('$id_res_table');";
}
else if($re==18){
$id_order= $_POST['id_order'];
$id_resturant_food= $_POST['id_resturant_food'];
$quantity=$_POST['quantity'];
$sql="insert into res_db.order_resturant_food (id_order,id_resturant_food,quantity)values('$id_order','$id_resturant_food','$quantity');";
}
else if($re==22){
$id_order_resturant_food= $_POST['id_order_resturant_food'];
$sql="Delete from res_db.order_resturant_food where id_order_resturant_food='$id_order_resturant_food';";
}
else if($re==24){
$resturant_name= $_POST['resturant_name'];
$resturant_address= $_POST['resturant_address'];
$id_manger=$_POST['id_manger'];
$sql="insert into res_db.resturant (resturant_name,resturant_address,id_manger)values('$resturant_name','$resturant_address','$id_manger');";
}
if(mysqli_query($con,$sql)){
  echo json_encode(array('response'=>"order is add to database"));}
?>



