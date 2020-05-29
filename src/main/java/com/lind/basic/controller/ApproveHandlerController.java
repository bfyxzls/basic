package com.lind.basic.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("approve")
public class ApproveHandlerController {
    @GetMapping("index")
    public ResponseEntity index(HttpServletRequest request) {
        return ResponseEntity.ok(ImmutableMap.of(
                "approve", request.getAttribute("approveIds"),
                "button", request.getAttribute("buttonIds")));
    }
}
