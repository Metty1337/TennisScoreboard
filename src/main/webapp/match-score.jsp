<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="#">Home</a>
                <a class="nav-link" href="#">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <c:choose>

        <c:when test="${matchFinished}">
            <<div style="border: 2px solid #ffffff; height: 100vh; display: flex; justify-content: center; align-items: center;">
                <div class="final-result">
                    <h1>Match Finished</h1>

                    <h2>Winner: ${winnerName}</h2>
                    <h3>Final
                        Score: ${String.format("%d - %d", score.getPlayerOneSets(), score.getPlayerTwoSets())}</h3>

                    <p>${playerOne.getName()} vs ${playerTwo.getName()}</p>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <div class="container">
                <h1>Current match</h1>
                <div class="current-match-image"></div>
                <section class="score">
                    <table class="table">
                        <thead class="result">
                        <tr>
                            <th class="table-text">Player</th>
                            <th class="table-text">Games</th>
                            <th class="table-text">Sets</th>
                            <th class="table-text">Points</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="player1">
                            <td class="table-text">${playerOne.getName()}</td>
                            <td class="table-text">${score.getPlayerOneGames()}</td>
                            <td class="table-text">${score.getPlayerOneSets()}</td>
                            <td class="table-text">
                                <c:choose>
                                    <c:when test="${score.isTieBreak()}">
                                        ${score.getPlayerOneTieBreakPoints()}
                                    </c:when>

                                    <c:otherwise>
                                        ${score.getPlayerOnePoints().getValue()}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="table-text">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/match-score?uuid=${uuid}">
                                    <input type="hidden" name="winnerId" value="${playerOne.getId()}">
                                    <button type="submit" class="score-btn">Score</button>
                                </form>
                            </td>
                        </tr>
                        <tr class="player2">
                            <td class="table-text">${playerTwo.getName()}</td>
                            <td class="table-text">${score.getPlayerTwoGames()}</td>
                            <td class="table-text">${score.getPlayerTwoSets()}</td>
                            <td class="table-text">
                                <c:choose>
                                    <c:when test="${score.isTieBreak()}">
                                        ${score.getPlayerTwoTieBreakPoints()}
                                    </c:when>
                                    <c:otherwise>
                                        ${score.getPlayerTwoPoints().getValue()}
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/match-score?uuid=${uuid}">
                                    <input type="hidden" name="winnerId" value="${playerTwo.getId()}">
                                    <button type="submit" class="score-btn">Score</button>
                                </form>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </section>
            </div>
        </c:otherwise>

    </c:choose>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
