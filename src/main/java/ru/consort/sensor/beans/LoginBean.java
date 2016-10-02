package ru.consort.sensor.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import ru.consort.sensor.handlers.ExceptionHandler;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Автор: Туров Данил
 * Дата создания: 25.08.2016.
 * Содержит реализацию методов
 * управления страницей /login.
 * Проект: "Модуль локальной автоматизации".
 * Консорт.
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    private String login = "";
    private String password = "";

    public void doLogin() throws IOException {
        if (login.equalsIgnoreCase("lukperm1") && password.equalsIgnoreCase("lkp1vdl")){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("authorized", true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/temperature");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("authorized", false);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/NO");
        }

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
