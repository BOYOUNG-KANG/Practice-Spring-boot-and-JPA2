package jpabook.real1.controller;

import jpabook.real1.domain.Address;
import jpabook.real1.domain.Member;
import jpabook.real1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    //멤버 등록
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setUsername(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; //첫 페이지로 넘어가기
    }
}