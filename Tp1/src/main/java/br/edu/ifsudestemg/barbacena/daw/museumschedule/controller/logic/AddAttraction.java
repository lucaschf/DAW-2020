package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.AttractionDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Attraction;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.PagesNames.ATTRACTION_REGISTRATION;
import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;

public class AddAttraction implements Logic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Attraction attraction = getAttractionFromRequest(request);

        Message message;

        if (new AttractionDao().add(attraction)) {
            message = new Message(Constants.ATTRACTION_SUCCESSFUL_INSERTED, SUCCESS);
        } else {
            putBackData(request, attraction);
            message = new Message(Constants.FAIL_TO_ADD_ATTRACTION);
        }

        request.setAttribute(MESSAGE_PARAM, message);

        return ATTRACTION_REGISTRATION;
    }

    private Attraction getAttractionFromRequest(HttpServletRequest request) {
        Attraction attraction = new Attraction();

        attraction.setTitle(request.getParameter("title"));
        attraction.setDetails(request.getParameter("details"));
        attraction.setBeginningExhibition(LocalDate.parse(request.getParameter("begin")));
        attraction.setEndExhibition(LocalDate.parse(request.getParameter("end")));

        attraction.setCoverUrl(request.getParameter("coverUrl"));

        attraction.setMuseum(getCurrentUser(request).isSystemAdmin() ?
                new MuseumDAO().fetchById(Long.parseLong(request.getParameter("museum_id"))) :
                getCurrentUser(request).getEmployee().getMuseum());

        return attraction;
    }

    private void putBackData(HttpServletRequest request, Attraction attraction) {
        request.setAttribute("title", attraction.getTitle());
        request.setAttribute("details", attraction.getDetails());
        request.setAttribute("museum_id", attraction.getMuseum().getId());
        request.setAttribute("coverUrl", attraction.getCoverUrl());
        request.setAttribute("begin", attraction.getBeginningExhibition());
        request.setAttribute("end", attraction.getEndExhibition());
    }

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("user");
    }
}
