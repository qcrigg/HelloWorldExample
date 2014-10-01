<?php

header('Content-Type:application/json; charset=utf-8');

$con=mysqli_connect("localhost","root","","qcri");
mysqli_set_charset($con, "utf8");


// Check connection
if (mysqli_connect_errno()) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
//mysqli_query("SET NAMES 'utf8'");
//mysqli_query("SET CHARACTER SET 'utf8'");
$ar = 'وقوف';

//echo $ar;

//echo $_POST['ORIGINAL_TRANSLATION'];
// Variables Zeeshan is sending from front end
$user_id = $_POST['user_id'];
$ORIGINAL_WORD = $_POST['ORIGINAL_WORD'];
$RECOGNIZED_WORD = $_POST['RECOGNIZED_WORD'];
$TRANSLATED_WORD = $_POST['TRANSLATED_WORD'];
$ORIGINAL_TRANSLATION = $_POST['ORIGINAL_TRANSLATION'];
$RECOGNITION_CORRECT = $_POST['RECOGNITION_CORRECT'];
$TRANSLATION_CORRECT = $_POST['TRANSLATION_CORRECT'];
$RECOGNITION_TIME = $_POST['RECOGNITION_TIME'];
$TRANSLATION_TIME = $_POST['TRANSLATION_TIME'];

$table_name = "data_en";

$filename = "ar/".preg_replace("['| |\"]", "",$RECOGNIZED_WORD).".mp3";

$qr = "INSERT INTO $table_name (user_id, ORIGINAL_WORD, TRANSLATED_WORD, ORIGINAL_TRANSLATION,  RECOGNIZED_WORD, RECOGNITION_CORRECT, TRANSLATION_CORRECT, RECOGNITION_TIME, TRANSLATION_TIME) VALUES ($user_id, $ORIGINAL_WORD, $TRANSLATED_WORD, $ORIGINAL_TRANSLATION, $RECOGNIZED_WORD, $RECOGNITION_CORRECT, $TRANSLATION_CORRECT, $RECOGNITION_TIME, $TRANSLATION_TIME)";



// Append location so with history
$res = mysqli_query($con,$qr);

$query = urlencode(trim($TRANSLATED_WORD,"'"));

if ($soundfile = file_get_contents("http://translate.google.com/translate_tts?ie=UTF-8&tl=ar&q=".$query))
{
	file_put_contents($filename,$soundfile);
	
	echo $filename;
}
else
 echo "Query $res\n";

//echo "Query: " $qr "\n";

?>
