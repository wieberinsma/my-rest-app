package nl.han.rwd.srd.domain.user.impl.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateController
{
    @PreAuthorize("hasAuthority('PRIVATE')")
    @GetMapping("/private")
    public String privateTest() {
        return "This line should not be reached.";
    }
}
