<?php

$resp = array();

if(isset($_GET['username']) && isset($_GET['value']) && isset($_GET['type'])){
	
	$username = $_GET['username'];
	$distance = $_GET['value'];
	$type = $_GET['type'];

	require 'db.php';
	
	$SQL = $odb -> prepare("SELECT * FROM binlocations WHERE username = :username AND type = :type;");
	$SQL -> execute(array(':username' => $username, ':type' => $type));
	$location = $SQL->fetch();
	
	$SQL1 = $odb -> prepare("DELETE FROM binlocations WHERE username = :username AND type = :type;");
	$SQL1 -> execute(array(':username' => $username, ':type' => $type));
	
	$SQL2 = $odb -> prepare("INSERT INTO binlocations VALUES (:username, :distance, :type);");
	$SQL2 -> execute(array(':username' => $username, ':distance' => $distance, ':type' => $type));
	
	$SQL3 = $odb -> prepare("SELECT * FROM binusers where username = :username;");
	$SQL3 -> execute(array(':username' => $username));
	$user = $SQL3->fetch();
	
	$send = false;
	
	if($distance > 200 && $location['distance'] < 200){
		$send = true;
		$message = 'Your ' . $type . ' bin has been removed!';
	}
	
	if($distance < 200 && $location['distance'] > 200){
		$send = true;
		$message = 'Your ' . $type . ' bin has been returned!';
	}
	
	if($send){
		
		$SQL3 = $odb -> prepare("INSERT INTO binlogs VALUES (:username, :message);");
		$SQL3 -> execute(array(':username' => $username, ':message' => $message"));
		
		
	/*
		try
		 {
			// Create a Clockwork object using your API key (hidden from github)
			require 'clockwork.php';
			
			// Setup and send a message
			$message = array( 'to' => $user['number'], 'message' => $message );
			$result = $clockwork->send( $message );
		 
			// Check if the send was successful
			if($result['success']) {
				$resp['status'] = true;
			} else {
				$resp['status'] = false;
			}
		}
		catch (ClockworkException $e)
		{
			echo 'Exception sending SMS: ' . $e->getMessage();
		}
		*/
	}
	
}else{
	$resp['status'] = false;
}

echo json_encode($resp);

?>
