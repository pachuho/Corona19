<?php
    error_reporting(E_ALL);
    ini_set("display_errors", 1);

    $con=mysqli_connect("aa9334.cafe24.com", "root", "rus7wp850!@vkt", "DB", "8509");

    /* select data */
    $selectSQL = "SELECT location.* FROM location JOIN member ON location.id = member.id AND member.corona = 'Y'";
    $result = mysqli_query($con, $selectSQL);

    $data = array();
    
    if ($result) {
        while ($row=mysqli_fetch_array($result)) {
            array_push($data,array(
              'id'=>$row[0],
              'time'=>$row[1], 
              'la'=>$row[2],
              'lo'=>$row[3],
              'store'=>$row[4]));
        }
        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("location"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

		echo $json; // => 출력되는 값이 이 코드로 하여금 android로 전송된다..

	} else {
        echo "SQL문 처리중 에러 발생 : ";
        echo mysqli_error($con);
    }

    // DB 연동을 종료 합니다
    mysqli_close($con);
?>
