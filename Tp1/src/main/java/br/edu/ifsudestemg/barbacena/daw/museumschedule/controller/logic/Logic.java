package br.edu.ifsudestemg.barbacena.daw.museumschedule.controller.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Logic {
	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
