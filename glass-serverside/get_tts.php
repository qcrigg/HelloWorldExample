<?php

$query = urldecode($_GET['ORIGINAL_WORD']);
$filename = "en/".preg_replace("['| |\"]", "",$query).".mp3";
$filename = strtolower($filename);

if (file_exists($filename))
 	print $filename;
else if ($soundfile = file_get_contents("http://translate.google.com/translate_tts?tl=en&q=".urlencode($query)))
{
	file_put_contents($filename,$soundfile);
	
	print $filename;
}

?>
