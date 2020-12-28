<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<head>
<meta charset="ISO-8859-1">
<jsp:include page="../index.jsp" />
<link rel="stylesheet" href="http://localhost:8080/comic-books/css/header.css"/>

</head>
<body>
<div class="header-container">
<div class="mdl-grid nav-container">

<div class="mdl-cell--2-col">sign In | join</div>
<div class="mdl-layout-spacer"></div>

<div class="mdl-layout-spacer"></div>
<input class="mdl-cell--2-col" type="text" name ="search" />
<nav class="mdl-cell--12-col">
<span class="mdl-cell--3-col logo-title"><img src="${pageContext.request.contextPath}/img/comicverse_logo.png" 
    width="240" height="50" ></span>

<ul class="mdl-grid header-ul">

    <li class="mdl-cell--1-col"><a href="${pageContext.request.contextPath}/home"><span class="material-icons">
home</span></a></li>
    <li class="mdl-cell--2-col"><a href="">movies</a></li>
    <li class="mdl-cell--2-col"><a href="">shows</a></li>
    <li class="mdl-cell--2-col"><a href="">news</a></li>
    <li class="mdl-cell--1-col"><a href="">store</a></li>

</ul></nav>
</div>
</div>
</body>
