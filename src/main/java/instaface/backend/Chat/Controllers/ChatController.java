package instaface.backend.Chat.Controllers;

import instaface.backend.Chat.Models.ChatDefine;
import instaface.backend.Chat.Models.ChatLog;
import instaface.backend.Chat.Models.user;
import instaface.backend.Chat.Respository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import instaface.backend.Chat.Services.ChatService;
import javax.servlet.http.HttpSession;

import instaface.backend.domain.entities.Relationship;
import instaface.backend.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableAsync
@Configuration
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRes chatRes;

    @Autowired
    private userRes userRes;

    @Autowired
    private ChatDefineRes chatDefineRes;

    @Autowired
    private _HuyFriendRes HuyFriend;

    @Autowired
    private _HuyUserRes HuyUser;



    @GetMapping("/add/{id}")
    public String chatBox(@PathVariable String id, HttpSession session) {
        session.setAttribute("ID", id);
        session.setAttribute("STTchat", Long.valueOf(0));
        return "redirect:/add";
    }

    //Hiển thị danh sách đoạn chat và người dùng.
    @GetMapping("/add")
    public String chatBox(Model model, HttpSession session) {
        ChatLog ChatLog = new ChatLog();
        String userId = (String) session.getAttribute("ID");
        Long STTchat = (Long) session.getAttribute("STTchat");
        String target = (String) session.getAttribute("IDchat");
        model.addAttribute("chatLog", ChatLog);
        model.addAttribute("chatofme", userId);
        User itme = HuyUser.findById(userId);
        model.addAttribute("myimage", itme.getProfilePicUrl());
        List<ChatLog> chatList = chatRes.findByStt(STTchat);
        model.addAttribute("listOfchats", chatList);


        User targetuser = HuyUser.findById(target);
        if(targetuser != null)
        {
            model.addAttribute("targetimage",targetuser.getProfilePicUrl());
            model.addAttribute("targetname", targetuser.getFirstName() + " " + targetuser.getLastName());
            model.addAttribute("isonline", targetuser.isOnline());

        }




        List<Relationship> userList1 = HuyFriend.findAllByUserOneId(userId);
        List<Relationship> userList2 = HuyFriend.findAllByUserTwoId(userId);
        List<Relationship> allRelationships = new ArrayList<>();
        allRelationships.addAll(userList1);
        allRelationships.addAll(userList2);

// Loại bỏ tham chiếu id từ danh sách allIds
        List<String> allIds = allRelationships.stream()
                .flatMap(relationship -> List.of(relationship.getUserOne().getId(), relationship.getUserTwo().getId()).stream())
                .collect(Collectors.toList());
// Loại bỏ các giá trị trùng lặp từ danh sách allIds
        List<String> uniqueIds = allIds.stream().distinct().collect(Collectors.toList());


        List<User> userList = HuyUser.findAll();
        List<User> filteredUsers = userList.stream()
                .filter(user -> uniqueIds.contains(user.getId()))
                .collect(Collectors.toList());
        model.addAttribute("listOfUsers", filteredUsers);
        model.addAttribute("target", target);

        return "ChatApp/ChatBox";
    }

    //Kênh chat
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ChatLog greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return new ChatLog("Hello","Time");
    }

    //Lấy ngày giờ
    public String getDateTimeAsString() {
        // Chuyển đổi ngày giờ thành chuỗi khi cần
        LocalDateTime dateTime = LocalDateTime.now(); // Thay bằng ngày giờ thực tế
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    //Lưu tin nhắn lên database
    @PostMapping ("/chatSave")
    @ResponseBody
    public RedirectView saveCourse(@ModelAttribute("chatLog") ChatLog chatLog, HttpSession session) {
        chatLog.setTime(getDateTimeAsString());
        chatLog.setIdsender((String) session.getAttribute("ID"));
        chatLog.setStt((Long) session.getAttribute("STTchat"));
        chatService.saveChat(chatLog);
        return new RedirectView("/add");
    }

    //Chọn người để nhắn tin
    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable String id, Model model, HttpSession session) {
        session.setAttribute("IDchat", id);
        String a = (String) session.getAttribute("ID");
        String b = (String) session.getAttribute("IDchat");

        ChatDefine chatDefine = chatDefineRes.findByIdaAndIdb(a,b);
        ChatDefine chatDefine1 = chatDefineRes.findByIdaAndIdb(b,a);
        if (chatDefine == null && chatDefine1 == null) {
            // Bản ghi không tồn tại, tạo bản ghi mới và lưu vào cơ sở dữ liệu
            ChatDefine chatDefine2 = new ChatDefine();
            chatDefine2.setIda(a);
            chatDefine2.setIdb(b);
            // Thiết lập các giá trị khác cho chatDefine (nếu cần)
            chatDefineRes.save(chatDefine2); // Sử dụng phương thức để lưu
            session.setAttribute("STTchat", chatDefine2.getStt());
        }
        else {
            if(chatDefine == null)
            {
                session.setAttribute("STTchat", chatDefine1.getStt());
            }
            else {
                session.setAttribute("STTchat", chatDefine.getStt());
            }

        }
        return "redirect:/add"; // Trả về trang hiển thị chi tiết người dùng
    }

    //Gửi ảnh
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "redirect:/add";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file, HttpSession session) throws IOException {
        // Generate a unique file name using timestamp
        String pathreal = "/PictureSave/";
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path fileNameAndPath = Paths.get(pathreal, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());
        ChatLog chatLog = new ChatLog();
        chatLog.setTime(getDateTimeAsString());
        chatLog.setIdsender((String) session.getAttribute("ID"));
        chatLog.setStt((Long) session.getAttribute("STTchat"));
        chatLog.setText("/images/" + uniqueFileName);
        chatLog.setImage(true);

        chatService.saveChat(chatLog);


        model.addAttribute("msg", "Uploaded image: " + uniqueFileName);
        return "redirect:/add";
    }

    // Helper method to generate a unique file name
    private String generateUniqueFileName(String originalFileName) {
        // You can use various methods to generate a unique name, here's an example with timestamp
        long timestamp = System.currentTimeMillis();
        return timestamp + "_" + originalFileName;
    }
    @GetMapping("/changepasspage/{id}")
    public String changepasspager(@PathVariable String id, HttpSession session)
    {
        session.setAttribute("ID", id);
        return "Login/ChangePass";
    }
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostMapping("/changepass")
    public RedirectView changepassw(@RequestParam("passwrd") String passwrd, HttpSession session)
    {
        User user = HuyUser.findById((String) session.getAttribute("ID"));
        user.setPassword(passwrd);
        user.setPassword(this.bCryptPasswordEncoder.encode(passwrd));
        HuyUser.save(user);
        return new RedirectView("http://localhost:3000");
    }
    @PostMapping("/SendIcon")
    public String sendicon(@RequestParam("icon") String icon, HttpSession session)
    {
        ChatLog chatLog = new ChatLog();
        chatLog.setTime(getDateTimeAsString());
        chatLog.setIdsender((String) session.getAttribute("ID"));
        chatLog.setStt((Long) session.getAttribute("STTchat"));
        chatLog.setText(icon);
        chatLog.setImage(true);
        chatRes.save(chatLog);
        return "redirect:/add";
    }



}
