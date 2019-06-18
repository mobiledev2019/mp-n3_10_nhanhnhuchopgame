<?php
	$connect = mysqli_connect("localhost","root","","nhanh_nhu_chop");
	mysqli_query($connect,"SET NAMES 'utf8'");
	 $id = $_POST['id_player'];
	//$id = "123";
	 $name = $_POST['name_player'];
	//$name = "123";
	 $score = $_POST['score_player'];
	//$score = "123";

	$query = "INSERT INTO players VALUES('$id','$name','$score')";

	if(mysqli_query($connect, $query)){
		echo "success";
	}
	else{
		echo "error";
	}

?>