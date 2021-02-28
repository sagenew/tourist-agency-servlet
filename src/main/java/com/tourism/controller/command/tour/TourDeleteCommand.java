package com.tourism.controller.command.tour;

import com.tourism.controller.command.Command;
import com.tourism.model.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class TourDeleteCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final TourService tourService;

    public TourDeleteCommand(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long tourId;
        int page = 0;
        int size = 5;
        String sortCol = "hot";
        String sortDir = "DESC";
        try {
            tourId = Long.parseLong(request.getParameter("id"));
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            if (request.getParameter("size") != null)
                size = Integer.parseInt(request.getParameter("size"));
            if (request.getParameter("sortCol") != null)
                sortCol = request.getParameter("sortCol");
            if (request.getParameter("sortDir") != null)
                sortDir = request.getParameter("sortDir");
        } catch (NumberFormatException e) {
            log.warn("Can not parse request parameter");
            return "/WEB-INF/error/404.jsp";
        }
        tourService.deleteTour(tourId);

        return "redirect:/tours" +
                "?page=" + page +
                "&size=" + size +
                "&sortCol=" + sortCol +
                "&sortDir=" + sortDir;
    }
}
