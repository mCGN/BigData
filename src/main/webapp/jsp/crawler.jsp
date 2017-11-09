<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>爬虫</title>
<link style="text/css" rel="stylesheet" href="./css/index.css" />
<script type="text/javascript">
	function Msumbit() {
		var keyword = document.getElementById("keyword");

	}
</script>
</head>

<body background="./images/bg.jpg">
	<hr />

	<br />
	<br />
	<center>
		<table border="0" cellspacing="0" cellpadding="5" align="center">

			<tr>
				<form id="form1" name="form1" method="post" action="#" method="get">

					<td><select class="select_css" name="keyword">
							<option value="lianjia">1</option>
							<option value="taobao">2</option>
							<option value="carhome">3</option>
							<option value="dazhongdianping">4</option>
							<option value="guahaowang">5</option>
					</select></td>
					<td><div class="submit">
							<input class="submit_css" type="submit" name="sumbit"
								value="CAT" onClick="Msumbit()" />
						</div></td>
				</form>

			</tr>
		</table>
		<br />
		<br />


	</center>
</body>
</html>
