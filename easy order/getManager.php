<?php
$host="localhost";
$user="root";
$password="root";
$con = mysqli_connect($host,$user,$password,"res_db");
$id_query=$_GET['id_query'];
if($con){
if($id_query==3){
   $cooker_name=$_POST['cooker_name'];
   $sql="SELECT id_account from res_db.account where Full_name='$cooker_name';";
   $response=array();
$result = mysqli_query($con,$query);
while($row=mysqli_fetch_array($result))
{
array_push($response,array('id_account'=>$row['id_account']));} 
}	
}
echo json_encode($response);
?>