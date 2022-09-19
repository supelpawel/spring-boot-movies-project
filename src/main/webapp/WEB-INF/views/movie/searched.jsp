<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Searched movie</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    You are logged as: <sec:authentication property="principal.username"/><br>
    Your role: <sec:authentication property="principal.authorities"/>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <form action="/logout" method="post">
        <input class="fa fa-id-badge" type="submit" value="Log out">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</sec:authorize>
<h1>Searched movie</h1>
<table style="text-align:center">
    <tr>
        <th>Title</th>
        <th>Plot</th>
        <th>Genre</th>
        <th>Director</th>
        <th>Poster</th>
    </tr>
    <tr>
        <td>${movie.title}</td>
        <td>${movie.plot}</td>
        <td>${movie.genre}</td>
        <td>${movie.director}</td>
        <td><img src="${movie.poster}" alt="movie poster"></td>
        <td><a href="/movie/favourite/${movie.title}/${movie.year}">Add to the favourite movies</a>
        </td>
    </tr>
</table>
<p>
    <a href="/movie/search">Search a movie</a>
</p>
<p>
    <a href="/movie/favourite">Favourite movies</a>
</p>
<p>
    <a href="/movie/top3">TOP 3</a>
</p>
</body>
</html>
