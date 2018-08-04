<h2>Login Validation</h2><br/>
-바꾼 것-<br/>
0. pom.xml<br/>
<br/>
<!-- VALIDATION --><br/>
<!-- hibernate --><br/>
  <dependency><br/>
  <groupId>org.hibernate</groupId><br/>
  <artifactId>hibernate-annotations</artifactId><br/>
  <version>3.5.6-Final</version><br/>
</dependency><br/>
<br/>
<br/>
<!-- hibernate --><br/>
<dependency><br/>
  <groupId>org.hibernate</groupId><br/>
  <artifactId>hibernate-validator</artifactId><br/>
  <version>5.1.3.Final</version><br/>
</dependency><br/>
<br/>
1. MemVo.java<br/>
@NotEmpty(message="아이디를 입력하세요")<br/>
private String memId<br/>
@NotEmpty(message="비밀번호를 입력하세요")<br/>
private String memPassword;<br/>
2. MembersController.java<br/>
<br/>
3. info / login.jsp<br/>
<br/>
<style><br/>
  .color {<br/>
    color: red;<br/>
  }<br/>
</style><br/>
<script><br/>
$(document).ready(function() {<br/>
		$("#login").click(function() {<br/>
			$("loginForm").attr({<br/>
// 				action="/shop01/login/" method="POST"<br/>
				"method" : "post",<br/>
				"action" : "/shop01/login/"<br/>
			});<br/>
			$("loginForm").submit();<br/>
		});<br/>
   });<br/>
</script>
