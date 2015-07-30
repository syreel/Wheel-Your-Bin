<?php

$resp = array();

if(isset($_GET['username']) && isset($_GET['value'])){
	
	$username = $_GET['username'];
	$distance = $_GET['value'];

	require 'db.php';
	
	$SQL = $odb -> prepare("SELECT * FROM binlocations WHERE username = :username");
	$SQL -> execute(array(':username' => $username));
	$location = $SQL->fetch();
	
	$SQL1 = $odb -> prepare("DELETE FROM binlocations WHERE username = :username");
	$SQL1 -> execute(array(':username' => $username));
	
	$SQL2 = $odb -> prepare("INSERT INTO binlocations VALUES (:username, :distance)");
	$SQL2 -> execute(array(':username' => $username, ':distance' => $distance));
	
	$SQL3 = $odb -> prepare("SELECT * FROM binusers where username = :username");
	$SQL3 -> execute(array(':username' => $username));
	$user = $SQL3->fetch();
	
	$send = false;
	
	if($distance > 200 && $location['distance'] < 200){
		$send = true;
		$message = 'Your bin has been removed!';
	}
	
	if($distance < 200 && $location['distance'] > 200){
		$send = true;
		$message = 'Your bin has been returned!';
	}
	
	if($send){
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
	}
	
}else{
	$resp['status'] = false;
}

echo json_encode($resp);

?>
