<style>
@import url('https://fonts.googleapis.com/css?family=Nanum+Gothic');

body {
	font-family: 'Nanum Gothic', sans-serif;
}
</style>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
.location01 {
	padding-top: 0px;
	padding-left: 190px;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../template3/header.jsp"%>
</head>
<body>
	<%@ include file="../template3/header2.jsp"%>
	<div class="location01">
		<div class="row">
			<div class="col-xs-2 col-md-3">
				<a href="/shop01/mypage3/" class="thumbnail"> <img
					src="../resources/imgs2/mypage01.jpg" alt="..."
					class="img-responsive img-rounded">
				</a>
			</div>
			<div class="col-xs-2 col-md-3">
				<a href="#" class="thumbnail"> <img
					src="../resources/imgs2/mypage02.jpg" alt="..."
					class="img-responsive img-rounded">
				</a>
			</div>
		</div>
	</div>
	<%@ include file="../template3/footer.jsp"%>
</body>
</html>