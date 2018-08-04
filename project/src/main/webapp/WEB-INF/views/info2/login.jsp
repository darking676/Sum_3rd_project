<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
.location01 {
	padding-top: 150px;
	padding-left: 670px;
}

#naver_id_login {
	padding-left: 120px;
}

.color {
	color: red;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
//<![CDATA[
    function login() {
        var form = document.getElementById("loginForm");
        //TODO 유효성 검사
        return true;
    }        
//]]>
</script>
<%@ include file="../template/header.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		
// 		$("#loginBtn").click(function() {
// 			$("loginForm").attr({
// // 				action="/shop01/login/" method="POST"
// // 				"method" : "post",
// 				"action" : "/shop01/login/"
// 			});
// 			$("loginForm").submit();
// 		});
		
		$('#login').click(function() {
			var memId = $("#memId").val();
			var memPassword = $("#memPassword").val();
			
			if(memId == "") {
				alert("아이디를 입력하세요");
				$("#memId").focus();
				return false;
			}
			
			else if(memPassword == "") {
				alert("비밀번호를 입력하세요");
				$("#memPassword").focus();
				return false;
			}
			
// 			else if() {
// 				alert();
// 				return false;
// 			}
			
// 			else if() {
// 				alert();
// 				return false;
// // 			}
			
			document.login.action = "/shop01/login";
			document.login.submit();
			
		});
	});
</script>
</head>
<body>
	<!-- Join 후 Login -->
	<%@ include file="../template/header2.jsp"%>

	<div class="location01">
<%-- 		<form:form commandName="loginForm" class="form-horizontal">  --%>
 		<form id="loginForm" class="form-horizontal" action="/shop01/login/" method="POST" 
			onsubmit="return login()">
			<fieldset>
				<div id="legend">
					<legend class="">LOGIN</legend>
				</div>
				<div class="control-group">
					<!-- ID -->
					<label class="control-label" for="ID">ID</label>
					<div class="controls">

						<input type="text" id="memId" name="memId" placeholder=""
							class="input-xlarge" style="text-align: Left; width: 288px;" /><br/>
							<form:errors path="memId" class="color" /><br/>
					</div>
				</div>
				<div class="control-group">
					<!-- Password-->
					<label class="control-label" for="password">PASSWORD</label>
					<div class="controls">
						<input type="password" id="memPassword" name="memPassword"
							placeholder="" class="input-xlarge"
							style="text-align: Left; width: 288px;" /> <br/>
							<form:errors path="memPassword" class="color"/><br/><br/>
					</div>
				</div>
				<div class="control-group">
					<!-- Button -->
					<div class="controls">
						<button class="btn btn-info btn-sm active" style="width: 288px"
							type="submit">LOGIN</button>
						<br /> <br />
					</div>
				</div>
			</fieldset>
		</form>
		<button class="btn btn-info btn-sm active" style="width: 288px"
			type="button" onclick="location.href='/shop01/join/'">JOIN</button>
	</div>
	<%-- <div id="naver_id_login" style="text-align:center"><a href="${url}"><img width="200" id="naver"  src="${pageContext.request.contextPath}/resources/imgs/NAVER.PNG"/></a></div> --%>
	<br>




	<%@ include file="../template/footer.jsp"%>
</body>
</html>