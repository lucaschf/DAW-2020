package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface Logic {

    String MESSAGE_PARAM = "message";

    void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
