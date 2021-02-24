package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Logic {

	String MESSAGE_PARAM = "message";

	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
