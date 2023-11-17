package instaface.backend.Chat.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với ứng dụng Spring Boot!");
        return "index";
    }
}
