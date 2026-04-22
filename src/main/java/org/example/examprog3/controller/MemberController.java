package org.example.examprog3.controller;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.Service.MemberService;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateMember;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Member> enroll(@RequestBody List<CreateMember> members) {
        return memberService.enrollMembers(members);
    }
}