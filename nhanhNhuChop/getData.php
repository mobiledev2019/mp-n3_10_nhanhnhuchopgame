<?php
	$connect = mysqli_connect("localhost","root","","nhanh_nhu_chop");
	mysqli_query($connect,"SET NAMES 'utf8'");

	$querry = "SELECT * FROM questions";

	$data = mysqli_query($connect, $querry);

	//

	class Questions{
		function Questions($idQuestion, $question , $answer1, $answer2 , $answer3, $answer4, $result, $hint){
			$this->ID = $idQuestion;
			$this->QUESTION = $question;
			$this->ANSWER1 = $answer1;
			$this->ANSWER2 = $answer2;
			$this->ANSWER3 = $answer3;
			$this->ANSWER4 = $answer4;
			$this->RESULT = $result;
			$this->HINT = $hint;


		}
	}

	//

	$mangQuestions = array();

	//

	while($row = mysqli_fetch_assoc($data)){
		array_push($mangQuestions, new Questions($row['idQuestion'], $row['question'], $row['answer1'], $row['answer2'], $row['answer3'], $row['answer4'], $row['result'], $row['hint']));
	}

	//

	echo json_encode($mangQuestions);
?>