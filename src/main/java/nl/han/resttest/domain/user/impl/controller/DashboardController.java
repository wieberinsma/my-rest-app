package nl.han.resttest.domain.user.impl.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController
{
    @Secured("ROLE_USER")
    @GetMapping("/dashboard")
    public String index()
    {
        return "dashboard";
    }
}
