package mahat.aviran.tvseriesfetcher.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.tvseriesfetcher.services.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fetcher")
@RequiredArgsConstructor
public class FetcherController {

    private final ManagerService managerService;

    @PostMapping(value = "trigger")
    @ResponseBody
    public void triggerFetch() {
        this.managerService.fetchAll();
    }
}
