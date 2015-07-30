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
	
	$token = md5(microtime().rand());
	
	$resp['status'] = true;
	$resp['sessionToken'] = $token;
	
	$SQL1 = $odb -> prepare("DELETE FROM binsessions where username = :username");
	$SQL1 -> execute(array(':username' => $username));
	
	$SQL2 = $odb -> prepare("INSERT INTO binsessions values (:username, :token)");
	$SQL2 -> execute(array(':username' => $username, ':token' => $token));
	
}else{
	$resp['status'] = false;
}

echo json_encode($resp);

}else{
	echo 'none specified';
}

?>