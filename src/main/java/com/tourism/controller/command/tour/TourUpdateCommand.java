package com.tourism.controller.command.tour;

import com.tourism.controller.command.Command;
import com.tourism.controller.dto.TourDTO;
import com.tourism.model.entity.Tour;
import com.tourism.model.entity.enums.HotelType;
import com.tourism.model.entity.enums.TourType;
import com.tourism.model.service.TourService;
import com.tourism.util.CommandUtils;
import com.tourism.util.validator.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TourUpdateCommand implements Command {
    public static final Logger log = LogManager.getLogger();
    private final TourService tourService;
    private ResourceBundle rb;

    public TourUpdateCommand(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages",
                CommandUtils.getLocaleFromSession(request));

        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }

        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String price = request.getParameter("price");
        String groupSize = request.getParameter("groupSize");
        String hotel = request.getParameter("hotel");
        String description = request.getParameter("description");

        if (!ObjectUtils.allNotNull(name, type, price, groupSize, hotel)) {
            Tour tour = tourService.findTourById(id);
            request.setAttribute("tour", tour);
            request.setAttribute("tourTypes", TourType.values());
            request.setAttribute("hotelTypes", HotelType.values());
            return "/WEB-INF/pages/tour-update.jsp";
        }

        TourDTO tourDTO = TourDTO.builder()
                .name(name)
                .type(TourType.valueOf(type))
                .price(Double.parseDouble(price))
                .groupSize(Integer.parseInt(groupSize))
                .hotel(HotelType.valueOf(hotel))
                .description(description)
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(tourDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("tour", tourDTO);
            request.setAttribute("tourTypes", TourType.values());
            request.setAttribute("hotelTypes", HotelType.values());
            request.setAttribute("errors", validationErrorsMap);
            return "/WEB-INF/pages/tour-update.jsp";
        }
        tourService.updateTour(id, tourDTO);
        return "redirect:/tours";
    }

    private Map<String, String[]> getValidationErrorsMap(TourDTO tourDTO) {
        Map<String, String[]> validationErrorMap = new HashMap<>();

        CompositeValidator<String> tourNameValidator = new CompositeValidator<>(
                new SizeValidator(0, 50, rb.getString("validation.tour.name.size")),
                new NotBlankValidator(rb.getString("validation.tour.name.not_blank"))
        );

        CompositeValidator<Double> tourPriceValidator = new CompositeValidator<>(
                new PositiveDoubleValidator(rb.getString("validation.tour.price.positive"))
        );

        CompositeValidator<Integer> tourGroupSizeValidator = new CompositeValidator<>(
                new PositiveIntegerValidator(rb.getString("validation.tour.group_size.positive"))
        );

        CompositeValidator<String> tourDescriptionValidator = new CompositeValidator<>(
                new SizeValidator(0, 200, rb.getString("validation.tour.description.size"))
        );

        Result result = tourNameValidator.validate(tourDTO.getName());
        if (!result.isValid()) {
            validationErrorMap.put("nameErrors", result.getMessage().split("\n"));
        }
        result = tourPriceValidator.validate(tourDTO.getPrice());
        if (!result.isValid()) {
            validationErrorMap.put("priceErrors", result.getMessage().split("\n"));
        }
        result = tourGroupSizeValidator.validate(tourDTO.getGroupSize());
        if (!result.isValid()) {
            validationErrorMap.put("groupSizeErrors", result.getMessage().split("\n"));
        }
        result = tourDescriptionValidator.validate(tourDTO.getDescription());
        if (!result.isValid()) {
            validationErrorMap.put("descriptionErrors", result.getMessage().split("\n"));
        }
        return validationErrorMap;
    }
}
