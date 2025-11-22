package metty1337.tennisscoreboard.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metty1337.tennisscoreboard.dto.MatchScoreDto;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.service.OngoingMatchesService;

import java.io.IOException;

@WebServlet(value = "/match-score")
public class MatchScoreController extends HttpServlet {
    private static final String UUID = "uuid";

    @Inject
    private OngoingMatchesService ongoingMatchesService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter(UUID);
        MatchScoreModel matchScoreModel = ongoingMatchesService.getMatch(uuid);

        request.setAttribute("playerOne", matchScoreModel.playerOne());
        request.setAttribute("playerTwo", matchScoreModel.playerTwo());
        request.setAttribute("score", matchScoreModel.score());

        request.getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}