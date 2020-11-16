<?php 
    $con = mysqli_connect("aa9334.cafe24.com", "root", "rus7wp850!@vkt", "DB", "8509");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];
    $time = $_POST["time"];
    $la = $_POST["la"];
    $lo = $_POST["lo"];
    $store = $_POST["store"];

    $statement = mysqli_prepare($con, "INSERT INTO location VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssdds", $id, $time, $la, $lo, $store);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;
   
    echo json_encode($response);
?>
