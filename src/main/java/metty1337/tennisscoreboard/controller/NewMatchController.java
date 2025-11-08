package metty1337.tennisscoreboard.controller;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metty1337.tennisscoreboard.service.OngoingMatchesService;

import java.io.IOException;

@WebServlet(value = "/new-match")
public class NewMatchController extends HttpServlet {
    private static final String PLAYER_ONE_PARAMETER = "playerOne";
    private static final String PLAYER_TWO_PARAMETER = "playerTwo";

    @Inject
    private OngoingMatchesService ongoingMatchesService;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String playerOneName = request.getParameter(PLAYER_ONE_PARAMETER);
        String playerTwoName = request.getParameter(PLAYER_TWO_PARAMETER);

        String uuid = ongoingMatchesService.createMatch(playerOneName, playerTwoName);
        response.sendRedirect("/match-score?uuid=$%s".formatted(uuid));
    }
}