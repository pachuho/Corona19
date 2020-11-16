<?php
    error_reporting(E_ALL);
    ini_set("display_errors", 1);
    $con = mysqli_connect("aa9334.cafe24.com", "root", "rus7wp850!@vkt", "DB", "8509");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];

    $statement = mysqli_prepare($con, "SELECT * FROM member WHERE id = ?");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $name, $id, $pwd, $address, $age, $sex, $manager, $corona);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["id"] = $id;
    }

    echo json_encode($response);
?>
