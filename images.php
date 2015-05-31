<?php
				$host_name  = "db562916345.db.1and1.com";
				$database   = "db562916345";
				$user_name  = "dbo562916345";
				$password   = "asdf1234";

				$con = new mysqli($host_name, $user_name, $password, $database);
				if(!$con)
				{
					die('Could not connect: '. mysql_error());
				}
				
				$input_page = $_GET['page'];
				$sql = "SELECT * FROM `graduatephoto`;";
				$result = $con -> query($sql);
				echo "[";
				$flag = 0;
				$index = 0;
				while($row = $result -> fetch_row())
				{
					if ($index < ($input_page + 1) * 10 && $index >= $input_page * 10) {
						if($flag != 0) echo ",";
						$flag++;
						echo '{"image":"'.$row[4].'"}';
					}
					$index ++;
				}
				echo "]";
?>