<?php

if(isset($_GET['username']) && isset($_GET['token'])){

$username = $_GET['username'];
$token = $_GET['token'];

require 'db.php';

$SQL = $odb -> prepare("SELECT * from binsessions where username = :username");
$SQL -> execute(array(':username' => $username));
$row = $SQL->fetch();

$resp = array();

if($token == $row['token']){
	$resp['status'] = true;
}else{
	$resp['status'] = false;
}

echo json_encode($resp);

}else{
	echo 'none specified';
}

?>