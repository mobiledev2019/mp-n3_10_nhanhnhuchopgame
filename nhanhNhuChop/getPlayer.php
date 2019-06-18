<?php
	$connect = mysqli_connect("localhost","root","","nhanh_nhu_chop");
	mysqli_query($connect,"SET NAMES 'utf8'");

	$querry = "SELECT * FROM players";

	$data = mysqli_query($connect, $querry);

	//

	class Questions{
		function Questions($id, $name , $score){
			$this->ID = $id;
			$this->NAME = $name;
			$this->SCORE = $score;
		}
	}

	//

	$mangQuestions = array();

	//

	while($row = mysqli_fetch_assoc($data)){
		array_push($mangQuestions, new Questions($row['id'], $row['name'], $row['score']));
	}

	//

	echo json_encode($mangQuestions);
?>