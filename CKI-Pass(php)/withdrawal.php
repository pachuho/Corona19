<?php
    error_reporting(E_ALL);
    ini_set("display_errors", 1);
    $con = mysqli_connect("aa9334.cafe24.com", "root", "rus7wp850!@vkt", "DB", "8509");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];

    $statement = mysqli_prepare($con, "DELETE FROM member WHERE id = ?");
    mysqli_stmt_bind_param($statement, "s",$id);
    mysqli_stmt_execute($statement);
    // $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
