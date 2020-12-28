<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>home</title>
<link href="./css/home.css" rel="stylesheet" />
<script>
	$(function() {

		$("#dialog").dialog({
			autoOpen : false,
			modal : true
		});

		$("#deleteAction").on("click", function(e) {
			e.preventDefault();
			$("#dialog").dialog("open");
		});

	});
</script>
</head>
<style>
.carousel-inner>.item>img {
	display: block;
	max-width: 100%;
	max-height: 650px;
	margin: 0 auto;
}
</style>
<body class="mdl-layout">
	<div class="page-container">
		<jsp:include page="./WEB-INF/header.jsp" />
		<div id="content-wrap">
			<div id="topStories" class="carousel slide" data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#topStories" data-slide-to="0" class="active"></li>
					<li data-target="#topStories" data-slide-to="1"></li>
					<li data-target="#topStories" data-slide-to="2"></li>
				</ol>

				<div class="carousel-inner">
					<div class="item active" data-interval="5000">
						<img src="https://bit.ly/2JLNdCj" alt="" style="width: 100%;">
						<div class="feature-promo">
							<h3>The Boys season 3 on Netflix</h3>
							<button>go there</button>
						</div>
					</div>

					<div class="item" data-interval="5000">
						<img src="https://bit.ly/3gl1tOd" alt="xbox promo"
							style="width: 100%;">
						<div class="feature-promo">
							<h3>Marvel Xbox series x Spider-man bundle sweepstakes</h3>
							<button>Enter</button>
						</div>
					</div>

					<div class="item" data-interval="5000">
						<img src="https://bit.ly/36XRP12" alt="dc future"
							style="width: 100%;">
						<div class="feature-promo">
							<h3>DC Future State an exciting revolution</h3>
							<button>go here</button>
						</div>
					</div>
				</div>

			</div>

			<div class="mdl-grid featured-comics-container">
				<h1 class="mdl-cell--12-col">New Issues</h1>
				<c:forEach items="${featuredComics}" var="comic">
					<div class="mdl-cell--3-col mdl-card">
						<img src="${comic.img}" width="173" height="300">
						<div class="feature-item-title">${comic.title}<span
								class="issue-number">#${comic.issue}</span>
						</div>
					</div>
				</c:forEach>

			</div>

			<div class="holiday">
				<div class="holiday-caption">
					<h1>Gift guide this holiday!</h1>
					<button class="mdl-button mdl-color--light-blue-100">See
						deals</button>
				</div>
			</div>
			<div class="category-title">
				<h1>News</h1>
			</div>

			<div class="articles-list-container">
				<div style="border: 1px solid black; width: 100%;">
					<a href="${pageContext.request.contextPath}/admin/create">create
						new article</a>
				</div>
				<c:forEach items="${articles}" var="article">
					<c:set var="articlePath"
						value="${pageContext.request.contextPath}/article/${article.title.replace(' ','-')}" />
					<div class="article-container">

						<a href="${articlePath}"> <input type="hidden" name="hiddenID"
							value="${article.id}"> <img src="${article.thumbnail}"
							width="300" height="150" />
						</a> <span class="article-title-container"> <a
							href="${articlePath}"> <input type="hidden" name="hiddenID"
								value="${article.id}">

								<div>
									<h2>${article.title}</h2>
								</div>
								<div>
									<h5>${article.date}</h5>
								</div>
						</a>
						</span>

						<div class="admin-controls-container">
							<a
								href="${pageContext.request.contextPath}/admin/edit?article-id=${article.id}">update</a>
							<a
								href="${pageContext.request.contextPath}/admin/delete?article-id=${article.id}">delete</a>
						</div>
					</div>

				</c:forEach>

			</div>
			<div class="page-numbers">
				<form action="" method="post">

					<c:forEach items="${paginator.pagesList}" var="page">
						<!-- if page number active make dark disabled -->
						<input type="submit" class="mdl-button" name="page"
							value="${page}"
							<c:if test="${page == paginator.currentPage}">
					
							<c:out value="disabled='disabled'"/>
						    <c:out value="style ='border-bottom:1px solid black'" escapeXml="false"/>
							 </c:if> />

					</c:forEach>
				</form>
			</div>

		</div>

		<div class=".footer-container">
			<jsp:include page="./WEB-INF/footer.jsp" />
		</div>
	</div>

</body>

</html>