package metty1337.tennisscoreboard.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metty1337.tennisscoreboard.model.MatchModel;
import metty1337.tennisscoreboard.service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(value = "/matches")
public class FinishedMatchesController extends HttpServlet {
    private static final String PAGE = "page";
    private static final String FILTER_BY_PLAYER_NAME = "filter_by_player_name";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String MATCHES = "matches";
    private static final int PAGE_SIZE = 5;
    @Inject
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Optional.ofNullable(request.getParameter(PAGE))
                .map(Integer::parseInt)
                .orElse(1);
        String filterByPlayerName = Optional.ofNullable(request.getParameter(FILTER_BY_PLAYER_NAME))
                .orElse("");
        int pageSize = PAGE_SIZE;

        List<MatchModel> pageOfMatches = finishedMatchesPersistenceService.getPageOfMatches(page, pageSize, filterByPlayerName);
        int totalPages = finishedMatchesPersistenceService.getTotalPages(pageSize);

        request.setAttribute(PAGE, page);
        request.setAttribute(FILTER_BY_PLAYER_NAME, filterByPlayerName);
        request.setAttribute(TOTAL_PAGES, totalPages);
        request.setAttribute(MATCHES, pageOfMatches);

        request.getRequestDispatcher("/matches.jsp").forward(request, response);
    }
}