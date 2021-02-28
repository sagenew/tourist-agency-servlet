package com.tourism.controller.command.order;

import com.tourism.controller.command.Command;
import com.tourism.controller.dto.DiscountDTO;
import com.tourism.model.service.OrderService;
import com.tourism.util.CommandUtils;
import com.tourism.util.validator.CompositeValidator;
import com.tourism.util.validator.RangeDoubleValidator;
import com.tourism.util.validator.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DiscountSetCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final OrderService orderService;
    private ResourceBundle rb;

    public DiscountSetCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages", CommandUtils.getLocaleFromSession(request));
        String step = request.getParameter("step");
        String threshold = request.getParameter("threshold");

        DiscountDTO discountDTO = DiscountDTO.builder()
                .step(Double.parseDouble(step))
                .threshold(Double.parseDouble(threshold))
                .build();
        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(discountDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("discount", discountDTO);
            request.setAttribute("errors", validationErrorsMap);
            return "/WEB-INF/pages/orders.jsp";
        }
        orderService.setDiscount(discountDTO);
        return "redirect:/tours/orders";
    }

    private Map<String, String[]> getValidationErrorsMap(DiscountDTO discountDTO) {
        Map<String, String[]> validationErrorMap = new HashMap<>();

        CompositeValidator<Double> discountStepValidator = new CompositeValidator<>(
                new RangeDoubleValidator(0, 100, rb.getString("validation.discount.step.range"))
        );

        CompositeValidator<Double> discountThresholdValidator = new CompositeValidator<>(
                new RangeDoubleValidator(0, 100, rb.getString("validation.discount.threshold.range"))
        );

        Result result = discountStepValidator.validate(discountDTO.getStep());
        if (!result.isValid()) {
            validationErrorMap.put("stepErrors", result.getMessage().split("\n"));
        }
        result = discountThresholdValidator.validate(discountDTO.getThreshold());
        if (!result.isValid()) {
            validationErrorMap.put("thresholdErrors", result.getMessage().split("\n"));
        }
        return validationErrorMap;
    }
}
