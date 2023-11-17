package instaface.backend.Chat.Controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import instaface.backend.Chat.Models.user;
import instaface.backend.Chat.Respository.userRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private userRes userres;



    private Long loggedInUserId;
    private String loggedInUserName;


    // Hiện trang đăng ký
    @GetMapping("/Register")
    public String register(Model model) {
        user user = new user();
        model.addAttribute("user", user);
        return "/Login/Register";
    }

    //Lưu thông tin đăng ký
    @PostMapping("/RegisterSave")
    public String registerSave(@ModelAttribute("user") user user) {
        userres.save(user);
        return "redirect:/";
    }

    //Hiện trang đăng nhập
    @GetMapping("/Login")
    public String Login(Model model) {
        user user = new user();
        model.addAttribute("user", user);
        return "/Login/Login";
    }

    //Lưu thông itn đăng nhập và duy trì đăng nhập.
    @PostMapping("/authenticate")
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password,  HttpSession session, HttpServletResponse response) throws IOException {
        user usercheck = userres.findByUsernameAndPassword(username, password);
        if(usercheck != null) {
            session.setAttribute("ID", "a2c23be7-ce99-4f14-a567-2da7050a098b");
            session.setAttribute("STTchat", Long.valueOf(0));
            response.sendRedirect("/add");
            return null;
        }
            return "redirect:/login?error=true";
    }


}
