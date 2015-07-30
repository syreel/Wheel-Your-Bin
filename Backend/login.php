<?php

if(isset($_GET['username']) && isset($_GET['password'])){

$username = $_GET['username'];
$password = $_GET['password'];

require 'db.php';

$SQL = $odb -> prepare("SELECT * from binusers where username = :username");
$SQL -> execute(array(':username' => $username));
$row = $SQL->fetch();

$resp = array();

if($password == $row['password']){
	$resp['status'] = true;
	$resp['sessionToken'] = "abcd";
}else{
	$resp['status'] = false;
}

echo json_encode($resp);

}else{
	echo 'none specified';
}

?>