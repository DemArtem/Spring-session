package org.itstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringSessionJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionJdbcApplication.class, args);
    }

    @GetMapping( "/" )
    public String index(Model model, HttpSession session) {
        List<String> favoriteColors = getFavColors(session);
        model.addAttribute("favoriteColors", favoriteColors);
        model.addAttribute("sessionId", session.getId());
        return "index";
    }

    @PostMapping("/saveColor")
    public String saveColor(@RequestBody String color, HttpServletRequest request){
        List<String> favoriteColors = getFavColors(request.getSession());
        if (!color.isEmpty())
            favoriteColors.add(color);
        request.getSession().setAttribute("favoriteColors",favoriteColors);
        return "redirect:/";
    }

    private List<String> getFavColors(HttpSession session) {
        List<String> favoriteColors = (List<String>) session.getAttribute("favoriteColors");
        if (favoriteColors == null) {
            favoriteColors = new ArrayList<>();
        }
        return favoriteColors;
    }
}