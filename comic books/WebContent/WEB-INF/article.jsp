<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="http://localhost:8080/comic-books/css/article.css"/>
</head>
<body>
<div id=main-content>
<jsp:include page="/WEB-INF/header.jsp"/>

<div class="article-title"><h1>${article.title}</h1></div>
 
 <div class="article-author"><h3>${article.author}</h3><span class="article-date">${article.date}</span></div>
<div class="article-body-container">
    
       ${article.body}

</div>
</div>
<div class="footer">
<jsp:include page="/WEB-INF/footer.jsp" />
</div>
</body>
</html>