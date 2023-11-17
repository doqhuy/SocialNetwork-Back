package instaface.backend.Chat.Controllers;

import instaface.backend.Chat.Models.ChatGroup;
import instaface.backend.Chat.Models.ChatGroupLogs;
import instaface.backend.Chat.Models.ChatGroupMembers;
import instaface.backend.Chat.Models.ChatLog;
import instaface.backend.Chat.Respository.*;
import instaface.backend.domain.entities.Relationship;
import instaface.backend.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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
public class ChatGroupController {

    @Autowired
    private ChatGroupRes chatGroupRes;

    @Autowired
    private ChatGroupMembersRes chatGroupMembersRes;

    @Autowired
    private ChatGroupLogsRes chatGroupLogsRes;

    @Autowired
    private _HuyUserRes HuyUser;

    @Autowired
    private _HuyFriendRes HuyFriend;

    @GetMapping("/Chat/Groups/Return")
    public String returnChat(HttpSession httpSession)
    {
        httpSession.setAttribute("STTgroup", Long.valueOf(0));
        return "redirect:/Chat/Groups";
    }

    //Hiển thị đoạn chat
    @GetMapping("/Chat/Groups")
    public String Group(Model model, HttpSession httpSession, @ModelAttribute("successMessage") String successMessage) {


        ChatGroup chatGroupcheckavailable = chatGroupRes.findByStt((long) httpSession.getAttribute("STTgroup"));
        if(chatGroupcheckavailable == null)
        {
            httpSession.setAttribute("STTgroup", Long.valueOf(0));
        }

        model.addAttribute("successMessage", successMessage);

        //Hiển thị các nhóm
        String userId = (String) httpSession.getAttribute("ID");
        List<ChatGroupMembers> groupMembersList = chatGroupMembersRes.findAllByIdmember(userId);
        List<ChatGroup> groups = new ArrayList<>();
        for (ChatGroupMembers chatGroupMembers: groupMembersList)
        {
            ChatGroup chatGroup = chatGroupRes.findByStt(chatGroupMembers.getStt());
            groups.add(chatGroup);
        }
        model.addAttribute("listofgroup", groups);

        //Hiển thị các đoạn chat
        Long GroupID = (long) httpSession.getAttribute("STTgroup");
        List<ChatGroupLogs> chatGroupLogsList = chatGroupLogsRes.findAllByStt(GroupID);
        model.addAttribute("chatofme", userId);
        ChatGroupLogs ChatGroupLogs = new ChatGroupLogs();
        model.addAttribute("chatGroupLogs", ChatGroupLogs);
        model.addAttribute("listOfchats", chatGroupLogsList);
        model.addAttribute("image2", "/PictureSave/1699626578843_av.png");
        model.addAttribute("targetGroup", (long) httpSession.getAttribute("STTgroup"));
        ChatGroup chatGroupAD = chatGroupRes.findByStt((long) httpSession.getAttribute("STTgroup"));
        model.addAttribute("groupAD", chatGroupAD.getAdmin());
        model.addAttribute("groupImage", chatGroupAD.getGroupimage());
        model.addAttribute("groupName", chatGroupAD.getName());






        //Lấy danh sách tất cả bạn
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
        uniqueIds.removeIf(value -> value.equals(userId));


        //Hiển thị danh sách người tham gia
        List<User> userList = HuyUser.findAll();
        List<User> filteredUsers = userList.stream()
                .filter(user -> uniqueIds.contains(user.getId()))
                .collect(Collectors.toList());
        model.addAttribute("listOfUsers", filteredUsers);

        List<ChatGroupMembers> chatGroupMembersList = chatGroupMembersRes.findAllByStt(GroupID);
        chatGroupMembersList.removeIf(value -> value.getIdmember().equals(userId));
        model.addAttribute("chatGroupMembersList", chatGroupMembersList);

        return "ChatApp/ChatGroup";
    }

    //Chọn nhóm để nhắn
    @GetMapping("/Chat/Groups/{stt}")
    public String getGroup(@PathVariable long stt, Model model, HttpSession session) {
        session.setAttribute("STTgroup", stt);
        return "redirect:/Chat/Groups";
    }

