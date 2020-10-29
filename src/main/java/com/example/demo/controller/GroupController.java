package com.example.demo.controller;

import com.example.demo.dto.GroupDto;
import com.example.demo.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping("/groups")
    public List<GroupDto> getGroup(){
        return  groupService.getGroup();
    }

    @PostMapping("/groups/auto-grouping")
    public List<GroupDto> autoGroup(){
        return groupService.autoGroup();
    }
}
