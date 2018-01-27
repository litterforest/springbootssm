<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户信息列表</title>
</head>
<body>
	<table border="1" >
		<thead>
			<tr>
				<th>用户编号</th>
				<th>登录名称</th>
				<th>真实名称</th>
				<th>手机号码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${secureUserList }" var="secureUser" >
				<tr>
					<td>${secureUser.id }</td>
					<td>${secureUser.username }</td>
					<td>${secureUser.realname }</td>
					<td>${secureUser.mobile }</td>
					<td><form action="${ctx }/SecureUser/delete?id=${secureUser.id}" method="post" ><input type="hidden" name="_csrf" value="${_csrf.token}" ><input type="submit" value="删除" ></form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>