    //Gửi tin nhắn
    @PostMapping("/Chat/Groups/save")
    @ResponseBody
    public RedirectView saveChatGr(@ModelAttribute("chatGroupLogs") ChatGroupLogs chatGroupLogs, Model model, HttpSession session) {
        chatGroupLogs.setTime(getDateTimeAsString());
        chatGroupLogs.setIdsender((String) session.getAttribute("ID"));
        chatGroupLogs.setStt((Long) session.getAttribute("STTgroup"));
        User user = HuyUser.findById((String) session.getAttribute("ID"));
        chatGroupLogs.setSenderName(user.getLastName());
        chatGroupLogs.setSenderAvatar(user.getProfilePicUrl());
        chatGroupLogsRes.save(chatGroupLogs);
        return new RedirectView("/Chat/Groups");
    }

    //Upload ảnh
    @PostMapping("/group/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file, HttpSession session) throws IOException {
        // Generate a unique file name using timestamp
        String pathreal = "/PictureSave/";
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path fileNameAndPath = Paths.get(pathreal, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());
        ChatGroupLogs chatGroupLogs = new ChatGroupLogs();
        chatGroupLogs.setTime(getDateTimeAsString());
        chatGroupLogs.setIdsender((String) session.getAttribute("ID"));
        chatGroupLogs.setStt((Long) session.getAttribute("STTgroup"));
        chatGroupLogs.setText("/images/" + uniqueFileName);
        chatGroupLogs.setImage(true);

        chatGroupLogsRes.save(chatGroupLogs);


        model.addAttribute("msg", "Uploaded image: " + uniqueFileName);
        return "redirect:/Chat/Groups";
    }

    //Tạo group chat mới
    @GetMapping("/createnewgroup")
    public String CreateGroup(Model model, HttpSession httpSession)
    {
        String userID = (String) httpSession.getAttribute("ID");
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setName("New Group Chat");
        chatGroup.setAdmin(userID);
        chatGroup.setGroupimage("https://i.pinimg.com/564x/a3/33/85/a3338541288aa31ff713edaf12c99961.jpg");
        chatGroup.setCount(1);
        ChatGroup savedChatGroup = chatGroupRes.save(chatGroup);

        User user = HuyUser.findById(userID);

        ChatGroupMembers chatGroupMembers = new ChatGroupMembers();
        chatGroupMembers.setStt(savedChatGroup.getStt());
        chatGroupMembers.setIdmember(userID);

        chatGroupMembers.setAvatamember(user.getProfilePicUrl());
        chatGroupMembers.setNamemember(user.getFirstName() + user.getLastName());

        chatGroupMembersRes.save(chatGroupMembers);
        return "redirect:/Chat/Groups";
    }

    //Thêm thành viên mới
    @PostMapping("/addmemgroups")
    public String addmemGroup(RedirectAttributes redirectAttributes, Model model, @RequestParam("userId") String userId, HttpSession session) {
        ChatGroupMembers chatGroupMembers = new ChatGroupMembers();
        chatGroupMembers.setStt((long) session.getAttribute("STTgroup"));
        chatGroupMembers.setIdmember(userId);
        User user = HuyUser.findById(userId);
        chatGroupMembers.setNamemember(user.getFirstName() + user.getLastName());
        chatGroupMembers.setAvatamember(user.getProfilePicUrl());


        ChatGroupMembers chatGroupMembersCheck= chatGroupMembersRes.findBySttAndIdmember((long) session.getAttribute("STTgroup"), userId);
        if(chatGroupMembersCheck != null)
        {
            redirectAttributes.addFlashAttribute("successMessage", "failed");
        }
        else {
            chatGroupMembersRes.save(chatGroupMembers);
            redirectAttributes.addFlashAttribute("successMessage", "success");
        }

        return "redirect:/Chat/Groups";
    }

