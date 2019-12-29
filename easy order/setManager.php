<?php
$host="localhost";
$user="root";
$password="root";
$con = mysqli_connect($host,$user,$password,"res_db");
$id_query=$_POST['id_query'];
if($con){
if($id_query==0){

  $id_account=$_POST['id_account'];
  $manager_cooker_name=$_POST['Full_name'];
  $sql="update res_db.account set account.Full_name='$manager_cooker_name' 
   where account.id_account='$id_account';";
 
}
else if($id_query==1){
	 $id_resturant=$_POST['id_resturant'];
    $resturant_name=$_POST['resturant_name'];
    $resturant_address= $_POST['resturant_address'];
   $sql="update  res_db.resturant set resturant.resturant_name='$resturant_name', 
    resturant.resturant_address='$resturant_address' 
   where resturant.id_resturant='$id_resturant';";
}
else if($id_query==2){
    $meal_type=$_POST['meal_type'];
   $sql="INSERT INTO `res_db`.`meal_type` (`meal_type`) VALUES ('$meal_type');";
}

if(mysqli_query($con,$sql)){
  echo json_encode(array('response'=>"order is add to database"));}
}
?>