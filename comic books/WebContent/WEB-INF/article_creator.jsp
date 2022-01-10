<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/article_creator.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
<meta charset="ISO-8859-1">
</head>
<body>
<div class="header">
<jsp:include page="./header.jsp" />
</div>
<!-- if article is being created the submit button will read to publish else it will ask to update -->
<c:choose>
 <c:when test="${article == null}">
 <c:set var="formAction" value="${pageContext.request.contextPath}/admin/publish"/>
 <c:set var="actionLabel" value="Publish"/>
 </c:when>
 <c:otherwise>
 <c:set var ="formAction" value="${pageContext.request.contextPath}/admin/update"/>
 <c:set var="actionLabel" value="Update"/>
 </c:otherwise>
 </c:choose>
 
 <form action="${formAction}" id="article-form">
	<div class="article-body-container">
	<div class="title-header">
     Title: <input type="text" name="title" value="${article == null? '' : article.title}"/>   Thumbnail-url: <input type="text" name="thumbnail" value="${article == null? '' : article.thumbnail}"/>
     </div>
		<textarea id="article-body" name="article-body" form="article-form" >
		${article.body}
</textarea>
<div class="submit-button-container">
<input type="hidden" name="articleID" value="${article.id}"/>
<input class="mdl-button " type="submit" value="${actionLabel}"/>
</div>
	</div>
	
	</form>
<div class="footer">
<jsp:include page="./footer.jsp" />
</div>
</body>
<script>
	var simplemde = new SimpleMDE({
		element : document.getElementById("article-body")
	});
</script>
</html>