    //Xóa mem khỏi nhóm
    @Transactional
    @PostMapping("/deletememgroups")
    public String DeletememGroup(RedirectAttributes redirectAttributes, Model model, @RequestParam("targetdeId") String targetdeId, HttpSession session) {
        chatGroupMembersRes.deleteBySttAndIdmember((long) session.getAttribute("STTgroup"), targetdeId);


        ChatGroupMembers chatGroupMembersCheck= chatGroupMembersRes.findBySttAndIdmember((long) session.getAttribute("STTgroup"), targetdeId);
        if(chatGroupMembersCheck != null)
        {
            redirectAttributes.addFlashAttribute("successMessage", "delfailed");
        }
        else {
            redirectAttributes.addFlashAttribute("successMessage", "delsuccess");
        }

        return "redirect:/Chat/Groups";
    }


    //Cút khỏi nhóm
    @Transactional
    @GetMapping("/getout")
    public String GetOut(RedirectAttributes redirectAttributes, HttpSession session) {
        chatGroupMembersRes.deleteBySttAndIdmember((long) session.getAttribute("STTgroup"), (String) session.getAttribute("ID"));

        ChatGroupMembers chatGroupMembersCheck= chatGroupMembersRes.findBySttAndIdmember((long) session.getAttribute("STTgroup"), (String) session.getAttribute("ID"));
        if(chatGroupMembersCheck != null)
        {
            redirectAttributes.addFlashAttribute("successMessage", "outfailed");
        }
        else {
            redirectAttributes.addFlashAttribute("successMessage", "outsuccess");
        }
        session.setAttribute("STTgroup", Long.valueOf(0));

        return "redirect:/Chat/Groups";
    }

    //Xóa nhóm
    @Transactional
    @GetMapping("/delgroup")
    public String DeleteGroup(RedirectAttributes redirectAttributes, HttpSession session) {
        chatGroupMembersRes.deleteByStt((long) session.getAttribute("STTgroup"));
        chatGroupLogsRes.deleteByStt((long) session.getAttribute("STTgroup"));
        chatGroupRes.deleteByStt((long) session.getAttribute("STTgroup"));


        ChatGroup chatGroup = chatGroupRes.findByStt((long) session.getAttribute("STTgroup"));
        if(chatGroup != null)
        {
            redirectAttributes.addFlashAttribute("successMessage", "droupfailed");
        }
        else {
            redirectAttributes.addFlashAttribute("successMessage", "droupsuccess");
        }
        session.setAttribute("STTgroup", Long.valueOf(0));

        return "redirect:/Chat/Groups";
    }

    @PostMapping("/changegroupimage")
    public String ChangeGroupImage(Model model, @RequestParam("image") MultipartFile file, HttpSession session) throws IOException {
        // Generate a unique file name using timestamp
        String pathreal = "/PictureSave/";
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path fileNameAndPath = Paths.get(pathreal, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        Long sttgr = (Long) session.getAttribute("STTgroup");
        ChatGroup chatGroup = chatGroupRes.findByStt(sttgr);
        chatGroup.setGroupimage("/images/" + uniqueFileName);
        chatGroupRes.save(chatGroup);

        model.addAttribute("msg", "Uploaded image: " + uniqueFileName);
        return "redirect:/Chat/Groups";
    }

    @PostMapping("/changegroupname")
    public String ChangeGroupName(Model model, @RequestParam("text") String text, HttpSession session) throws IOException {
        // Generate a unique file name using timestamp
    Long STTGroup = (Long) session.getAttribute("STTgroup");
    ChatGroup chatGroup = chatGroupRes.findByStt(STTGroup);
    chatGroup.setName(text);
    chatGroupRes.save(chatGroup);
    return "redirect:/Chat/Groups";
    }


    public String getDateTimeAsString() {
        // Chuyển đổi ngày giờ thành chuỗi khi cần
        LocalDateTime dateTime = LocalDateTime.now(); // Thay bằng ngày giờ thực tế
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    private String generateUniqueFileName(String originalFileName) {
        // You can use various methods to generate a unique name, here's an example with timestamp
        long timestamp = System.currentTimeMillis();
        return timestamp + "_" + originalFileName;
    }
}
