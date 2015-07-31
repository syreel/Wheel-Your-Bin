<?php

$resp = array();

if(isset($_GET['username']) && isset($_GET['password']) && isset($_GET['confirmPassword'])){
	
	$username = $_GET['username'];
	$password = $_GET['password'];
	$confirmPassword = $_GET['confirmPassword'];
	
	if($password == $confirmPassword){
		
		require 'db.php';
		
		$SQL = $odb -> prepare("SELECT COUNT(*) AS NumberOfUsers FROM binusers WHERE username = :username");
		$result = $SQL -> execute(array(':username' => $username));
		
		if($count['NumberOfUsers'] > 0){
			$resp['status'] = 0;
			$resp['message'] = 'This username is already registered!';
		}else{
			$resp['status'] = 1;
			$resp['message'] = 'Welcome';
		}
				
	}else{
		$resp['status'] = 0;
		$resp['message'] = 'The passwords need to match!';
	}
	
}else{
	$resp['status'] = 0;
	$resp['message'] = 'Not enough parameters specified!';
}

echo json_encode($resp);

?>