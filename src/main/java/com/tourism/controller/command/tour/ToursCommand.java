package com.tourism.controller.command.tour;

import com.tourism.controller.command.Command;
import com.tourism.model.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToursCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final TourService tourService;

    public ToursCommand(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        String sortCol = "hot";
        String sortDir = "DESC";
        try {
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
        long numberOfRecords = tourService.getNumberOfRecords();
        long totalPages = (long) Math.ceil((double) numberOfRecords / size);
        request.setAttribute("tours", tourService.findAllToursPageable(page, size, sortCol, sortDir));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("sortCol", sortCol);
        request.setAttribute("sortDir", sortDir);
        request.setAttribute("totalPages", totalPages);
        return "/WEB-INF/pages/tours.jsp";
    }
}
