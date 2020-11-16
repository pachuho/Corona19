<?php
    error_reporting(E_ALL);
    ini_set("display_errors", 1);

    $con = mysqli_connect("aa9334.cafe24.com", "root", "rus7wp850!@vkt", "DB", "8509");

    if (!$con) {
        $error = mysqli_connect_error();
        $errno = mysqli_connect_errno();
        print "$errno: $error\n";
        exit();
    }
    mysqli_query($con,'SET NAMES utf8');

    $name = $_POST["name"];
    $id = $_POST["id"];
    $pwd = $_POST["pwd"];
    $address = $_POST["address"];
    $age = $_POST["age"];
    $sex = $_POST["sex"];
    $manager = $_POST["manager"];
    $corona = $_POST["corona"];

    $statement = mysqli_prepare($con, "INSERT INTO member VALUES (?,?,?,?,?,?,?,?)");
    $bind = mysqli_stmt_bind_param($statement, "ssssisss", $name, $id, $pwd, $address, $age, $sex, $manager, $corona);
    if($bind === false) {
        echo('파라미터 바인드 실패 : ' . mysqli_error($con));
        exit();
    }
    $exec = mysqli_stmt_execute($statement);


    $response = array("success"=>"true");
 
   
    echo json_encode($response);
?>
