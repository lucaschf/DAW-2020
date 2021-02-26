package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.AttractionDao;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Attraction;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Message.MessageType.SUCCESS;

public class AddAttraction implements Logic {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Attraction attraction = new Attraction();

        attraction.setTitle(request.getParameter("title"));
        attraction.setDetails(request.getParameter("details"));
        attraction.setMuseum(new MuseumDAO().fetchById(Long.parseLong(request.getParameter("museum_id"))));
        attraction.setCoverUrl(request.getParameter("coverUrl"));
        attraction.setBeginningExhibition(LocalDate.parse(request.getParameter("begin")));
        attraction.setEndExhibition(LocalDate.parse(request.getParameter("end")));

        if (new AttractionDao().add(attraction)) {
            request.setAttribute(MESSAGE_PARAM, new Message(Constants.ATTRACTION_SUCCESSFUL_INSERTED, SUCCESS));
        } else {
            putBackData(request, attraction);
            request.setAttribute(MESSAGE_PARAM, new Message(Constants.FAIL_TO_ADD_ATTRACTION));
        }

        request.getRequestDispatcher("add_attraction.jsp").forward(request, response);
    }

    private void putBackData(HttpServletRequest request, Attraction attraction){
        request.setAttribute("title", attraction.getTitle());
        request.setAttribute("details", attraction.getDetails());
        request.setAttribute("museum_id", attraction.getMuseum().getId());
        request.setAttribute("coverUrl", attraction.getCoverUrl());
        request.setAttribute("begin", attraction.getBeginningExhibition());
        request.setAttribute("end", attraction.getEndExhibition());
    }
}
