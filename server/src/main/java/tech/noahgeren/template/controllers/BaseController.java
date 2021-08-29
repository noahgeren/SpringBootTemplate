package tech.noahgeren.template.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BaseController implements ErrorController {
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "index";
	}
	
	@PostMapping("/register")
	@ResponseBody
	public Object register(HttpServletRequest request) {
		// TODO: Add registering here
		return null;
	}
	
	@RequestMapping(value = "/error", produces="application/json")
	@ResponseBody
	public Map<String, Object> handleErrorJson(HttpServletRequest request) {
		final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
            final int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	log.error("Unhandled error:", (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
            }
        }
		final Map<String, Object> response = new HashMap<>();
		response.put("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		response.put("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
		response.put("timestamp", LocalDateTime.now());
		response.put("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
		return response;
	}
	
	@RequestMapping(value = "/error",  produces="text/html")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        // get error status
        final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            final int statusCode = Integer.parseInt(status.toString());

            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errorpages/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	log.error("Unhandled error:", (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
                return "errorpages/500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "errorpages/403";
            }
        }

        // display generic error
        return "errorpages/error";
    }

}
