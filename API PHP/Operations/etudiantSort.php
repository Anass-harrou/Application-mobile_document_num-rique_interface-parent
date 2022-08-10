<?php
$username = "root";
$server = "localhost";
$password = "";
$db_name = "gestioncollegue2";

$connection = new mysqli($server, $username, $password, $db_name);

if($connection->connect_error){
echo "The error happened " . $connection->connect_error;
}

$query = "SELECT id_etudiant,nom_etudiant,niveau,classe,note,presence FROM etudiant LIMIT 1";
$statement = $connection->prepare($query);
$statement->execute();

$users_array = array();

$statement->bind_result($id,$nm,$nv,$cls,$nte,$pr);

while($statement->fetch()){
    $temp = array();
    $temp['#'] = $id;
    $temp['nom'] = $nm;
    $temp['niveau'] = $nv;
    $temp['classe'] = $cls;
    $temp['note'] = $nte;
    $temp['presence'] = $pr;
    array_push($users_array,$temp);
}

echo json_encode($users_array);


$connection->close();