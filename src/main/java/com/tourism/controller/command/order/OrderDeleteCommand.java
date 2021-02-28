package com.tourism.controller.command.order;

import com.tourism.controller.command.Command;
import com.tourism.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class OrderDeleteCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final OrderService orderService;

    public OrderDeleteCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long orderId;
        int page = 0;
        int size = 5;
        String sortCol = "orders.id";
        String sortDir = "DESC";
        try {
            orderId = Long.parseLong(request.getParameter("id"));
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
        orderService.deleteOrder(orderId);

        return "redirect:/tours/orders" +
                "?page=" + page +
                "&size=" + size +
                "&sortCol=" + sortCol +
                "&sortDir=" + sortDir;
    }
}