<?php

# These aren't the real details of the sql server, for security reasons. This is only an example file.

define('DB_HOST', '192.168.1.1');
define('DB_NAME', 'admin');
define('DB_USERNAME', 'admin');
define('DB_PASSWORD', 'password');

$odb = new PDO('mysql:host=' . DB_HOST . ';dbname=' . DB_NAME, DB_USERNAME, DB_PASSWORD);

?>