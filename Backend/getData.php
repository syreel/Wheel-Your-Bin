<?php

$username = $_GET['username'];

require 'db.php';

$SQL = $odb -> prepare("SELECT FROM binlocations WHERE username = :username");
$SQL -> execute(array(':username' => $username));
$row = $SQL->fetch();

$distances = array();

$distances['status'] = true;
$distances['distance'] = $row['distance'];

/*
if(isset($_GET['token'])){

$token = $_GET['token'];

$alerts = array();

$alert = array();
$alert['name'] = "Blue Bin";
$alert['value'] = "Fifteen minutes";
array_push($alerts, $alert);

$alert1 = array();
$alert1['name'] = "Green Bin";
$alert1['value'] = "One hour, twenty minutes";
array_push($alerts, $alert1);

$container['alerts'] = $alerts;
}
*/

$container['distances'] = $distances;

echo json_encode($container);

?>
