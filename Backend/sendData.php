<?php

$resp = array();

if(isset($_GET['username']) && isset($_GET['value'])){
	
	$username = $_GET['username'];
	$distance = $_GET['value'];

	require 'db.php';

	$SQL = $odb -> prepare("UPDATE binlocations SET distance = :distance WHERE username = :username");
	$SQL -> execute(array(':username' => $username, ':distance' => $distance));
	
	$resp['status'] = true;
}else{
	$resp['status'] = false;
}

echo json_encode($resp);

?>
