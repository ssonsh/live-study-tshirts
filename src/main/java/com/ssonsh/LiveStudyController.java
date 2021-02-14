package com.ssonsh;

import com.ssonsh.domain.Dropship;
import com.ssonsh.repository.DropshipRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor // autowired 없이 주입 받을 수 있는 방법 (변수는 final)
public class LiveStudyController {

    private final DropshipRepository dropshipRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("message", "hello thymeleaf");
        return "index";
    }


    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("dropship", new Dropship());
        return "form";
    }

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute @Valid Dropship dropship,
                             Errors erros,
                             Model model,
                             RedirectAttributes redirectAttributes){
        if(erros.hasErrors()){
            model.addAttribute("message", "입력을 잘못했어요.");
            return "form";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        String github = principal.getAttribute("login");

        Dropship existingDropship = dropshipRepository.findByGithub(github);
        if(existingDropship != null){
            Integer id = existingDropship.getId();
            String originGithub = existingDropship.getGithub();
            modelMapper.map(dropship, existingDropship);

            existingDropship.setId(id);
            existingDropship.setGithub(originGithub);
            dropshipRepository.save(existingDropship);
        }else{
            dropship.setGithub(github);
            dropshipRepository.save(dropship);
        }

        redirectAttributes.addAttribute("message", "등록되었습니다.");
        return "redirect:/";
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Dropship> all(){
        return dropshipRepository.findAll();
    }
}
